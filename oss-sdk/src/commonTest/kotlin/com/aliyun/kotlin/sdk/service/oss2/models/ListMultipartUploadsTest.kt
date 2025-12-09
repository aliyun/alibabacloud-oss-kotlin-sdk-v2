package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListMultipartUploads
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListMultipartUploadsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListMultipartUploadsRequest {}
        assertNull(request.bucket)
        assertNull(request.delimiter)
        assertNull(request.maxUploads)
        assertNull(request.keyMarker)
        assertNull(request.prefix)
        assertNull(request.uploadIdMarker)
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
        val request = ListMultipartUploadsRequest {
            bucket = "my-bucket"
            delimiter = "/"
            maxUploads = 100
            keyMarker = "key100"
            prefix = "prefix/"
            uploadIdMarker = "uploadIdMarker"
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxUploads)
        assertEquals("key100", request.keyMarker)
        assertEquals("prefix/", request.prefix)
        assertEquals("uploadIdMarker", request.uploadIdMarker)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-uploads")
            request.parameters.containsKey("key-marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("upload-id-marker")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListMultipartUploadsRequest.Builder()
        builder.bucket = "my-bucket"
        builder.delimiter = "/"
        builder.maxUploads = 100
        builder.keyMarker = "key100"
        builder.prefix = "prefix/"
        builder.uploadIdMarker = "uploadIdMarker"
        builder.encodingType = "url"

        val request = ListMultipartUploadsRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxUploads)
        assertEquals("key100", request.keyMarker)
        assertEquals("prefix/", request.prefix)
        assertEquals("uploadIdMarker", request.uploadIdMarker)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-uploads")
            request.parameters.containsKey("key-marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("upload-id-marker")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListMultipartUploadsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.keyMarker)
        assertNull(result.uploadIdMarker)
        assertNull(result.nextKeyMarker)
        assertNull(result.maxUploads)
        assertNull(result.isTruncated)
        assertNull(result.bucket)
        assertNull(result.encodingType)
        assertNull(result.nextUploadIdMarker)
        assertNull(result.prefix)
        assertNull(result.delimiter)
        assertNull(result.uploads)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListMultipartUploadsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListMultipartUploads {
                keyMarker = "key"
                uploadIdMarker = "uploadId"
                nextKeyMarker = "key1"
                maxUploads = 100
                isTruncated = true
                bucket = "bucket-name"
                encodingType = "url"
                nextUploadIdMarker = "uploadId1"
                prefix = "prefix/"
                delimiter = "/"
                uploads = listOf(
                    Upload {
                        key = "key0"
                        uploadId = "uploadId0"
                        initiated = "initiated"
                    }
                )
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.keyMarker)
        assertEquals("uploadId", result.uploadIdMarker)
        assertEquals("key1", result.nextKeyMarker)
        assertEquals(100, result.maxUploads)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.bucket)
        assertEquals("url", result.encodingType)
        assertEquals("uploadId1", result.nextUploadIdMarker)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals(1, result.uploads?.size)
        assertEquals("key0", result.uploads?.first()?.key)
        assertEquals("uploadId0", result.uploads?.first()?.uploadId)
        assertEquals("initiated", result.uploads?.first()?.initiated)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = ListMultipartUploadsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListMultipartUploads {
            keyMarker = "key"
            uploadIdMarker = "uploadId"
            nextKeyMarker = "key1"
            maxUploads = 100
            isTruncated = true
            bucket = "bucket-name"
            encodingType = "url"
            nextUploadIdMarker = "uploadId1"
            prefix = "prefix/"
            delimiter = "/"
            uploads = listOf(
                Upload {
                    key = "key0"
                    uploadId = "uploadId0"
                    initiated = "initiated"
                }
            )
        }

        val result = ListMultipartUploadsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.keyMarker)
        assertEquals("uploadId", result.uploadIdMarker)
        assertEquals("key1", result.nextKeyMarker)
        assertEquals(100, result.maxUploads)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.bucket)
        assertEquals("url", result.encodingType)
        assertEquals("uploadId1", result.nextUploadIdMarker)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals(1, result.uploads?.size)
        assertEquals("key0", result.uploads?.first()?.key)
        assertEquals("uploadId0", result.uploads?.first()?.uploadId)
        assertEquals("initiated", result.uploads?.first()?.initiated)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
