package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutStyleTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutStyleRequest {}
        assertNull(request.bucket)
        assertNull(request.styleName)
        assertNull(request.category)
        assertNull(request.style)

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
        val style = Style {
            content = "image/resize,p_50"
        }
        val request = PutStyleRequest {
            bucket = "bucket"
            styleName = "imageStyle"
            category = "image"
            this.style = style
        }

        assertEquals("bucket", request.bucket)
        assertEquals("imageStyle", request.styleName)
        assertEquals("image", request.category)
        assertEquals(style, request.style)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("styleName")
        }
        assertTrue {
            request.parameters.contains("category")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val style = Style {
            content = "image/resize,p_50"
        }
        val builder = PutStyleRequest.Builder()
        builder.bucket = "bucket"
        builder.styleName = "imageStyle"
        builder.category = "image"
        builder.style = style

        val request = PutStyleRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals("imageStyle", request.styleName)
        assertEquals("image", request.category)
        assertEquals(style, request.style)

        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("styleName")
        }
        assertTrue {
            request.parameters.contains("category")
        }
    }

    @Test
    fun serializeXmlBodyIncludesContent() {
        val style = Style {
            content = "image/resize,p_50"
        }
        val xml = SerdeUtils.serializeXmlBody(style).decodeToString()
        assertTrue(xml.contains("<Style>"))
        assertTrue(xml.contains("<Content>image/resize,p_50</Content>"))
        assertTrue(xml.contains("</Style>"))
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutStyleResult {}
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
        val builder = PutStyleResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutStyleResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
