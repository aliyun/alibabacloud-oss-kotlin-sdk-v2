package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeleteStyleTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = DeleteStyleRequest {}
        assertNull(request.bucket)
        assertNull(request.styleName)

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
        val request = DeleteStyleRequest {
            bucket = "bucket"
            styleName = "imageStyle"
        }

        assertEquals("bucket", request.bucket)
        assertEquals("imageStyle", request.styleName)

        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("styleName")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = DeleteStyleRequest.Builder()
        builder.bucket = "bucket"
        builder.styleName = "imageStyle"

        val request = DeleteStyleRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals("imageStyle", request.styleName)

        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("styleName")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = DeleteStyleResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = DeleteStyleResult.Builder()
        builder.status = "No Content"
        builder.statusCode = 204
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = DeleteStyleResult(builder)
        assertEquals(204, result.statusCode)
        assertEquals("No Content", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
