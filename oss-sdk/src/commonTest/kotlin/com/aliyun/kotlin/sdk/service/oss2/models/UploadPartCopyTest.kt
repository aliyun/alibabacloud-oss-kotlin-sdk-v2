package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UploadPartCopyTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = UploadPartCopyRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.uploadId)
        assertNull(request.sourceBucket)
        assertNull(request.sourceKey)
        assertNull(request.sourceVersionId)
        assertNull(request.copySourceRange)
        assertNull(request.copySourceIfMatch)
        assertNull(request.copySourceIfNoneMatch)
        assertNull(request.copySourceIfUnmodifiedSince)
        assertNull(request.copySourceIfModifiedSince)
        assertNull(request.partNumber)

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
        val request = UploadPartCopyRequest {
            bucket = "my-bucket"
            key = "key"
            uploadId = "uploadId"
            sourceBucket = "sourceBucket1"
            sourceKey = "sourceKey1"
            sourceVersionId = "sourceVersionId1"
            copySourceRange = "bytes=1-100"
            copySourceIfMatch = "5B3C1A2E053D763E1B002CC607C5****"
            copySourceIfNoneMatch = "6B3C1A2E053D763E1B002CC607C5****"
            copySourceIfUnmodifiedSince = "Mon, 11 May 2020 08:16:23 GMT"
            copySourceIfModifiedSince = "Mon, 11 May 2021 08:16:23 GMT"
            partNumber = 1
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals("sourceBucket1", request.sourceBucket)
        assertEquals("sourceKey1", request.sourceKey)
        assertEquals("sourceVersionId1", request.sourceVersionId)
        assertEquals("bytes=1-100", request.copySourceRange)
        assertEquals(1, request.partNumber)
        assertEquals("5B3C1A2E053D763E1B002CC607C5****", request.copySourceIfMatch)
        assertEquals("6B3C1A2E053D763E1B002CC607C5****", request.copySourceIfNoneMatch)
        assertEquals("Mon, 11 May 2020 08:16:23 GMT", request.copySourceIfUnmodifiedSince)
        assertEquals("Mon, 11 May 2021 08:16:23 GMT", request.copySourceIfModifiedSince)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-copy-source-range")
            request.headers.containsKey("x-oss-copy-source-if-match")
            request.headers.containsKey("x-oss-copy-source-if-none-match")
            request.headers.containsKey("x-oss-copy-source-if-unmodified-since")
            request.headers.containsKey("x-oss-copy-source-if-modified-since")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("versionId")
            request.parameters.containsKey("partNumber")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = UploadPartCopyRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.uploadId = "uploadId"
        builder.sourceBucket = "sourceBucket1"
        builder.sourceKey = "sourceKey1"
        builder.sourceVersionId = "sourceVersionId1"
        builder.copySourceRange = "bytes=1-100"
        builder.copySourceIfMatch = "5B3C1A2E053D763E1B002CC607C5****"
        builder.copySourceIfNoneMatch = "6B3C1A2E053D763E1B002CC607C5****"
        builder.copySourceIfUnmodifiedSince = "Mon, 11 May 2020 08:16:23 GMT"
        builder.copySourceIfModifiedSince = "Mon, 11 May 2021 08:16:23 GMT"
        builder.partNumber = 1

        val request = UploadPartCopyRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("uploadId", request.uploadId)
        assertEquals("sourceBucket1", request.sourceBucket)
        assertEquals("sourceKey1", request.sourceKey)
        assertEquals("sourceVersionId1", request.sourceVersionId)
        assertEquals("bytes=1-100", request.copySourceRange)
        assertEquals(1, request.partNumber)
        assertEquals("5B3C1A2E053D763E1B002CC607C5****", request.copySourceIfMatch)
        assertEquals("6B3C1A2E053D763E1B002CC607C5****", request.copySourceIfNoneMatch)
        assertEquals("Mon, 11 May 2020 08:16:23 GMT", request.copySourceIfUnmodifiedSince)
        assertEquals("Mon, 11 May 2021 08:16:23 GMT", request.copySourceIfModifiedSince)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-copy-source-range")
            request.headers.containsKey("x-oss-copy-source-if-match")
            request.headers.containsKey("x-oss-copy-source-if-none-match")
            request.headers.containsKey("x-oss-copy-source-if-unmodified-since")
            request.headers.containsKey("x-oss-copy-source-if-modified-since")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("versionId")
            request.parameters.containsKey("partNumber")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = UploadPartCopyResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.copyPartResult)
        assertNull(result.copySourceVersionId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = UploadPartCopyResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-copy-source-version-id" to "versionId"
            )
            innerBody = CopyPartResult {
                lastModified = "2014-07-17T06:27:54.000Z"
                eTag = "5B3C1A2E053D763E1B002CC607C5***"
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.copySourceVersionId)
        assertEquals("5B3C1A2E053D763E1B002CC607C5***", result.copyPartResult?.eTag)
        assertEquals("2014-07-17T06:27:54.000Z", result.copyPartResult?.lastModified)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-copy-source-version-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = UploadPartCopyResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-copy-source-version-id" to "versionId"
        )
        builder.innerBody = CopyPartResult {
            lastModified = "2014-07-17T06:27:54.000Z"
            eTag = "5B3C1A2E053D763E1B002CC607C5***"
        }

        val result = UploadPartCopyResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.copySourceVersionId)
        assertEquals("5B3C1A2E053D763E1B002CC607C5***", result.copyPartResult?.eTag)
        assertEquals("2014-07-17T06:27:54.000Z", result.copyPartResult?.lastModified)

        assertNotNull(result.headers)
        assertEquals(2, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-copy-source-version-id"])
    }
}
