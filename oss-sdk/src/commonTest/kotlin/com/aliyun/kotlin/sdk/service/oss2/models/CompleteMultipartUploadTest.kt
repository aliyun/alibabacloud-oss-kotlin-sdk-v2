package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.CompleteMultipartUploadResultXml
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CompleteMultipartUploadTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = CompleteMultipartUploadRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.uploadId)
        assertNull(request.completeAll)
        assertNull(request.encodingType)
        assertNull(request.completeMultipartUpload)
        assertNull(request.callback)
        assertNull(request.callbackVar)

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
        val request = CompleteMultipartUploadRequest {
            bucket = "my-bucket"
            key = "key"
            uploadId = "uploadId"
            forbidOverwrite = true
            completeAll = "yes"
            encodingType = "url"
            completeMultipartUpload = CompleteMultipartUpload()
            callback = "eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0="
            callbackVar =
                "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("yes", request.completeAll)
        assertEquals("url", request.encodingType)
        assertEquals("eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0=", request.callback)
        assertEquals(
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9",
            request.callbackVar
        )
        assertNotNull(request.completeMultipartUpload)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-complete-all")
            request.headers.containsKey("x-oss-callback")
            request.headers.containsKey("x-oss-callback-var")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = CompleteMultipartUploadRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.uploadId = "uploadId"
        builder.forbidOverwrite = true
        builder.completeAll = "yes"
        builder.encodingType = "url"
        builder.completeMultipartUpload = CompleteMultipartUpload()
        builder.callback = "eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0="
        builder.callbackVar =
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9"

        val request = CompleteMultipartUploadRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("yes", request.completeAll)
        assertEquals("url", request.encodingType)
        assertEquals("eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0=", request.callback)
        assertEquals(
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9",
            request.callbackVar
        )
        assertNotNull(request.completeMultipartUpload)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-complete-all")
            request.headers.containsKey("x-oss-callback")
            request.headers.containsKey("x-oss-callback-var")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = CompleteMultipartUploadResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.location)
        assertNull(result.encodingType)
        assertNull(result.eTag)
        assertNull(result.versionId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = CompleteMultipartUploadResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****"
            )
            innerBody = CompleteMultipartUploadResultXml {
                location = "http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data"
                eTag = "\"B864DB6A936D376F9F8D3ED3BBE540****\""
                bucket = "oss-example"
                key = "multipart.data"
                encodingType = "url"
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****", result.versionId)
        assertEquals("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data", result.location)
        assertEquals("\"B864DB6A936D376F9F8D3ED3BBE540****\"", result.eTag)
        assertEquals("oss-example", result.bucket)
        assertEquals("multipart.data", result.key)
        assertEquals("url", result.encodingType)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****",
            result.headers["x-oss-version-id"]
        )
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = CompleteMultipartUploadResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****"
        )
        builder.innerBody = CompleteMultipartUploadResultXml {
            location = "http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data"
            eTag = "\"B864DB6A936D376F9F8D3ED3BBE540****\""
            bucket = "oss-example"
            key = "multipart.data"
            encodingType = "url"
        }

        val result = CompleteMultipartUploadResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****", result.versionId)
        assertEquals("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data", result.location)
        assertEquals("\"B864DB6A936D376F9F8D3ED3BBE540****\"", result.eTag)
        assertEquals("oss-example", result.bucket)
        assertEquals("multipart.data", result.key)
        assertEquals("url", result.encodingType)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQMxiBgID6v86D0BYiIDc3ZDI0YTBjZGQzYjQ2Mjk4OWVjYWNiMDljYzhlN****",
            result.headers["x-oss-version-id"]
        )
    }
}
