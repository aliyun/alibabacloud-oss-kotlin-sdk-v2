package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Tag
import com.aliyun.kotlin.sdk.service.oss2.models.TagSet
import com.aliyun.kotlin.sdk.service.oss2.models.Tagging
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ObjectTaggingTest: TestBase() {

    @Test
    fun testPutObjectTagging() = objectTest { bucketName, objectKey ->
        val result = defaultClient.putObjectTagging(PutObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
            tagging = Tagging {
                tagSet = TagSet {
                    tags = listOf(Tag {
                        key = "key"
                        value = "value"
                    })
                }
            }
        })
        assertEquals(result.statusCode, 200)
    }

    @Test
    fun testGetObjectTagging() = objectTest { bucketName, objectKey ->
        val result = defaultClient.putObjectTagging(PutObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
            tagging = Tagging {
                tagSet = TagSet {
                    tags = listOf(Tag {
                        key = "key"
                        value = "value"
                    })
                }
            }
        })
        assertEquals(result.statusCode, 200)

        val getObjectTaggingResult = defaultClient.getObjectTagging(GetObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
        })
        assertEquals(result.statusCode, 200)
        assertEquals(getObjectTaggingResult.tagging?.tagSet?.tags?.size, 1)
        assertEquals(getObjectTaggingResult.tagging?.tagSet?.tags?.first()?.key, "key")
        assertEquals(getObjectTaggingResult.tagging?.tagSet?.tags?.first()?.value, "value")
    }

    @Test
    fun testPutObjectTaggingWithException() = objectTest { bucketName, objectKey ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putObjectTagging(PutObjectTaggingRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.putObjectTagging(PutObjectTaggingRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.putObjectTagging(PutObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
        }) }
        assertEquals(exception.message, "request.tagging is required")

        exception = assertFails { invalidClient.putObjectTagging(PutObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
            tagging = Tagging()
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetObjectTaggingWithException() = objectTest { bucketName, objectKey ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectTagging(GetObjectTaggingRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectTagging(GetObjectTaggingRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.getObjectTagging(GetObjectTaggingRequest {
            bucket = bucketName
            key = objectKey
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}