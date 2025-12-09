package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetSymlinkTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetSymlinkRequest {}
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
        val request = GetSymlinkRequest {
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
        val builder = GetSymlinkRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"

        val request = GetSymlinkRequest(builder)
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
        val result = GetSymlinkResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.symlinkTarget)
        assertNull(result.versionId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = GetSymlinkResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-symlink-target" to "symlink-target",
                "x-oss-version-id" to "version-id"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("symlink-target", result.symlinkTarget)
        assertEquals("version-id", result.versionId)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("symlink-target", result.headers["x-oss-symlink-target"])
        assertEquals("version-id", result.headers["x-oss-version-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetSymlinkResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-symlink-target" to "symlink-target",
            "x-oss-version-id" to "version-id"
        )

        val result = GetSymlinkResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("symlink-target", result.symlinkTarget)
        assertEquals("version-id", result.versionId)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("symlink-target", result.headers["x-oss-symlink-target"])
        assertEquals("version-id", result.headers["x-oss-version-id"])
    }
}
