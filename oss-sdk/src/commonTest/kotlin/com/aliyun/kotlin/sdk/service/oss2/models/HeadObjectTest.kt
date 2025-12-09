package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HeadObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = HeadObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.versionId)

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
        val request = HeadObjectRequest {
            bucket = "my-bucket"
            key = "key"
            versionId = "versionId"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = HeadObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.versionId = "versionId"

        val request = HeadObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("versionId", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = HeadObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.nextAppendPosition)
        assertNull(result.hashCrc64ecma)
        assertNull(result.expiration)
        assertNull(result.serverSideEncryptionKeyId)
        assertNull(result.restore)
        assertNull(result.lastModified)
        assertNull(result.taggingCount)
        assertNull(result.serverSideEncryption)
        assertNull(result.objectType)
        assertNull(result.processStatus)
        assertNull(result.requestCharged)
        assertNull(result.contentMd5)
        assertNull(result.contentType)
        assertNull(result.eTag)
        assertNull(result.storageClass)
        assertNull(result.transitionTime)
        assertNull(result.contentLength)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = HeadObjectResult {
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
        val builder = HeadObjectResult.Builder()
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

        val result = HeadObjectResult(builder)
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
