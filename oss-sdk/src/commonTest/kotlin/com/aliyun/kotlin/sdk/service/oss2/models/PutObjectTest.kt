package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.progressListener)
        assertNull(request.serverSideEncryption)
        assertNull(request.serverSideDataEncryption)
        assertNull(request.serverSideEncryptionKeyId)
        assertNull(request.storageClass)
        assertNull(request.tagging)
        assertNull(request.objectAcl)
        assertNull(request.callback)
        assertNull(request.callbackVar)
        assertNull(request.trafficLimit)
        assertNull(request.body)
        assertEquals(true, request.metadata?.isEmpty())

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
        val request = PutObjectRequest {
            bucket = "my-bucket"
            key = "key1"
            objectAcl = "private"
            forbidOverwrite = true
            progressListener = ProgressListener { _, _, _ -> }
            serverSideEncryption = "AES256"
            serverSideDataEncryption = "SM4"
            serverSideEncryptionKeyId = "9468da86-3509-4f8d-a61e-6eab1eac****"
            storageClass = "IA"
            tagging = "a=b"
            callback =
                "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9"
            callbackVar = "eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0="
            trafficLimit = "100"
            metadata = mapOf(
                "key" to "value"
            )
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertNotNull(request.progressListener)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("SM4", request.serverSideDataEncryption)
        assertEquals("9468da86-3509-4f8d-a61e-6eab1eac****", request.serverSideEncryptionKeyId)
        assertEquals("IA", request.storageClass)
        assertEquals("a=b", request.tagging)
        assertEquals(
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9",
            request.callback
        )
        assertEquals("eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0=", request.callbackVar)
        assertEquals("100", request.trafficLimit)
        assertEquals("value", request.metadata?.get("key"))

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-server-side-data-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-callback")
            request.headers.containsKey("x-oss-callback-var")
            request.headers.containsKey("x-oss-traffic-limit")
            request.headers.containsKey("x-oss-meta-key")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = PutObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key1"
        builder.objectAcl = "private"
        builder.forbidOverwrite = true
        builder.progressListener = ProgressListener { _, _, _ -> }
        builder.serverSideEncryption = "AES256"
        builder.serverSideDataEncryption = "SM4"
        builder.serverSideEncryptionKeyId = "9468da86-3509-4f8d-a61e-6eab1eac****"
        builder.storageClass = "IA"
        builder.tagging = "a=b"
        builder.callback =
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9"
        builder.callbackVar = "eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0="
        builder.trafficLimit = "100"
        builder.metadata = mapOf(
            "key" to "value"
        )

        val request = PutObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertEquals("private", request.objectAcl)
        assertNotNull(request.progressListener)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("SM4", request.serverSideDataEncryption)
        assertEquals("9468da86-3509-4f8d-a61e-6eab1eac****", request.serverSideEncryptionKeyId)
        assertEquals("IA", request.storageClass)
        assertEquals("a=b", request.tagging)
        assertEquals(
            "eyJjYWxsYmFja0hvc3QiOiAieW91ci5jYWxsYmFjay5jb20iLCAiY2FsbGJhY2tVcmwiOiAiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsICJjYWxsYmFja0JvZHkiOiAiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9JnVpZD0ke3g6dWlkfSZvcmRlcj0ke3g6b3JkZXJfaWR9IiwgImNhbGxiYWNrQm9keVR5cGUiOiAiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwgImNhbGxiYWNrU05JIjogZmFsc2V9",
            request.callback
        )
        assertEquals("eyJ4OnVpZCI6ICIxMjM0NSIsICJ4Om9yZGVyX2lkIjogIjY3ODkwIn0=", request.callbackVar)
        assertEquals("100", request.trafficLimit)
        assertEquals("value", request.metadata?.get("key"))

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-server-side-data-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-callback")
            request.headers.containsKey("x-oss-callback-var")
            request.headers.containsKey("x-oss-traffic-limit")
            request.headers.containsKey("x-oss-meta-key")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.versionId)
        assertNull(result.eTag)
        assertNull(result.hashCrc64ecma)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = PutObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "versionId",
                "ETag" to "eTag",
                "x-oss-hash-crc64ecma" to "123"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)
        assertEquals("eTag", result.eTag)
        assertEquals(123, result.hashCrc64ecma)

        assertNotNull(result.headers)
        assertEquals(4, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
        assertEquals("eTag", result.headers["ETag"])
        assertEquals("123", result.headers["x-oss-hash-crc64ecma"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "versionId",
            "ETag" to "eTag",
            "x-oss-hash-crc64ecma" to "123"
        )

        val result = PutObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)
        assertEquals("eTag", result.eTag)
        assertEquals(123, result.hashCrc64ecma)

        assertNotNull(result.headers)
        assertEquals(4, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
        assertEquals("eTag", result.headers["ETag"])
        assertEquals("123", result.headers["x-oss-hash-crc64ecma"])
    }
}
