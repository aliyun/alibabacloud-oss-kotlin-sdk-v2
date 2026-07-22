package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketArchiveDirectRead
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketArchiveDirectRead
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ArchiveDirectReadConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketArchiveDirectReadRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketArchiveDirectReadRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketArchiveDirectReadTest : TestBase() {

    @Test
    fun testPutAndGetBucketArchiveDirectRead() = bucketTest { bucketName ->
        val configuration = ArchiveDirectReadConfiguration {
            enabled = true
        }
        val putResult = defaultClient.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest {
            bucket = bucketName
            this.archiveDirectReadConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.archiveDirectReadConfiguration)
    }

    @Test
    fun testPutBucketArchiveDirectReadWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.archiveDirectReadConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest {
                bucket = bucketName
                archiveDirectReadConfiguration = ArchiveDirectReadConfiguration {}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketArchiveDirectReadWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
