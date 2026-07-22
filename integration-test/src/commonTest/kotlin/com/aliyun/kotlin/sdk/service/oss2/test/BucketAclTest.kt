package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketAclRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketAclTest: TestBase() {

    @Test
    fun testPutAndGetBucketAcl() = bucketTest { bucketName ->
        defaultClient.putBucketAcl(PutBucketAclRequest {
            bucket = bucketName
            acl = "private"
        })
        val result = defaultClient.getBucketAcl(GetBucketAclRequest {
            bucket = bucketName
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
    }

    @Test
    fun testPutBucketAclWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketAcl(PutBucketAclRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.putBucketAcl(PutBucketAclRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketAclWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketAcl(GetBucketAclRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketAcl(GetBucketAclRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}