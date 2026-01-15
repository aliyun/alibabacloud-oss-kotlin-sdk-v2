package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketTransferAccelerationTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketTransferAccelerationRequest {}
        assertNull(request.bucket)
        assertNull(request.transferAccelerationConfiguration)

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
        val configuration = TransferAccelerationConfiguration {
            enabled = true
        }
        val request = PutBucketTransferAccelerationRequest {
            bucket = "bucket"
            this.transferAccelerationConfiguration = configuration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.transferAccelerationConfiguration)

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
    fun buildRequestFromBuilder() {
        val configuration = TransferAccelerationConfiguration {
            enabled = true
        }
        val builder = PutBucketTransferAccelerationRequest.Builder()
        builder.bucket = "bucket"
        builder.transferAccelerationConfiguration = configuration

        val request = PutBucketTransferAccelerationRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.transferAccelerationConfiguration)

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
    fun buildResultWithEmptyValues() {
        val result = PutBucketTransferAccelerationResult {}
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
        val result = PutBucketTransferAccelerationResult {
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
        val builder = PutBucketTransferAccelerationResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketTransferAccelerationResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
