package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListStyleTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListStyleRequest {}
        assertNull(request.bucket)

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
        val request = ListStyleRequest {
            bucket = "bucket"
        }

        assertEquals("bucket", request.bucket)
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListStyleRequest.Builder()
        builder.bucket = "bucket"

        val request = ListStyleRequest(builder)
        assertEquals("bucket", request.bucket)
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListStyleResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.styleList)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val styleList = StyleList {
            styles = listOf(
                StyleInfo {
                    name = "imageStyle1"
                    content = "image/resize,p_50"
                    category = "image"
                },
                StyleInfo {
                    name = "imageStyle2"
                    content = "image/resize,p_60"
                    category = "image"
                }
            )
        }
        val result = ListStyleResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = styleList
        }
        assertEquals(200, result.statusCode)
        assertEquals(styleList, result.styleList)
        assertEquals(2, result.styleList?.styles?.size)
        assertEquals("imageStyle1", result.styleList?.styles?.get(0)?.name)
        assertEquals("imageStyle2", result.styleList?.styles?.get(1)?.name)
    }

    @Test
    fun deserializeXmlBody() {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <StyleList>
            <Style>
            <Name>imageStyle1</Name>
            <Content>image/resize,p_50</Content>
            <Category>image</Category>
            </Style>
            <Style>
            <Name>imageStyle2</Name>
            <Content>image/resize,p_60</Content>
            <Category>image</Category>
            </Style>
            </StyleList>
        """.trimIndent()
        val styleList = SerdeUtils.deserializeXmlBody<StyleList>(xml.encodeToByteArray())
        assertEquals(2, styleList.styles?.size)
        assertEquals("imageStyle1", styleList.styles?.get(0)?.name)
        assertEquals("image/resize,p_50", styleList.styles?.get(0)?.content)
        assertEquals("image", styleList.styles?.get(0)?.category)
        assertEquals("imageStyle2", styleList.styles?.get(1)?.name)
        assertEquals("image/resize,p_60", styleList.styles?.get(1)?.content)
    }
}
