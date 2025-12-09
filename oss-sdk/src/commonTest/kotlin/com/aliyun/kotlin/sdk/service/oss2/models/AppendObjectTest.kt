package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AppendObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = AppendObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.position)
        assertNull(request.body)
        assertNull(request.progressListener)
        assertNull(request.serverSideEncryption)
        assertNull(request.objectAcl)
        assertNull(request.storageClass)
        assertEquals(true, request.metadata?.isEmpty())
        assertNull(request.cacheControl)
        assertNull(request.expires)
        assertNull(request.contentEncoding)
        assertNull(request.contentDisposition)
        assertNull(request.contentMd5)
        assertNull(request.initHashCRC64)

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
        val request = AppendObjectRequest {
            bucket = "my-bucket"
            key = "key"
            position = 10
            body = ByteStream.fromString("hello oss.")
            progressListener = ProgressListener { _, _, _ -> }
            serverSideEncryption = "AES256"
            objectAcl = "private"
            storageClass = "Standard"
            metadata = mapOf(
                "key" to "value"
            )
            cacheControl = "no-cache"
            expires = "Wed, 08 Jul 2015 16:57:01 GMT"
            contentEncoding = "url"
            contentDisposition = "attachment;filename=oss_download.jpg"
            contentMd5 = "ohhnqLBJFiKkPSBO1eNaUA=="
            initHashCRC64 = 10
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertNotNull(request.body)
        assertNotNull(request.progressListener)
        assertEquals(10, request.position)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("private", request.objectAcl)
        assertEquals("Standard", request.storageClass)
        assertEquals("value", request.metadata?.get("key"))
        assertEquals("no-cache", request.cacheControl)
        assertEquals("Wed, 08 Jul 2015 16:57:01 GMT", request.expires)
        assertEquals("url", request.contentEncoding)
        assertEquals("attachment;filename=oss_download.jpg", request.contentDisposition)
        assertEquals("ohhnqLBJFiKkPSBO1eNaUA==", request.contentMd5)
        assertEquals(10, request.initHashCRC64)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-meta-key")
            request.headers.containsKey("Content-Disposition")
            request.headers.containsKey("Content-Encoding")
            request.headers.containsKey("Content-MD5")
            request.headers.containsKey("Expires")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("position")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = AppendObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.position = 10
        builder.body = ByteStream.fromString("hello oss.")
        builder.progressListener = ProgressListener { _, _, _ -> }
        builder.serverSideEncryption = "AES256"
        builder.objectAcl = "private"
        builder.storageClass = "Standard"
        builder.metadata = mapOf(
            "key" to "value"
        )
        builder.cacheControl = "no-cache"
        builder.expires = "Wed, 08 Jul 2015 16:57:01 GMT"
        builder.contentEncoding = "url"
        builder.contentDisposition = "attachment;filename=oss_download.jpg"
        builder.contentMd5 = "ohhnqLBJFiKkPSBO1eNaUA=="
        builder.initHashCRC64 = 10

        val request = AppendObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertNotNull(request.body)
        assertNotNull(request.progressListener)
        assertEquals(10, request.position)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("private", request.objectAcl)
        assertEquals("Standard", request.storageClass)
        assertEquals("value", request.metadata?.get("key"))
        assertEquals("no-cache", request.cacheControl)
        assertEquals("Wed, 08 Jul 2015 16:57:01 GMT", request.expires)
        assertEquals("url", request.contentEncoding)
        assertEquals("attachment;filename=oss_download.jpg", request.contentDisposition)
        assertEquals("ohhnqLBJFiKkPSBO1eNaUA==", request.contentMd5)
        assertEquals(10, request.initHashCRC64)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-meta-key")
            request.headers.containsKey("Content-Disposition")
            request.headers.containsKey("Content-Encoding")
            request.headers.containsKey("Content-MD5")
            request.headers.containsKey("Expires")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("position")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = AppendObjectResult {}
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
        val result = AppendObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-next-append-position" to "10",
                "x-oss-hash-crc64ecma" to "3231342946509354535"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(10, result.nextAppendPosition)
        assertEquals("3231342946509354535", result.hashCrc64ecma)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("10", result.headers["x-oss-next-append-position"])
        assertEquals("3231342946509354535", result.headers["x-oss-hash-crc64ecma"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = AppendObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-next-append-position" to "10",
            "x-oss-hash-crc64ecma" to "3231342946509354535"
        )

        val result = AppendObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(10, result.nextAppendPosition)
        assertEquals("3231342946509354535", result.hashCrc64ecma)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("10", result.headers["x-oss-next-append-position"])
        assertEquals("3231342946509354535", result.headers["x-oss-hash-crc64ecma"])
    }
}
