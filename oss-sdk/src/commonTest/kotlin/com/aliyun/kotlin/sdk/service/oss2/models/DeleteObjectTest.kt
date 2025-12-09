package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeleteObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = DeleteObjectRequest {}
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
        val request = DeleteObjectRequest {
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
        val builder = DeleteObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"

        val request = DeleteObjectRequest(builder)
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
        val result = DeleteObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.versionId)
        assertNull(result.deleteMarker)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = DeleteObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****",
                "x-oss-delete-marker" to "true"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****", result.versionId)
        assertEquals(true, result.deleteMarker)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****",
            result.headers["x-oss-version-id"]
        )
        assertEquals("true", result.headers["x-oss-delete-marker"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = DeleteObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****",
            "x-oss-delete-marker" to "true"
        )

        val result = DeleteObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****", result.versionId)
        assertEquals(true, result.deleteMarker)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQMxiBgIDh3ZCB0BYiIGE4YjIyMjExZDhhYjQxNzZiNGUyZTI4ZjljZDcz****",
            result.headers["x-oss-version-id"]
        )
        assertEquals("true", result.headers["x-oss-delete-marker"])
    }
}
