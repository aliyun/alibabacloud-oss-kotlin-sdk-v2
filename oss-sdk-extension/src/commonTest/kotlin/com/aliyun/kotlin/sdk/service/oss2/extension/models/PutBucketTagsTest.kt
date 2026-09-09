package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketTagsTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketTagsRequest {}
        assertNull(request.bucket)
        assertNull(request.tagging)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestWithFullValuesFromDsl() {
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
        val request = PutBucketTagsRequest {
            bucket = "bucket"
            this.tagging = tagging
        }

        assertEquals("bucket", request.bucket)
        assertEquals(tagging, request.tagging)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
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
        val builder = PutBucketTagsRequest.Builder()
        builder.bucket = "bucket"
        builder.tagging = tagging

        val request = PutBucketTagsRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(tagging, request.tagging)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutBucketTagsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = PutBucketTagsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutBucketTagsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketTagsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
