package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketReferer
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketReferer
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketRefererRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketRefererRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RefererBlacklist
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RefererConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RefererList
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

class BucketRefererTest: TestBase() {

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
    fun testPutAndGetBucketReferer() = runTest {
        val configuration = RefererConfiguration {
            allowEmptyReferer = false
            allowTruncateQueryString = true
            truncatePath = true
            refererList = RefererList {
                referrers = listOf(
                    "http://www.aliyun.com",
                    "https://www.aliyun.com",
                    "http://www.*.com",
                    "https://www.?.aliyuncs.com"
                )
            }
            refererBlacklist = RefererBlacklist {
                referrers = listOf(
                    "http://www.refuse.com",
                    "https://*.hack.com",
                    "http://ban.*.com",
                    "https://www.?.deny.com"
                )
            }
        }
        val putResult = defaultClient.putBucketReferer(PutBucketRefererRequest {
            bucket = bucketName
            refererConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketReferer(GetBucketRefererRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.refererConfiguration)
    }

    @Test
    fun testPutBucketRefererWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketReferer(PutBucketRefererRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketReferer(PutBucketRefererRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.refererConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketReferer(PutBucketRefererRequest {
                bucket = bucketName
                refererConfiguration = RefererConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketRefererWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketReferer(GetBucketRefererRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketReferer(GetBucketRefererRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
