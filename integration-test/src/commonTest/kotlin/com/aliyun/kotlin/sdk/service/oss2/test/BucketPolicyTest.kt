package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketPolicy
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketPolicy
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketPolicyStatus
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketPolicy
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketPolicyRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsRequest.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketPolicyRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketPolicyStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketPolicyRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketPolicyTest: TestBase() {

    val bucketName: String = randomBucketName()

    @BeforeTest
    fun putBucket() = runTest {
        defaultClient.putBucket(PutBucketRequest {
            bucket = bucketName
        })
    }

    @AfterTest
    fun cleanAndDeleteBucket() = runTest {
        defaultClient.deleteBucket(DeleteBucketRequest {
            bucket = bucketName
        })
    }

    @Test
    fun testPutAndGetBucketPolicy() = runTest {
        val policy = """
            {
                "Version":"1",
                "Statement":[
                {
                    "Action":[
                        "oss:PutObject",
                        "oss:GetObject"
                    ],
                    "Effect":"Deny",
                    "Principal":["1234567890"],
                    "Resource":["acs:oss:*:1234567890:*/*"]
               }
               ]
            }
        """.trimIndent().replace("\n", "")
        val putResult = defaultClient.putBucketPolicy(PutBucketPolicyRequest {
            bucket = bucketName
            body = ByteStream.fromString(policy)
        })
        assertEquals(200, putResult.statusCode)

        val getResult = defaultClient.getBucketPolicy(GetBucketPolicyRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(policy, getResult.body?.decodeToString())
    }

    @Test
    fun testPutBucketPolicyWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketPolicy(PutBucketPolicyRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.putBucketPolicy(PutBucketPolicyRequest {
                bucket = bucketName
                body = ByteStream.fromString("")
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketPolicyWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketPolicy(GetBucketPolicyRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketPolicy(GetBucketPolicyRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketPolicyStatus() = runTest {
        val getResult = defaultClient.getBucketPolicyStatus(GetBucketPolicyStatusRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(false, getResult.policyStatus?.isPublic)
    }

    @Test
    fun testGetBucketPolicyStatusWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketPolicyStatus(GetBucketPolicyStatusRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketPolicyStatus(GetBucketPolicyStatusRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketPolicy() = runTest {
        val policy = """
            {
                "Version":"1",
                "Statement":[
                {
                    "Action":[
                        "oss:PutObject",
                        "oss:GetObject"
                    ],
                    "Effect":"Deny",
                    "Principal":["1234567890"],
                    "Resource":["acs:oss:*:1234567890:*/*"]
               }
               ]
            }
        """.trimIndent().replace("\n", "")
        val putResult = defaultClient.putBucketPolicy(PutBucketPolicyRequest {
            bucket = bucketName
            body = ByteStream.fromString(policy)
        })
        assertEquals(200, putResult.statusCode)

        val deleteResult = defaultClient.deleteBucketPolicy(DeleteBucketPolicyRequest {
            bucket = bucketName
        })
        assertEquals(204, deleteResult.statusCode)
    }

    @Test
    fun testDeleteBucketPolicyWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketPolicy(DeleteBucketPolicyRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketPolicy(DeleteBucketPolicyRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
