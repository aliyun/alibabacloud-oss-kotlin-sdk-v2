package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.versionId)
        assertNull(request.range)
        assertNull(request.ifModifiedSince)
        assertNull(request.ifUnmodifiedSince)
        assertNull(request.ifMatch)
        assertNull(request.ifNoneMatch)
        assertNull(request.acceptEncoding)
        assertNull(request.responseContentType)
        assertNull(request.responseContentLanguage)
        assertNull(request.responseExpires)
        assertNull(request.responseCacheControl)
        assertNull(request.responseContentDisposition)
        assertNull(request.responseContentEncoding)

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
        val request = GetObjectRequest {
            bucket = "my-bucket"
            key = "key"
            versionId = "versionId"
            range = "bytes=100-900"
            ifMatch = "ifMatch-123"
            ifNoneMatch = "ifNoneMatch-123"
            ifModifiedSince = "ifModifiedSince-123"
            ifUnmodifiedSince = "ifUnmodifiedSince-123"
            acceptEncoding = "acceptEncoding-123"
            responseExpires = "responseExpires-123"
            responseContentType = "responseContentType-123"
            responseCacheControl = "responseCacheControl-123"
            responseContentLanguage = "responseContentLanguage-123"
            responseContentEncoding = "responseContentEncoding-123"
            responseContentDisposition = "responseContentDisposition-123"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)
        assertEquals("bytes=100-900", request.range)
        assertEquals("ifModifiedSince-123", request.ifModifiedSince)
        assertEquals("ifUnmodifiedSince-123", request.ifUnmodifiedSince)
        assertEquals("ifMatch-123", request.ifMatch)
        assertEquals("ifNoneMatch-123", request.ifNoneMatch)
        assertEquals("acceptEncoding-123", request.acceptEncoding)
        assertEquals("responseContentType-123", request.responseContentType)
        assertEquals("responseContentLanguage-123", request.responseContentLanguage)
        assertEquals("responseExpires-123", request.responseExpires)
        assertEquals("responseCacheControl-123", request.responseCacheControl)
        assertEquals("responseContentDisposition-123", request.responseContentDisposition)
        assertEquals("responseContentEncoding-123", request.responseContentEncoding)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("Range")
            request.headers.containsKey("If-Modified-Since")
            request.headers.containsKey("If-Unmodified-Since")
            request.headers.containsKey("If-Match")
            request.headers.containsKey("If-None-Match")
            request.headers.containsKey("Accept-Encoding")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
            request.parameters.containsKey("response-content-type")
            request.parameters.containsKey("response-content-language")
            request.parameters.containsKey("response-expires")
            request.parameters.containsKey("response-cache-control")
            request.parameters.containsKey("response-content-disposition")
            request.parameters.containsKey("response-content-encoding")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = GetObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"
        builder.range = "bytes=100-900"
        builder.ifMatch = "ifMatch-123"
        builder.ifNoneMatch = "ifNoneMatch-123"
        builder.ifModifiedSince = "ifModifiedSince-123"
        builder.ifUnmodifiedSince = "ifUnmodifiedSince-123"
        builder.acceptEncoding = "acceptEncoding-123"
        builder.responseExpires = "responseExpires-123"
        builder.responseContentType = "responseContentType-123"
        builder.responseCacheControl = "responseCacheControl-123"
        builder.responseContentLanguage = "responseContentLanguage-123"
        builder.responseContentEncoding = "responseContentEncoding-123"
        builder.responseContentDisposition = "responseContentDisposition-123"

        val request = GetObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)
        assertEquals("bytes=100-900", request.range)
        assertEquals("ifModifiedSince-123", request.ifModifiedSince)
        assertEquals("ifUnmodifiedSince-123", request.ifUnmodifiedSince)
        assertEquals("ifMatch-123", request.ifMatch)
        assertEquals("ifNoneMatch-123", request.ifNoneMatch)
        assertEquals("acceptEncoding-123", request.acceptEncoding)
        assertEquals("responseContentType-123", request.responseContentType)
        assertEquals("responseContentLanguage-123", request.responseContentLanguage)
        assertEquals("responseExpires-123", request.responseExpires)
        assertEquals("responseCacheControl-123", request.responseCacheControl)
        assertEquals("responseContentDisposition-123", request.responseContentDisposition)
        assertEquals("responseContentEncoding-123", request.responseContentEncoding)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("Range")
            request.headers.containsKey("If-Modified-Since")
            request.headers.containsKey("If-Unmodified-Since")
            request.headers.containsKey("If-Match")
            request.headers.containsKey("If-None-Match")
            request.headers.containsKey("Accept-Encoding")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
            request.parameters.containsKey("response-content-type")
            request.parameters.containsKey("response-content-language")
            request.parameters.containsKey("response-expires")
            request.parameters.containsKey("response-cache-control")
            request.parameters.containsKey("response-content-disposition")
            request.parameters.containsKey("response-content-encoding")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = GetObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.contentType)
        assertNull(result.storageClass)
        assertNull(result.requestCharged)
        assertNull(result.nextAppendPosition)
        assertNull(result.hashCrc64ecma)
        assertNull(result.restore)
        assertNull(result.processStatus)
        assertNull(result.lastModified)
        assertNull(result.taggingCount)
        assertNull(result.serverSideEncryptionKeyId)
        assertNull(result.objectType)
        assertNull(result.eTag)
        assertEquals(true, result.metadata?.isEmpty())
        assertNull(result.serverSideEncryption)
        assertNull(result.expiration)
        assertNull(result.contentMd5)
        assertNull(result.contentLength)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = GetObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "Content-Type" to "application/xml",
                "x-oss-storage-class" to "IA",
                "x-oss-request-charged" to "requestCharged123",
                "x-oss-next-append-position" to "100",
                "x-oss-hash-crc64ecma" to "crc-123",
                "x-oss-restore" to "restore",
                "x-oss-process-status" to "processStatus",
                "Last-Modified" to "lastModified",
                "x‑oss‑tagging‑count" to "1",
                "x-oss-server-side-encryption-key-id" to "serverSideEncryptionKeyId",
                "x-oss-object-type" to "Normal",
                "ETag" to "eTag-123",
                "x-oss-meta-key" to "value",
                "x-oss-server-side-encryption" to "serverSideEncryption",
                "x-oss-expiration" to "expiration",
                "Content-Md5" to "contentMd5",
                "Content-Length" to "123"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("application/xml", result.contentType)
        assertEquals("IA", result.storageClass)
        assertEquals("requestCharged123", result.requestCharged)
        assertEquals(100, result.nextAppendPosition)
        assertEquals("crc-123", result.hashCrc64ecma)
        assertEquals("restore", result.restore)
        assertEquals("processStatus", result.processStatus)
        assertEquals("lastModified", result.lastModified)
        assertEquals(1, result.taggingCount)
        assertEquals("serverSideEncryptionKeyId", result.serverSideEncryptionKeyId)
        assertEquals("Normal", result.objectType)
        assertEquals("eTag-123", result.eTag)
        assertEquals("value", result.metadata?.get("key"))
        assertEquals("serverSideEncryption", result.serverSideEncryption)
        assertEquals("expiration", result.expiration)
        assertEquals("contentMd5", result.contentMd5)
        assertEquals(123, result.contentLength)

        assertNotNull(result.headers)
        assertEquals(18, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("application/xml", result.headers["Content-Type"])
        assertEquals("IA", result.headers["x-oss-storage-class"])
        assertEquals("requestCharged123", result.headers["x-oss-request-charged"])
        assertEquals("100", result.headers["x-oss-next-append-position"])
        assertEquals("crc-123", result.headers["x-oss-hash-crc64ecma"])
        assertEquals("restore", result.headers["x-oss-restore"])
        assertEquals("processStatus", result.headers["x-oss-process-status"])
        assertEquals("lastModified", result.headers["Last-Modified"])
        assertEquals("1", result.headers["x‑oss‑tagging‑count"])
        assertEquals("serverSideEncryptionKeyId", result.headers["x-oss-server-side-encryption-key-id"])
        assertEquals("Normal", result.headers["x-oss-object-type"])
        assertEquals("eTag-123", result.headers["ETag"])
        assertEquals("value", result.headers["x-oss-meta-key"])
        assertEquals("serverSideEncryption", result.headers["x-oss-server-side-encryption"])
        assertEquals("expiration", result.headers["x-oss-expiration"])
        assertEquals("contentMd5", result.headers["Content-Md5"])
        assertEquals("123", result.headers["Content-Length"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "Content-Type" to "application/xml",
            "x-oss-storage-class" to "IA",
            "x-oss-request-charged" to "requestCharged123",
            "x-oss-next-append-position" to "100",
            "x-oss-hash-crc64ecma" to "crc-123",
            "x-oss-restore" to "restore",
            "x-oss-process-status" to "processStatus",
            "Last-Modified" to "lastModified",
            "x‑oss‑tagging‑count" to "1",
            "x-oss-server-side-encryption-key-id" to "serverSideEncryptionKeyId",
            "x-oss-object-type" to "Normal",
            "ETag" to "eTag-123",
            "x-oss-meta-key" to "value",
            "x-oss-server-side-encryption" to "serverSideEncryption",
            "x-oss-expiration" to "expiration",
            "Content-Md5" to "contentMd5",
            "Content-Length" to "123"
        )

        val result = GetObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("application/xml", result.contentType)
        assertEquals("IA", result.storageClass)
        assertEquals("requestCharged123", result.requestCharged)
        assertEquals(100, result.nextAppendPosition)
        assertEquals("crc-123", result.hashCrc64ecma)
        assertEquals("restore", result.restore)
        assertEquals("processStatus", result.processStatus)
        assertEquals("lastModified", result.lastModified)
        assertEquals(1, result.taggingCount)
        assertEquals("serverSideEncryptionKeyId", result.serverSideEncryptionKeyId)
        assertEquals("Normal", result.objectType)
        assertEquals("eTag-123", result.eTag)
        assertEquals("value", result.metadata?.get("key"))
        assertEquals("serverSideEncryption", result.serverSideEncryption)
        assertEquals("expiration", result.expiration)
        assertEquals("contentMd5", result.contentMd5)
        assertEquals(123, result.contentLength)

        assertNotNull(result.headers)
        assertEquals(18, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("application/xml", result.headers["Content-Type"])
        assertEquals("IA", result.headers["x-oss-storage-class"])
        assertEquals("requestCharged123", result.headers["x-oss-request-charged"])
        assertEquals("100", result.headers["x-oss-next-append-position"])
        assertEquals("crc-123", result.headers["x-oss-hash-crc64ecma"])
        assertEquals("restore", result.headers["x-oss-restore"])
        assertEquals("processStatus", result.headers["x-oss-process-status"])
        assertEquals("lastModified", result.headers["Last-Modified"])
        assertEquals("1", result.headers["x‑oss‑tagging‑count"])
        assertEquals("serverSideEncryptionKeyId", result.headers["x-oss-server-side-encryption-key-id"])
        assertEquals("Normal", result.headers["x-oss-object-type"])
        assertEquals("eTag-123", result.headers["ETag"])
        assertEquals("value", result.headers["x-oss-meta-key"])
        assertEquals("serverSideEncryption", result.headers["x-oss-server-side-encryption"])
        assertEquals("expiration", result.headers["x-oss-expiration"])
        assertEquals("contentMd5", result.headers["Content-Md5"])
        assertEquals("123", result.headers["Content-Length"])
    }
}
