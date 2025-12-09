package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SealAppendObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = SealAppendObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.position)

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
        val request = SealAppendObjectRequest {
            bucket = "my-bucket"
            key = "key"
            position = 10
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals(10, request.position)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("position")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = SealAppendObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.position = 10

        val request = SealAppendObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals(10, request.position)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("position")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = SealAppendObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.sealedTime)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = SealAppendObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-sealed-time" to "Wed, 07 May 2025 23:00:00 GMT"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("Wed, 07 May 2025 23:00:00 GMT", result.sealedTime)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("Wed, 07 May 2025 23:00:00 GMT", result.headers["x-oss-sealed-time"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = SealAppendObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-sealed-time" to "Wed, 07 May 2025 23:00:00 GMT"
        )

        val result = SealAppendObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("Wed, 07 May 2025 23:00:00 GMT", result.sealedTime)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("Wed, 07 May 2025 23:00:00 GMT", result.headers["x-oss-sealed-time"])
    }
}
