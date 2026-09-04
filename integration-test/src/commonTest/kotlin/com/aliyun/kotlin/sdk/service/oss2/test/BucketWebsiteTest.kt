package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketWebsite
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketWebsite
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketWebsite
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketWebsiteRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ErrorDocument
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketWebsiteRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.IndexDocument
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketWebsiteRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.WebsiteConfiguration
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

class BucketWebsiteTest: TestBase() {

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
    fun testPutAndGetBucketWebsite() = runTest {
        val configuration = WebsiteConfiguration {
            indexDocument = IndexDocument {
                suffix = "index.html"
                supportSubDir = true
                type = 0
            }
            errorDocument = ErrorDocument {
                key = "error.html"
                httpStatus = 404
            }
        }
        val putResult = defaultClient.putBucketWebsite(PutBucketWebsiteRequest {
            bucket = bucketName
            websiteConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketWebsite(GetBucketWebsiteRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.websiteConfiguration)
    }

    @Test
    fun testPutBucketWebsiteWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketWebsite(PutBucketWebsiteRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketWebsite(PutBucketWebsiteRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.websiteConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketWebsite(PutBucketWebsiteRequest {
                bucket = bucketName
                websiteConfiguration = WebsiteConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketWebsiteWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketWebsite(GetBucketWebsiteRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketWebsite(GetBucketWebsiteRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketWebsite() = runTest {
        val configuration = WebsiteConfiguration {
            indexDocument = IndexDocument {
                suffix = "index.html"
                supportSubDir = true
                type = 0
            }
            errorDocument = ErrorDocument {
                key = "error.html"
                httpStatus = 404
            }
        }
        val putResult = defaultClient.putBucketWebsite(PutBucketWebsiteRequest {
            bucket = bucketName
            websiteConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.deleteBucketWebsite(DeleteBucketWebsiteRequest {
            bucket = bucketName
        })
        assertEquals(204, result.statusCode)
    }

    @Test
    fun testDeleteBucketWebsiteWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketWebsite(DeleteBucketWebsiteRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketWebsite(DeleteBucketWebsiteRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
