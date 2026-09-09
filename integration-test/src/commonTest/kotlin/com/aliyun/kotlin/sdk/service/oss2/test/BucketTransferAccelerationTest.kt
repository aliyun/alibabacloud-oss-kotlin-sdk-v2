package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketTransferAcceleration
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketTransferAcceleration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketTransferAccelerationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketTransferAccelerationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.TransferAccelerationConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketTransferAccelerationTest: TestBase() {

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
    fun testPutAndGetBucketTransferAcceleration() = runTest {
        val putResult = defaultClient.putBucketTransferAcceleration(
            PutBucketTransferAccelerationRequest {
                bucket = bucketName
                transferAccelerationConfiguration = TransferAccelerationConfiguration {
                    enabled = true
                }
            })
        assertEquals(200, putResult.statusCode)

        val getResult = defaultClient.getBucketTransferAcceleration(
            GetBucketTransferAccelerationRequest {
                bucket = bucketName
            })
        assertEquals(true, getResult.transferAccelerationConfiguration?.enabled)
    }

    @Test
    fun testPutBucketTransferAccelerationWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketTransferAcceleration(PutBucketTransferAccelerationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketTransferAcceleration(PutBucketTransferAccelerationRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.transferAccelerationConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketTransferAcceleration(PutBucketTransferAccelerationRequest {
                bucket = bucketName
                transferAccelerationConfiguration = TransferAccelerationConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketTransferAccelerationWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketTransferAcceleration(GetBucketTransferAccelerationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketTransferAcceleration(GetBucketTransferAccelerationRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
