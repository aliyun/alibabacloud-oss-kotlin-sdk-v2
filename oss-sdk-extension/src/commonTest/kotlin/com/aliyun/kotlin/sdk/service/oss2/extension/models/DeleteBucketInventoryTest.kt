package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeleteBucketInventoryTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = DeleteBucketInventoryRequest {}
        assertNull(request.bucket)
        assertNull(request.inventoryId)

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
        val request = DeleteBucketInventoryRequest {
            bucket = "bucket"
            inventoryId = "inventoryId"
        }

        assertEquals("bucket", request.bucket)
        assertEquals("inventoryId", request.inventoryId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("inventoryId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = DeleteBucketInventoryRequest.Builder()
        builder.bucket = "bucket"
        builder.inventoryId = "inventoryId"

        val request = DeleteBucketInventoryRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals("inventoryId", request.inventoryId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("inventoryId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = DeleteBucketInventoryResult {}
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
        val result = DeleteBucketInventoryResult {
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
        val builder = DeleteBucketInventoryResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = DeleteBucketInventoryResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
