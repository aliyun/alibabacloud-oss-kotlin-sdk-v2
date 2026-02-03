package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketArchiveDirectRead
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketArchiveDirectRead
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ArchiveDirectReadConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketArchiveDirectReadRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketArchiveDirectReadRequest
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

class BucketArchiveDirectReadTest: TestBase() {

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
    fun testPutAndGetBucketArchiveDirectRead() = runTest {
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
    fun testPutBucketArchiveDirectReadWithException() = runTest {
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
                archiveDirectReadConfiguration = ArchiveDirectReadConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketArchiveDirectReadWithException() = runTest {
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
