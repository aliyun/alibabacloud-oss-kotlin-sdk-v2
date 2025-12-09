package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listMultipartUploadsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PresignerTest: TestBase() {

    private val bucketName = randomBucketName()

    @BeforeTest
    fun putBucket() = runTest {
        defaultClient.putBucket(PutBucketRequest {
            bucket = bucketName
        })
    }

    @AfterTest
    fun cleanAndDeleteBucket() = runTest {
        defaultClient.listMultipartUploadsPaginator(ListMultipartUploadsRequest {
            bucket = bucketName
        }).collect {
            it.uploads?.forEach { upload ->
                defaultClient.abortMultipartUpload(AbortMultipartUploadRequest {
                    bucket = bucketName
                    key = upload.key
                    uploadId = upload.uploadId
                })
            }
        }
        defaultClient.listObjectsV2Paginator(ListObjectsV2Request {
            bucket = bucketName
        }).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    bucket = bucketName
                    key = obj.key
                })
            }
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            bucket = bucketName
        })
    }

    @Test
    fun testPresignPutObject() = runTest {
        val key = randomObjectKey()
        val result = defaultClient.presign(PutObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("PUT", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, "Hello oss.".toByteArray().toRequestBody())
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
    }

    @Test
    fun testPresignGetObject() = runTest {
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

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, null)
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
        assertEquals("Hello oss.", response.body.string())
    }

    @Test
    fun testPresignHeadObject() = runTest {
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

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, null)
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
    }

    @Test
    fun testPresignInitiateMultipartUpload() = runTest {
        val key = randomObjectKey()

        val result = defaultClient.presign(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("POST", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, RequestBody.EMPTY)
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
    }

    @Test
    fun testPresignUploadPart() = runTest {
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
        })
        assertEquals("PUT", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, "Hello oss.".toByteArray().toRequestBody())
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
    }

    @Test
    fun testPresignCompleteMultipartUpload() = runTest {
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
        })
        assertEquals("POST", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, "<CompleteMultipartUpload><Part><PartNumber>1</PartNumber><ETag>${partResult.eTag}</ETag></Part></CompleteMultipartUpload>".toRequestBody())
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(200, response.code)
    }

    @Test
    fun testPresignAbortMultipartUpload() = runTest {
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val result = defaultClient.presign(AbortMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
        })
        assertEquals("DELETE", result.method)
        assertTrue(result.url.contains("x-oss-signature-version="))
        assertTrue(result.url.contains("x-oss-expires="))
        assertTrue(result.url.contains("x-oss-credential="))
        assertTrue(result.url.contains("x-oss-signature="))

        val response = OkHttpClient.Builder().build().newCall(Request.Builder().apply {
            method(result.method, "Hello oss.".toByteArray().toRequestBody())
            url(result.url)
            result.signedHeaders.forEach { (k, v) -> header(k, v) }
        }.build()).execute()
        assertEquals(204, response.code)
    }
}