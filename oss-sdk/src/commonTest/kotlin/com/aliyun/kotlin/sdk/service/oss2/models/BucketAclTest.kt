package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.*

class BucketAclTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketAclRequest {}
        assertNull(request.bucket)
        assertNull(request.acl)

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
        val request = PutBucketAclRequest {
            bucket = "my-bucket"
            acl = "private"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("private", request.acl)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-acl")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = PutBucketAclRequest.Builder()
        builder.bucket = "my-bucket"
        builder.acl = "private"

        val request = PutBucketAclRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("private", request.acl)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-acl")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutBucketAclResult {}
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
        val result = PutBucketAclResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutBucketAclResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketAclResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
