package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Part
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import com.aliyun.kotlin.sdk.service.oss2.paginator.listMultipartUploadsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listPartsPaginator
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.io.Buffer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ObjectMultipartTest: TestBase() {

    @Test
    fun testInitiateMultipartUpload() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals(200, result.statusCode)
        assertNotNull(result.uploadId)
    }

    @Test
    fun testInitiateMultipartUploadWithForbidOverwrite() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = key
                forbidOverwrite = true
            })
        }
        assertEquals(409, (exception.cause as ServiceException).statusCode)
        assertEquals("FileAlreadyExists", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testInitiateMultipartUploadWithStorageClass() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            storageClass = "IA"
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testInitiateMultipartUploadWithTagging() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            tagging = "a=b"
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testInitiateMultipartUploadWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.initiateMultipartUpload(InitiateMultipartUploadRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testUploadPart() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val uploadResult = defaultClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
            partNumber = 1
            body = ByteStream.fromString("Hello oss.")
        })
        assertEquals(200, uploadResult.statusCode)
        assertNotNull(200, uploadResult.eTag)
    }

    @Test
    fun testUploadPartWithCrc() = bucketTest { bucketName ->
        val key: String = randomObjectKey()
        OSSClient.create(ClientConfiguration.loadDefault().apply{
            region = OSS_TEST_REGION
            endpoint = OSS_TEST_ENDPOINT
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
            disableUploadCRC64Check = false
        }).use { client ->

            val result = client.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = key
            })
            val uploadResult = client.uploadPart(UploadPartRequest {
                bucket = bucketName
                this.key = key
                uploadId = result.uploadId
                partNumber = 1
                body = ByteStream.fromString("Hello oss.")
            })
            assertEquals(200, uploadResult.statusCode)
            assertNotNull(200, uploadResult.eTag)
        }
    }

    @Test
    fun testUploadPartWithProgress() = bucketTest { bucketName ->
        val key = randomObjectKey()
        var totalBytesTransferred: Long = 0

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        defaultClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
            partNumber = 1
            body = ByteStream.fromSource(Buffer().also { it0 ->
                repeat(1024) {
                    it0.write("Hello oss.".encodeToByteArray())
                }
            }, 10240)
            progressListener = ProgressListener { bytesSent, totalBytesSent, totalBytesExpectedToSend ->
                totalBytesTransferred += bytesSent
                assertEquals(totalBytesTransferred, totalBytesSent)
                assertEquals(totalBytesExpectedToSend, 10240)
            }
        })
        assertEquals(totalBytesTransferred, 10240)
    }

    @Test
    fun testUploadPartWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.uploadPart(UploadPartRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.uploadPart(UploadPartRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.uploadId is required")

        exception = assertFails { invalidClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            key = "objectKey"
            uploadId = "uploadId"
        }) }
        assertEquals(exception.message, "request.partNumber is required")

        exception = assertFails { invalidClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            key = "objectKey"
            uploadId = "uploadId"
            partNumber = 1
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testCompleteMultipartUpload() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val uploadResult = defaultClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
            partNumber = 1
            body = ByteStream.fromString("Hello oss.")
        })
        val completeResult = defaultClient.completeMultipartUpload(CompleteMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
            completeMultipartUpload = CompleteMultipartUpload {
                parts = listOf(Part{
                    partNumber = 1
                    eTag = uploadResult.eTag
                })
            }
        })
        assertEquals(200, completeResult.statusCode)
    }

    @Test
    fun testCompleteMultipartUploadWithForbidOverwrite() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        defaultClient.uploadPart(UploadPartRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
            partNumber = 1
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.completeMultipartUpload(CompleteMultipartUploadRequest {
                bucket = bucketName
                this.key = key
                uploadId = result.uploadId
                completeAll = "yes"
                forbidOverwrite = true
            })
        }
        assertEquals(409, (exception.cause as ServiceException).statusCode)
        assertEquals("FileAlreadyExists", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testCompleteMultipartUploadWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.completeMultipartUpload(CompleteMultipartUploadRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.completeMultipartUpload(CompleteMultipartUploadRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.completeMultipartUpload(CompleteMultipartUploadRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.uploadId is required")

        exception = assertFails { invalidClient.completeMultipartUpload(CompleteMultipartUploadRequest {
            bucket = bucketName
            key = "objectKey"
            uploadId = "uploadId"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testUploadPartCopy() = bucketTest { bucketName ->
        val key: String = randomObjectKey()
        val target = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = target
        })
        val result = defaultClient.uploadPartCopy(UploadPartCopyRequest {
            bucket = bucketName
            this.key = target
            uploadId = initResult.uploadId
            partNumber = 1
            sourceKey = key
            copySourceRange = "bytes=0~9"
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testUploadPartCopyWithCopySourceIfMatch() = bucketTest { bucketName ->
        val key = randomObjectKey()
        val target = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = target
        })
        val exception = assertFails {
            defaultClient.uploadPartCopy(UploadPartCopyRequest {
                bucket = bucketName
                this.key = target
                uploadId = initResult.uploadId
                partNumber = 1
                sourceKey = key
                copySourceIfMatch = "error"
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(412, (exception.cause as ServiceException).statusCode)
        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testUploadPartCopyWithCopySourceIfNoneMatch() = bucketTest { bucketName ->
        val key = randomObjectKey()
        val target = randomObjectKey()

        val result = defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = target
        })
        val exception = assertFails {
            defaultClient.uploadPartCopy(UploadPartCopyRequest {
                bucket = bucketName
                this.key = target
                uploadId = initResult.uploadId
                partNumber = 1
                sourceKey = key
                copySourceIfNoneMatch = result.headers["ETag"]
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(304, (exception.cause as ServiceException).statusCode)
        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
    }

//    @OptIn(ExperimentalTime::class)
//    @Test
//    fun testUploadPartCopyWithCopySourceIfUnmodifiedSince() = bucketTest { bucketName ->
//        val key = randomObjectKey()
//        val target = randomObjectKey()
//        val date = Clock.System.now().format(DateTimeComponents.Formats.RFC_1123)
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        delay(100)
//        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
//            bucket = bucketName
//            this.key = target
//        })
//        val exception = assertFails {
//            defaultClient.uploadPartCopy(UploadPartCopyRequest {
//                bucket = bucketName
//                this.key = target
//                uploadId = initResult.uploadId
//                partNumber = 1
//                sourceKey = key
//                copySourceIfUnmodifiedSince = date
//            })
//        }
//        assertTrue(exception.cause is ServiceException)
//        assertEquals(412, (exception.cause as ServiceException).statusCode)
//        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
//    }
//
//    @OptIn(ExperimentalTime::class)
//    @Test
//    fun testUploadPartCopyWithCopySourceIfModifiedSince() = bucketTest { bucketName ->
//        val key = randomObjectKey()
//        val target = randomObjectKey()
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        delay(100)
//        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
//            bucket = bucketName
//            this.key = target
//        })
//        val exception = assertFails {
//            defaultClient.uploadPartCopy(UploadPartCopyRequest {
//                bucket = bucketName
//                this.key = target
//                uploadId = initResult.uploadId
//                partNumber = 1
//                sourceKey = key
//                copySourceIfModifiedSince = Clock.System.now().format(DateTimeComponents.Formats.RFC_1123)
//            })
//        }
//        assertTrue(exception.cause is ServiceException)
//        assertEquals(304, (exception.cause as ServiceException).statusCode)
//        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
//    }

    @Test
    fun testUploadPartCopyWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.uploadPartCopy(UploadPartCopyRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.uploadPartCopy(UploadPartCopyRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.uploadPartCopy(UploadPartCopyRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.sourceKey is required")

        exception = assertFails { invalidClient.uploadPartCopy(UploadPartCopyRequest {
            bucket = bucketName
            key = "objectKey"
            sourceKey = "sourceKey"
        }) }
        assertEquals(exception.message, "request.uploadId is required")

        exception = assertFails { invalidClient.uploadPartCopy(UploadPartCopyRequest {
            bucket = bucketName
            key = "objectKey"
            uploadId = "uploadId"
            sourceKey = "sourceKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testAbortMultipartUpload() = bucketTest { bucketName ->
        val key: String = randomObjectKey()

        val result = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        val abortResult = defaultClient.abortMultipartUpload(AbortMultipartUploadRequest {
            bucket = bucketName
            this.key = key
            uploadId = result.uploadId
        })
        assertEquals(204, abortResult.statusCode)
    }

    @Test
    fun testAbortMultipartUploadWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.abortMultipartUpload(AbortMultipartUploadRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.abortMultipartUpload(AbortMultipartUploadRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.abortMultipartUpload(AbortMultipartUploadRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.uploadId is required")

        exception = assertFails { invalidClient.abortMultipartUpload(AbortMultipartUploadRequest {
            bucket = bucketName
            key = "objectKey"
            uploadId = "uploadId"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testListMultipartUploads() = bucketTest { bucketName ->
        val key = randomObjectKey()

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = key
            })
        }

        val result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
        })
        assertEquals(10, result.uploads?.size)
        assertEquals(false, result.isTruncated)

        result.uploads?.forEach {
            assertEquals(key, it.key)
            assertNotNull(it.uploadId)
        }
    }

    @Test
    fun testListMultipartUploadsWithMaxUploads() = bucketTest { bucketName ->
        val key = randomObjectKey()

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = key
            })
        }

        val result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
            maxUploads = 5
        })
        assertEquals(5, result.uploads?.size)
        assertEquals(true, result.isTruncated)

        result.uploads?.forEach {
            assertEquals(key, it.key)
            assertNotNull(it.uploadId)
        }
    }

    @Test
    fun testListMultipartUploadsWithPrefix() = bucketTest { bucketName ->
        val key = randomObjectKey()

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = "$key-$i"
            })
        }

        val result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
            prefix = key
        })
        assertEquals(10, result.uploads?.size)
        assertEquals(false, result.isTruncated)
        assertEquals(key, result.prefix)

        result.uploads?.forEach {
            assertEquals(true, it.key?.startsWith(key))
            assertNotNull(it.uploadId)
        }
    }

    @Test
    fun testListMultipartUploadsWithDelimiter() = bucketTest { bucketName ->

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = "key-$i"
            })
        }

        val result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
            delimiter = "a"
        })
        assertEquals(10, result.uploads?.size)
        assertEquals(false, result.isTruncated)
        assertEquals("a", result.delimiter)
    }

    @Test
    fun testListMultipartUploadsWithKeyMarker() = bucketTest { bucketName ->
        val key = randomObjectKey()

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = "$key-$i"
            })
        }

        var result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
            maxUploads = 5
        })
        assertEquals(5, result.uploads?.size)
        assertEquals(true, result.isTruncated)
        assertNotNull(result.nextKeyMarker)
        assertNotNull(result.nextUploadIdMarker)
        result.uploads?.forEach {
            assertEquals(true, it.key?.startsWith(key))
            assertNotNull(it.uploadId)
        }
        result = defaultClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
            maxUploads = 5
            keyMarker = result.nextKeyMarker
            uploadIdMarker = result.nextUploadIdMarker
        })
        assertEquals(5, result.uploads?.size)
        assertEquals(false, result.isTruncated)
        result.uploads?.forEach {
            assertEquals(true, it.key?.startsWith(key))
            assertNotNull(it.uploadId)
        }
    }

    @Test
    fun testListMultipartUploadsPaginator() = bucketTest { bucketName ->
        val key = randomObjectKey()

        for (i in 1..10) {
            defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
                bucket = bucketName
                this.key = "$key-$i"
            })
        }

        defaultClient.listMultipartUploadsPaginator(ListMultipartUploadsRequest {
            bucket = bucketName
            maxUploads = 5
        }, PaginatorOptions {
            limit = 5
        }).collect {
            assertEquals(5, it.uploads?.size)
        }
    }

    @Test
    fun testListMultipartUploadsWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.listMultipartUploads(ListMultipartUploadsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith { invalidClient.listMultipartUploads(ListMultipartUploadsRequest {
            bucket = bucketName
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testListParts() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        for (i in 1..10) {
            defaultClient.uploadPart(UploadPartRequest {
                bucket = bucketName
                this.key = key
                uploadId = initResult.uploadId
                partNumber = i.toLong()
                body = ByteStream.fromString("hello oss.")
            })
        }

        val result = defaultClient.listParts(ListPartsRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
        })
        assertEquals(key, result.key)
        assertEquals(initResult.uploadId, result.uploadId)
        assertEquals(10, result.parts?.size)
        assertEquals(false, result.isTruncated)

        result.parts?.forEach {
            assertEquals(10, it.size)
            assertNotNull(it.eTag)
            assertNotNull(it.lastModified)
            assertNotNull(it.partNumber)
        }
    }

    @Test
    fun testListPartsWithMaxParts() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        for (i in 1..10) {
            defaultClient.uploadPart(UploadPartRequest {
                bucket = bucketName
                this.key = key
                uploadId = initResult.uploadId
                partNumber = i.toLong()
                body = ByteStream.fromString("hello oss.")
            })
        }

        val result = defaultClient.listParts(ListPartsRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            maxParts = 5
        })
        assertEquals(key, result.key)
        assertEquals(initResult.uploadId, result.uploadId)
        assertEquals(5, result.parts?.size)
        assertEquals(true, result.isTruncated)

        result.parts?.forEach {
            assertEquals(10, it.size)
            assertNotNull(it.eTag)
            assertNotNull(it.lastModified)
            assertNotNull(it.partNumber)
        }
    }

    @Test
    fun testListPartsWithPartNumberMarker() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        for (i in 1..10) {
            defaultClient.uploadPart(UploadPartRequest {
                bucket = bucketName
                this.key = key
                uploadId = initResult.uploadId
                partNumber = i.toLong()
                body = ByteStream.fromString("hello oss.")
            })
        }

        var result = defaultClient.listParts(ListPartsRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            maxParts = 5
        })
        assertEquals(key, result.key)
        assertEquals(initResult.uploadId, result.uploadId)
        assertEquals(5, result.parts?.size)
        assertEquals(true, result.isTruncated)
        result.parts?.forEach {
            assertEquals(10, it.size)
            assertNotNull(it.eTag)
            assertNotNull(it.lastModified)
            assertNotNull(it.partNumber)
        }

        result = defaultClient.listParts(ListPartsRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
            partNumberMarker = result.nextPartNumberMarker
        })
        assertEquals(key, result.key)
        assertEquals(initResult.uploadId, result.uploadId)
        assertEquals(5, result.parts?.size)
        assertEquals(false, result.isTruncated)
        result.parts?.forEach {
            assertEquals(10, it.size)
            assertNotNull(it.eTag)
            assertNotNull(it.lastModified)
            assertNotNull(it.partNumber)
        }
    }

    @Test
    fun testListPartsPaginator() = bucketTest { bucketName ->
        val key = randomObjectKey()

        val initResult = defaultClient.initiateMultipartUpload(InitiateMultipartUploadRequest {
            bucket = bucketName
            this.key = key
        })
        for (i in 1..10) {
            defaultClient.uploadPart(UploadPartRequest {
                bucket = bucketName
                this.key = key
                uploadId = initResult.uploadId
                partNumber = i.toLong()
                body = ByteStream.fromString("hello oss.")
            })
        }

        defaultClient.listPartsPaginator(ListPartsRequest {
            bucket = bucketName
            this.key = key
            uploadId = initResult.uploadId
        }, PaginatorOptions {
            limit = 5
        }).collect {
            assertEquals(5, it.parts?.size)
        }
    }

    @Test
    fun testListPartsWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.listParts(ListPartsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith { invalidClient.listParts(ListPartsRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFailsWith { invalidClient.listParts(ListPartsRequest {
            bucket = bucketName
            key = "key"
        }) }
        assertEquals(exception.message, "request.uploadId is required")

        exception = assertFailsWith { invalidClient.listParts(ListPartsRequest {
            bucket = bucketName
            key = "key"
            uploadId = "uploadId"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}