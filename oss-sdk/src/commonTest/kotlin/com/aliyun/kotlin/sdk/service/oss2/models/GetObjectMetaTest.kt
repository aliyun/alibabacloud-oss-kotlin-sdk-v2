package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetObjectMetaTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetObjectMetaRequest {}
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
        val request = GetObjectMetaRequest {
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
        val builder = GetObjectMetaRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"

        val request = GetObjectMetaRequest(builder)
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
        val result = GetObjectMetaResult {}
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
        val result = GetObjectMetaResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "versionId-123",
                "ETag" to "eTag-123",
                "Content-Length" to "100",
                "Last-Modified" to "Tue, 30 Mar 2021 06:07:48 GMT",
                "x-oss-last-access-time" to "Tue, 30 Mar 2022 06:07:48 GMT",
                "x-oss-transition-time" to "Tue, 30 Mar 2023 06:07:48 GMT"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId-123", result.versionId)
        assertEquals("eTag-123", result.eTag)
        assertEquals(100, result.contentLength)
        assertEquals("Tue, 30 Mar 2021 06:07:48 GMT", result.lastModified)
        assertEquals("Tue, 30 Mar 2022 06:07:48 GMT", result.lastAccessTime)
        assertEquals("Tue, 30 Mar 2023 06:07:48 GMT", result.transitionTime)

        assertNotNull(result.headers)
        assertEquals(7, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId-123", result.headers["x-oss-version-id"])
        assertEquals("eTag-123", result.headers["ETag"])
        assertEquals("100", result.headers["Content-Length"])
        assertEquals("Tue, 30 Mar 2021 06:07:48 GMT", result.headers["Last-Modified"])
        assertEquals("Tue, 30 Mar 2022 06:07:48 GMT", result.headers["x-oss-last-access-time"])
        assertEquals("Tue, 30 Mar 2023 06:07:48 GMT", result.headers["x-oss-transition-time"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetObjectMetaResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "versionId-123",
            "ETag" to "eTag-123",
            "Content-Length" to "100",
            "Last-Modified" to "Tue, 30 Mar 2021 06:07:48 GMT",
            "x-oss-last-access-time" to "Tue, 30 Mar 2022 06:07:48 GMT",
            "x-oss-transition-time" to "Tue, 30 Mar 2023 06:07:48 GMT"
        )

        val result = GetObjectMetaResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId-123", result.versionId)
        assertEquals("eTag-123", result.eTag)
        assertEquals(100, result.contentLength)
        assertEquals("Tue, 30 Mar 2021 06:07:48 GMT", result.lastModified)
        assertEquals("Tue, 30 Mar 2022 06:07:48 GMT", result.lastAccessTime)
        assertEquals("Tue, 30 Mar 2023 06:07:48 GMT", result.transitionTime)

        assertNotNull(result.headers)
        assertEquals(7, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId-123", result.headers["x-oss-version-id"])
        assertEquals("eTag-123", result.headers["ETag"])
        assertEquals("100", result.headers["Content-Length"])
        assertEquals("Tue, 30 Mar 2021 06:07:48 GMT", result.headers["Last-Modified"])
        assertEquals("Tue, 30 Mar 2022 06:07:48 GMT", result.headers["x-oss-last-access-time"])
        assertEquals("Tue, 30 Mar 2023 06:07:48 GMT", result.headers["x-oss-transition-time"])
    }
}
