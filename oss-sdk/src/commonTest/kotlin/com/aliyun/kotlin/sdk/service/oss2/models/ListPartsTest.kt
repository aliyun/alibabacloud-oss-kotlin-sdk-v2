package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListPartResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListPartsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListPartsRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.uploadId)
        assertNull(request.partNumberMarker)
        assertNull(request.maxParts)
        assertNull(request.encodingType)

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
        val request = ListPartsRequest {
            bucket = "my-bucket"
            key = "key"
            uploadId = "uploadId"
            partNumberMarker = 1
            maxParts = 100
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(1, request.partNumberMarker)
        assertEquals(100, request.maxParts)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("part-number-marker")
            request.parameters.containsKey("max-parts")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListPartsRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.uploadId = "uploadId"
        builder.partNumberMarker = 1
        builder.maxParts = 100
        builder.encodingType = "url"

        val request = ListPartsRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals(1, request.partNumberMarker)
        assertEquals(100, request.maxParts)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("part-number-marker")
            request.parameters.containsKey("max-parts")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListPartsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.isTruncated)
        assertNull(result.parts)
        assertNull(result.bucket)
        assertNull(result.key)
        assertNull(result.uploadId)
        assertNull(result.partNumberMarker)
        assertNull(result.nextPartNumberMarker)
        assertNull(result.maxParts)
        assertNull(result.encodingType)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListPartsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListPartResult {
                isTruncated = true
                parts = listOf(
                    Part {
                        partNumber = 1
                        eTag = "eTag***"
                        size = 100
                        lastModified = "2012-02-24T08:42:32.000Z"
                    }
                )
                bucket = "bucket1"
                key = "key1"
                uploadId = "uploadId1"
                partNumberMarker = 1
                nextPartNumberMarker = 2
                maxParts = 100
                encodingType = "url"
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket1", result.bucket)
        assertEquals("key1", result.key)
        assertEquals("uploadId1", result.uploadId)
        assertEquals(1, result.partNumberMarker)
        assertEquals(2, result.nextPartNumberMarker)
        assertEquals(100, result.maxParts)
        assertEquals("url", result.encodingType)
        assertEquals(1, result.parts?.size)
        assertEquals(100, result.parts?.first()?.size)
        assertEquals(1, result.parts?.first()?.partNumber)
        assertEquals("2012-02-24T08:42:32.000Z", result.parts?.first()?.lastModified)
        assertEquals("eTag***", result.parts?.first()?.eTag)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = ListPartsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListPartResult {
            isTruncated = true
            parts = listOf(
                Part {
                    partNumber = 1
                    eTag = "eTag***"
                    size = 100
                    lastModified = "2012-02-24T08:42:32.000Z"
                }
            )
            bucket = "bucket1"
            key = "key1"
            uploadId = "uploadId1"
            partNumberMarker = 1
            nextPartNumberMarker = 2
            maxParts = 100
            encodingType = "url"
        }

        val result = ListPartsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket1", result.bucket)
        assertEquals("key1", result.key)
        assertEquals("uploadId1", result.uploadId)
        assertEquals(1, result.partNumberMarker)
        assertEquals(2, result.nextPartNumberMarker)
        assertEquals(100, result.maxParts)
        assertEquals("url", result.encodingType)
        assertEquals(1, result.parts?.size)
        assertEquals(100, result.parts?.first()?.size)
        assertEquals(1, result.parts?.first()?.partNumber)
        assertEquals("2012-02-24T08:42:32.000Z", result.parts?.first()?.lastModified)
        assertEquals("eTag***", result.parts?.first()?.eTag)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
