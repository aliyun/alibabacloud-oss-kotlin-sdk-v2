package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutObjectAclTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutObjectAclRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.versionId)
        assertNull(request.objectAcl)

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
        val request = PutObjectAclRequest {
            bucket = "my-bucket"
            key = "key1"
            objectAcl = "private"
            versionId = "versionId1"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertEquals("versionId1", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = PutObjectAclRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key1"
        builder.objectAcl = "private"
        builder.versionId = "versionId1"

        val request = PutObjectAclRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertEquals("versionId1", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutObjectAclResult {}
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
        val result = PutObjectAclResult {
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
        val builder = PutObjectAclResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "versionId"
        )

        val result = PutObjectAclResult(builder)
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
