package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetObjectTaggingTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetObjectTaggingRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.versionId)

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
        val request = GetObjectTaggingRequest {
            bucket = "my-bucket"
            key = "key"
            versionId = "versionId"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = GetObjectTaggingRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"

        val request = GetObjectTaggingRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = GetObjectTaggingResult {}
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
        val result = GetObjectTaggingResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = Tagging {
                tagSet = TagSet {
                    tags = listOf(
                        Tag {
                            key = "key"
                            value = "value"
                        }
                    )
                }
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.tagging?.tagSet?.tags?.size)
        assertEquals("key", result.tagging?.tagSet?.tags?.first()?.key)
        assertEquals("value", result.tagging?.tagSet?.tags?.first()?.value)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetObjectTaggingResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = Tagging {
            tagSet = TagSet {
                tags = listOf(
                    Tag {
                        key = "key"
                        value = "value"
                    }
                )
            }
        }

        val result = GetObjectTaggingResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.tagging?.tagSet?.tags?.size)
        assertEquals("key", result.tagging?.tagSet?.tags?.first()?.key)
        assertEquals("value", result.tagging?.tagSet?.tags?.first()?.value)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
