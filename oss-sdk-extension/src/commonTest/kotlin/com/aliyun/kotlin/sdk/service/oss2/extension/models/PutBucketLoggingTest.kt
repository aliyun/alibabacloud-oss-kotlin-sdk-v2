package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketLoggingTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketLoggingRequest {}
        assertNull(request.bucket)
        assertNull(request.bucketLoggingStatus)

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
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = "targetBucket"
                targetPrefix = "targetPrefix"
            }
        }
        val request = PutBucketLoggingRequest {
            bucket = "bucket"
            this.bucketLoggingStatus = bucketLoggingStatus
        }

        assertEquals("bucket", request.bucket)
        assertEquals(bucketLoggingStatus, request.bucketLoggingStatus)

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
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = "targetBucket"
                targetPrefix = "targetPrefix"
            }
        }
        val builder = PutBucketLoggingRequest.Builder()
        builder.bucket = "bucket"
        builder.bucketLoggingStatus = bucketLoggingStatus

        val request = PutBucketLoggingRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(bucketLoggingStatus, request.bucketLoggingStatus)

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
        val result = PutBucketLoggingResult {}
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
        val result = PutBucketLoggingResult {
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
        val builder = PutBucketLoggingResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketLoggingResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
