package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketAccessMonitor
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketAccessMonitor
import com.aliyun.kotlin.sdk.service.oss2.extension.models.AccessMonitorConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketAccessMonitorRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketAccessMonitorRequest
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

class BucketAccessMonitorTest: TestBase() {

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
    fun testPutAndGetBucketAccessMonitor() = runTest {
        val configuration = AccessMonitorConfiguration {
            status = "Enabled"
        }
        val putResult = defaultClient.putBucketAccessMonitor(PutBucketAccessMonitorRequest {
            bucket = bucketName
            this.accessMonitorConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketAccessMonitor(GetBucketAccessMonitorRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.accessMonitorConfiguration)
    }

    @Test
    fun testPutBucketAccessMonitorWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketAccessMonitor(PutBucketAccessMonitorRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketAccessMonitor(PutBucketAccessMonitorRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.accessMonitorConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketAccessMonitor(PutBucketAccessMonitorRequest {
                bucket = bucketName
                accessMonitorConfiguration = AccessMonitorConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketAccessMonitorWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketAccessMonitor(GetBucketAccessMonitorRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketAccessMonitor(GetBucketAccessMonitorRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
