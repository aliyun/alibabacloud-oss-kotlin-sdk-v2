package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutSymlinkTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutSymlinkRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.symlinkTarget)
        assertNull(request.objectAcl)
        assertNull(request.storageClass)

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
        val request = PutSymlinkRequest {
            bucket = "my-bucket"
            key = "key1"
            objectAcl = "private"
            symlinkTarget = "symlinkTarget1"
            storageClass = "IA"
            forbidOverwrite = true
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertEquals("symlinkTarget1", request.symlinkTarget)
        assertEquals("IA", request.storageClass)
        assertEquals(true, request.forbidOverwrite)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-symlink-target")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-forbid-overwrite")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = PutSymlinkRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key1"
        builder.objectAcl = "private"
        builder.symlinkTarget = "symlinkTarget1"
        builder.storageClass = "IA"
        builder.forbidOverwrite = true

        val request = PutSymlinkRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertEquals("symlinkTarget1", request.symlinkTarget)
        assertEquals("IA", request.storageClass)
        assertEquals(true, request.forbidOverwrite)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-symlink-target")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-forbid-overwrite")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutSymlinkResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.versionId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = PutSymlinkResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "versionId"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutSymlinkResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "versionId"
        )

        val result = PutSymlinkResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
    }
}
