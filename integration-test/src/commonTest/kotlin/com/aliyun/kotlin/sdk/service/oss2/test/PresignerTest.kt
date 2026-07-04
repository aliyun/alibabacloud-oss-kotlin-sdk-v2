package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PresignerTest: TestBase() {

    private suspend fun sendPresigned(
        method: String,
        url: String,
        signedHeaders: Map<String, String>,
        body: Any? = null,
    ): HttpResponse = HttpClient().use { client ->
        client.request(url) {
            this.method = HttpMethod.parse(method)
            signedHeaders.forEach { (k, v) -> header(k, v) }
            if (body != null) {
                setBody(body)
            }
        }
    }

    @Test
    fun testPresignPutObject() = bucketTest { bucketName ->
        val key = randomObjectKey()
        val result = defaultClient.presign(PutObjectRequest {
            bucket = bucketName
            this.key = key
            addHeader("Content-Type", "application/octet-stream")
        })
        assertEquals("PUT", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders, "Hello oss.".encodeToByteArray())
        assertEquals(200, response.status.value)
    }

    @Test
    fun testPresignGetObject() = bucketTest { bucketName ->
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.presign(GetObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("GET", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders)
        assertEquals(200, response.status.value)
        assertEquals("Hello oss.", response.bodyAsText())
    }

    @Test
    fun testPresignHeadObject() = bucketTest { bucketName ->
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.presign(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("HEAD", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders)
        assertEquals(200, response.status.value)
    }

    @Test
    fun testPresignInitiateMultipartUpload() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val result = defaultClient.presign(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            addHeader("Content-Type", "application/octet-stream")
        })
        assertEquals("POST", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders, ByteArray(0))
        assertEquals(200, response.status.value)
    }

    @Test
    fun testPresignUploadPart() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val result = defaultClient.presign(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            partNumber = 1
            addHeader("Content-Type", "application/octet-stream")
        })
        assertEquals("PUT", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders, "Hello oss.".encodeToByteArray())
        assertEquals(200, response.status.value)
    }

    @Test
    fun testPresignCompleteMultipartUpload() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val partResult = defaultClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            partNumber = 1
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.presign(CompleteMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            addHeader("Content-Type", "application/octet-stream")
        })
        assertEquals("POST", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(
            result.method,
            result.url,
            result.signedHeaders,
            "<CompleteMultipartUpload><Part><PartNumber>1</PartNumber><ETag>${partResult.eTag}</ETag></Part></CompleteMultipartUpload>".encodeToByteArray(),
        )
        assertEquals(200, response.status.value)
    }

    @Test
    fun testPresignAbortMultipartUpload() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val result = defaultClient.presign(AbortMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            addHeader("Content-Type", "application/octet-stream")
        })
        assertEquals("DELETE", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = sendPresigned(result.method, result.url, result.signedHeaders, "Hello oss.".encodeToByteArray())
        assertEquals(204, response.status.value)
    }
}