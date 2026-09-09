package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketTags
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketTags
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketTags
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketTagsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketTagsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketTagsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.Tag
import com.aliyun.kotlin.sdk.service.oss2.extension.models.TagSet
import com.aliyun.kotlin.sdk.service.oss2.extension.models.Tagging
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

class BucketTagsTest: TestBase() {

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
    fun testPutAndGetBucketTags() = runTest {
        val tagging = Tagging {
            tagSet = TagSet {
                tags = listOf(
                    Tag {
                        key = "key1"
                        value = "value1"
                    },
                    Tag {
                        key = "key2"
                        value = "value2"
                    }
                )
            }
        }
        val putResult = defaultClient.putBucketTags(PutBucketTagsRequest {
            bucket = bucketName
            this.tagging = tagging
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketTags(GetBucketTagsRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(tagging, result.tagging)
    }

    @Test
    fun testPutBucketTagsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketTags(PutBucketTagsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketTags(PutBucketTagsRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.tagging is required")

        exception = assertFails {
            invalidClient.putBucketTags(PutBucketTagsRequest {
                bucket = bucketName
                tagging = Tagging{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketTagsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketTags(GetBucketTagsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketTags(GetBucketTagsRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketTags() = runTest {
        val tagging = Tagging {
            tagSet = TagSet {
                tags = listOf(
                    Tag {
                        key = "key1"
                        value = "value1"
                    },
                    Tag {
                        key = "key2"
                        value = "value2"
                    }
                )
            }
        }
        val putResult = defaultClient.putBucketTags(PutBucketTagsRequest {
            bucket = bucketName
            this.tagging = tagging
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.deleteBucketTags(DeleteBucketTagsRequest {
            bucket = bucketName
        })
        assertEquals(204, result.statusCode)
    }

    @Test
    fun testDeleteBucketTagsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketTags(DeleteBucketTagsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketTags(DeleteBucketTagsRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
