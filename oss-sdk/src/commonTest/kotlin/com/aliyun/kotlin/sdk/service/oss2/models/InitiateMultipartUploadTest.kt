package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.InitiateMultipartUploadResultXml
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InitiateMultipartUploadTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = InitiateMultipartUploadRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.storageClass)
        assertNull(request.tagging)
        assertNull(request.serverSideEncryption)
        assertNull(request.serverSideDataEncryption)
        assertNull(request.serverSideEncryptionKeyId)
        assertNull(request.cacheControl)
        assertNull(request.contentDisposition)
        assertNull(request.contentEncoding)
        assertNull(request.expires)
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
        val request = InitiateMultipartUploadRequest {
            bucket = "my-bucket"
            key = "key"
            forbidOverwrite = true
            storageClass = "IA"
            tagging = "A=B"
            serverSideEncryption = "serverSideEncryption"
            serverSideDataEncryption = "serverSideDataEncryption"
            serverSideEncryptionKeyId = "serverSideEncryptionKeyId"
            cacheControl = "no catch"
            contentDisposition = "contentDisposition"
            contentEncoding = "url"
            expires = "expires"
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("IA", request.storageClass)
        assertEquals("A=B", request.tagging)
        assertEquals("serverSideEncryption", request.serverSideEncryption)
        assertEquals("serverSideDataEncryption", request.serverSideDataEncryption)
        assertEquals("serverSideEncryptionKeyId", request.serverSideEncryptionKeyId)
        assertEquals("no catch", request.cacheControl)
        assertEquals("contentDisposition", request.contentDisposition)
        assertEquals("url", request.contentEncoding)
        assertEquals("expires", request.expires)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-server-side-data-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("Cache-Control")
            request.headers.containsKey("Content-Disposition")
            request.headers.containsKey("Content-Encoding")
            request.headers.containsKey("Expires")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = InitiateMultipartUploadRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.forbidOverwrite = true
        builder.storageClass = "IA"
        builder.tagging = "A=B"
        builder.serverSideEncryption = "serverSideEncryption"
        builder.serverSideDataEncryption = "serverSideDataEncryption"
        builder.serverSideEncryptionKeyId = "serverSideEncryptionKeyId"
        builder.cacheControl = "no catch"
        builder.contentDisposition = "contentDisposition"
        builder.contentEncoding = "url"
        builder.expires = "expires"
        builder.encodingType = "url"

        val request = InitiateMultipartUploadRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("IA", request.storageClass)
        assertEquals("A=B", request.tagging)
        assertEquals("serverSideEncryption", request.serverSideEncryption)
        assertEquals("serverSideDataEncryption", request.serverSideDataEncryption)
        assertEquals("serverSideEncryptionKeyId", request.serverSideEncryptionKeyId)
        assertEquals("no catch", request.cacheControl)
        assertEquals("contentDisposition", request.contentDisposition)
        assertEquals("url", request.contentEncoding)
        assertEquals("expires", request.expires)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-server-side-data-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("Cache-Control")
            request.headers.containsKey("Content-Disposition")
            request.headers.containsKey("Content-Encoding")
            request.headers.containsKey("Expires")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = InitiateMultipartUploadResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.key)
        assertNull(result.uploadId)
        assertNull(result.bucket)
        assertNull(result.encodingType)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = InitiateMultipartUploadResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = InitiateMultipartUploadResultXml {
                bucket = "bucket"
                key = "key"
                uploadId = "uploadId"
                encodingType = "url"
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.key)
        assertEquals("uploadId", result.uploadId)
        assertEquals("bucket", result.bucket)
        assertEquals("url", result.encodingType)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = InitiateMultipartUploadResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = InitiateMultipartUploadResultXml {
            bucket = "bucket"
            key = "key"
            uploadId = "uploadId"
            encodingType = "url"
        }

        val result = InitiateMultipartUploadResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.key)
        assertEquals("uploadId", result.uploadId)
        assertEquals("bucket", result.bucket)
        assertEquals("url", result.encodingType)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
