package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketEncryption
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketEncryption
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketEncryption
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ApplyServerSideEncryptionByDefault
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketEncryptionRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketEncryptionRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketEncryptionRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ServerSideEncryptionRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketEncryptionTest : TestBase() {

    @Test
    fun testPutAndGetBucketEncryption() = bucketTest { bucketName ->
        val rule = ServerSideEncryptionRule {
            applyServerSideEncryptionByDefault = ApplyServerSideEncryptionByDefault {
                sSEAlgorithm = "AES256"
            }
        }
        val putResult = defaultClient.putBucketEncryption(PutBucketEncryptionRequest {
            bucket = bucketName
            this.serverSideEncryptionRule = rule
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketEncryption(GetBucketEncryptionRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(rule, result.serverSideEncryptionRule)
    }

    @Test
    fun testPutBucketEncryptionWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketEncryption(PutBucketEncryptionRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketEncryption(PutBucketEncryptionRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.serverSideEncryptionRule is required")

        exception = assertFails {
            invalidClient.putBucketEncryption(PutBucketEncryptionRequest {
                bucket = bucketName
                serverSideEncryptionRule = ServerSideEncryptionRule {}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketEncryptionWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketEncryption(GetBucketEncryptionRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketEncryption(GetBucketEncryptionRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketEncryption() = bucketTest { bucketName ->
        val rule = ServerSideEncryptionRule {
            applyServerSideEncryptionByDefault = ApplyServerSideEncryptionByDefault {
                sSEAlgorithm = "AES256"
            }
        }
        val putResult = defaultClient.putBucketEncryption(PutBucketEncryptionRequest {
            bucket = bucketName
            this.serverSideEncryptionRule = rule
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.deleteBucketEncryption(DeleteBucketEncryptionRequest {
            bucket = bucketName
        })
        assertEquals(204, result.statusCode)
    }

    @Test
    fun testDeleteBucketEncryptionWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketEncryption(DeleteBucketEncryptionRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketEncryption(DeleteBucketEncryptionRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
