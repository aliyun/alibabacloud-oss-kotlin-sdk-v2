package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetStyleTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetStyleRequest {}
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
        val request = GetStyleRequest {
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
        val builder = GetStyleRequest.Builder()
        builder.bucket = "bucket"
        builder.styleName = "imageStyle"

        val request = GetStyleRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals("imageStyle", request.styleName)

        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("styleName")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = GetStyleResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.style)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val styleInfo = StyleInfo {
            name = "imageStyle"
            content = "image/resize,p_50"
            createTime = "Wed, 20 May 2020 12:07:15 GMT"
            lastModifyTime = "Wed, 21 May 2020 12:07:15 GMT"
            category = "image"
        }
        val result = GetStyleResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = styleInfo
        }
        assertEquals(200, result.statusCode)
        assertEquals(styleInfo, result.style)
        assertEquals("imageStyle", result.style?.name)
        assertEquals("image/resize,p_50", result.style?.content)
        assertEquals("image", result.style?.category)
    }

    @Test
    fun deserializeXmlBody() {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <Style>
            <Name>imageStyle</Name>
            <Content>image/resize,p_50</Content>
            <CreateTime>Wed, 20 May 2020 12:07:15 GMT</CreateTime>
            <LastModifyTime>Wed, 21 May 2020 12:07:15 GMT</LastModifyTime>
            <Category>image</Category>
            </Style>
        """.trimIndent()
        val styleInfo = SerdeUtils.deserializeXmlBody<StyleInfo>(xml.encodeToByteArray())
        assertEquals("imageStyle", styleInfo.name)
        assertEquals("image/resize,p_50", styleInfo.content)
        assertEquals("Wed, 20 May 2020 12:07:15 GMT", styleInfo.createTime)
        assertEquals("Wed, 21 May 2020 12:07:15 GMT", styleInfo.lastModifyTime)
        assertEquals("image", styleInfo.category)
    }
}
