package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UploadPartTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = UploadPartRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.uploadId)
        assertNull(request.progressListener)
        assertNull(request.partNumber)
        assertNull(request.body)

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
        val request = UploadPartRequest {
            bucket = "my-bucket"
            key = "key"
            uploadId = "uploadId"
            progressListener = ProgressListener { _, _, _ -> }
            partNumber = 1
            body = ByteStream.fromString("Hello oss.")
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(1, request.partNumber)
        assertNotNull(request.progressListener)
        assertNotNull(request.body)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("partNumber")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = UploadPartRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.uploadId = "uploadId"
        builder.progressListener = ProgressListener { _, _, _ -> }
        builder.partNumber = 1
        builder.body = ByteStream.fromString("Hello oss.")

        val request = UploadPartRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(1, request.partNumber)
        assertNotNull(request.progressListener)
        assertNotNull(request.body)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("partNumber")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = UploadPartResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.eTag)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = UploadPartResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "ETag" to "\"5B3C1A2E053D763E1B002CC607C5****\""
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("\"5B3C1A2E053D763E1B002CC607C5****\"", result.eTag)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("\"5B3C1A2E053D763E1B002CC607C5****\"", result.headers["ETag"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = UploadPartResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "ETag" to "\"5B3C1A2E053D763E1B002CC607C5****\""
        )

        val result = UploadPartResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("\"5B3C1A2E053D763E1B002CC607C5****\"", result.eTag)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("\"5B3C1A2E053D763E1B002CC607C5****\"", result.headers["ETag"])
    }
}
