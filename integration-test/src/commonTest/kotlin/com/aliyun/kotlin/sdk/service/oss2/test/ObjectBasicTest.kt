package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.hash.md5
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CleanRestoredObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Delete
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectIdentifier
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.JobParameters
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreRequest
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.types.toFlow
import io.ktor.util.encodeBase64
import kotlinx.coroutines.test.runTest
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.random.Random
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ObjectBasicTest: TestBase() {

    private val bucketName = randomBucketName()

    @BeforeTest
    fun putBucket() = runTest {
        defaultClient.putBucket(PutBucketRequest {
            bucket = bucketName
        })
    }

    @AfterTest
    fun cleanAndDeleteBucket() = runTest {
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
    fun testPutAndGetObject() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.getObject(GetObjectRequest{
            bucket = bucketName
            this.key = key
        })
        assertEquals(200, result.statusCode)
        assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())
        assertIs<ByteStream.Buffer>(result.body)
        result.close()
        result.abort()

        defaultClient.getObject(GetObjectRequest{
            bucket = bucketName
            this.key = key
        }).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())
            assertIs<ByteStream.Buffer>(result.body)
            result.close()
            result.abort()
        }
    }

    @Test
    fun testGetObjectAsStream() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString( "Hello oss.")
        })

         defaultClient.getObjectAsStream(GetObjectRequest{
            bucket = bucketName
            this.key = key
        }).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())
            assertIs<ByteStream.SourceStream>(result.body)
        }
    }


    @Test
    fun testGetObjectAsStreamWithLargeData() = runTest {
        val key = randomObjectKey()
        val length = 1024 *1024 + 1234
        val data = Random.nextBytes(length)

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromBytes(data)
        })

        val result = defaultClient.getObjectAsStream(GetObjectRequest{
            bucket = bucketName
            this.key = key
        })

        assertEquals(200, result.statusCode)
        assertEquals(length.toLong(), result.contentLength)
        assertIs<ByteStream.SourceStream>(result.body)
        result.close()

        defaultClient.getObjectAsStream(GetObjectRequest{
            bucket = bucketName
            this.key = key
        }).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals(length.toLong(), result.contentLength)

            val buffer = Buffer()
            result.body?.toFlow()?.collect {
                buffer.write(it)
            }
            assertEquals(data.md5().encodeBase64(), buffer.readByteArray().md5().encodeBase64())
        }
    }


    @Test
    fun testPutObjectWithFile() = runTest {
        val key = randomObjectKey()
        val path = createTestFile("file", "Hello oss.".toByteArray())

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromFile(path)
        })
        val result = defaultClient.getObject(GetObjectRequest{
            bucket = bucketName
            this.key = key
        })
        assertEquals(200, result.statusCode)
        assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())

        removeTestFile("file")
    }

    @Test
    fun testPutObjectWithCrc() = runTest {
        val key = randomObjectKey()
        OSSClient.create(ClientConfiguration.loadDefault().apply{
            region = OSS_TEST_REGION
            endpoint = OSS_TEST_ENDPOINT
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
            disableUploadCRC64Check = false
        }).use { client->
            client.putObject(PutObjectRequest {
                bucket = bucketName
                this.key = key
                body = ByteStream.fromString("Hello oss.")
            })
            val result = defaultClient.getObject(GetObjectRequest{
                bucket = bucketName
                this.key = key
            })
            assertEquals(200, result.statusCode)
            assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())
        }

    }

    @Test
    fun testPutObjectWithProgress() = runTest {
        val key = randomObjectKey()
        var totalBytesTransferred: Long = 0

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromSource(Buffer().also { it0 ->
                repeat(1024) {
                    it0.write("Hello oss.".toByteArray())
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
    fun testPutObjectWithForbidOverwrite() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.putObject(PutObjectRequest {
                bucket = bucketName
                this.key = key
                body = ByteStream.fromString("Hello oss.")
                forbidOverwrite = true
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(409, (exception.cause as ServiceException).statusCode)
        assertEquals("FileAlreadyExists", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testPutObjectWithObjectAcl() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            objectAcl = "private"
        })
        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
    }

    @Test
    fun testPutObjectWithStorageClass() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            storageClass = "IA"
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("IA", result.storageClass)
    }

    @Test
    fun testPutObjectWithTagging() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            tagging = "a=b"
        })
        val result = defaultClient.getObjectTagging(GetObjectTaggingRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals(1, result.tagging?.tagSet?.tags?.size)
        assertEquals("a", result.tagging?.tagSet?.tags?.first()?.key)
        assertEquals("b", result.tagging?.tagSet?.tags?.first()?.value)
    }

    @Test
    fun testPutObjectWithMetadata() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            metadata = mapOf(Pair("a", "b"))
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals(1, result.metadata?.size)
        assertEquals("a", result.metadata?.entries?.first()?.key)
        assertEquals("b", result.metadata?.entries?.first()?.value)
    }

    @Test
    fun testPutObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putObject(PutObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.putObject(PutObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.putObject(PutObjectRequest {
            bucket = bucketName
            key = "objectKey"
            body = ByteStream.fromString("")
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetObjectToFile() = runTest {
        val key = randomObjectKey()
        val path = createTestFile("file", 1024 * 1024 + 1234)
        val downloadPath = Path("$TEST_FILE_TEMP_DIR/downloadFile")

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromFile(path)
        })
        val result = defaultClient.getObjectToFile(GetObjectRequest {
            bucket = bucketName
            this.key = key
        }, downloadPath)

        assertEquals(
            SystemFileSystem.source(path).buffered().use { it.readByteArray().md5().encodeBase64() },
            SystemFileSystem.source(downloadPath).buffered().use { it.readByteArray().md5().encodeBase64() })

        assertNull(result.body)
        assertEquals("OK", result.status)
        assertEquals(200, result.statusCode)
        assertEquals("application/octet-stream", result.contentType)
        assertEquals("Normal", result.objectType)

        removeTestFile("file")
    }

    @Test
    fun testGetObjectToFileWithProgressListener() = runTest {
        val key = randomObjectKey()
        val path = createTestFile("file", 1024 * 1024 + 1234)
        val downloadPath = Path("$TEST_FILE_TEMP_DIR/downloadFile")
        var totalBytesTransferred: Long = 0

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromFile(path)
            progressListener = ProgressListener { bytesSent, totalBytesSent, totalBytesExpectedToSend ->
                totalBytesTransferred += bytesSent
                assertEquals(totalBytesTransferred, totalBytesSent)
                assertEquals(totalBytesExpectedToSend, 1024 * 1024 + 1234)
            }
        })
        val result = defaultClient.getObjectToFile(
            GetObjectRequest {
                bucket = bucketName
                this.key = key
            }, downloadPath)

        assertEquals(
            SystemFileSystem.source(path).buffered().use{it.readByteArray().md5().encodeBase64()},
            SystemFileSystem.source(downloadPath).buffered().use{it.readByteArray().md5().encodeBase64()}
        )
        assertEquals(1024 * 1024 + 1234, totalBytesTransferred)

        assertNull(result.body)
        assertEquals("OK", result.status)
        assertEquals(200, result.statusCode)
        assertEquals("application/octet-stream", result.contentType)
        assertEquals("Normal", result.objectType)

        removeTestFile("file")
    }

    @Test
    fun testGetObjectWithRange() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.getObject(GetObjectRequest {
            bucket = bucketName
            this.key = key
            range = "bytes=6-8"
        })
        assertEquals("oss", result.body?.toByteArray()?.decodeToString())
    }

    @Test
    fun testGetObjectWithIfMatch() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.getObject(GetObjectRequest {
                bucket = bucketName
                this.key = key
                ifMatch = "error"
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(412, (exception.cause as ServiceException).statusCode)
        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testGetObjectWithIfNoneMatch() = runTest {
        val key = randomObjectKey()

        val result = defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.getObject(GetObjectRequest {
                bucket = bucketName
                this.key = key
                ifNoneMatch = result.eTag
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(304, (exception.cause as ServiceException).statusCode)
        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testGetObjectWithResponseParameter() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.getObject(GetObjectRequest {
            bucket = bucketName
            this.key = key
            responseExpires = "10"
            responseCacheControl = "cache"
            responseContentDisposition = "disposition"
            responseContentEncoding = "url"
            responseContentLanguage = "ch"
        })
        assertEquals("10", result.headers["expires"])
        assertEquals("cache", result.headers["cache-control"])
        assertEquals("disposition", result.headers["content-disposition"])
        assertEquals("url", result.headers["content-encoding"])
        assertEquals("ch", result.headers["content-language"])
    }

    @Test
    fun testGetObjectWithVersionId() = runTest {
        val key = randomObjectKey()

        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            bucket = bucketName
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        val putResult = defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.getObject(GetObjectRequest {
            bucket = bucketName
            this.key = key
            versionId = putResult.versionId
        })
        assertEquals("Hello oss.", result.body?.toByteArray()?.decodeToString())

        defaultClient.deleteObject(DeleteObjectRequest {
            bucket = bucketName
            this.key = key
            versionId = putResult.versionId
        })
    }

    @Test
    fun testCopyObject() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            sourceBucket = bucketName
            sourceKey = key
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testCopyObjectWithForbidOverwrite() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.copyObject(CopyObjectRequest {
                bucket = bucketName
                this.key = "new-$key"
                sourceBucket = bucketName
                sourceKey = key
                forbidOverwrite = true
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(409, (exception.cause as ServiceException).statusCode)
        assertEquals("FileAlreadyExists", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testCopyObjectWithCopySourceIfMatch() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.copyObject(CopyObjectRequest {
                bucket = bucketName
                this.key = "new-$key"
                sourceBucket = bucketName
                sourceKey = key
                copySourceIfMatch = "error"
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(412, (exception.cause as ServiceException).statusCode)
        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testCopyObjectWithCopySourceIfNoneMatch() = runTest {
        val key = randomObjectKey()

        val result = defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.copyObject(CopyObjectRequest {
                bucket = bucketName
                this.key = "new-$key"
                sourceBucket = bucketName
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
//    fun testCopyObjectWithCopySourceIfUnmodifiedSince() = runTest {
//        val key = randomObjectKey()
//        val date = Clock.System.now().format(DateTimeComponents.Formats.RFC_1123)
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        delay(100)
//        val exception = assertFails {
//            defaultClient.copyObject(CopyObjectRequest {
//                bucket = bucketName
//                this.key = "new-$key"
//                sourceBucket = bucketName
//                sourceKey = key
//                copySourceIfUnmodifiedSince = date
//            })
//        }
//        assertTrue(exception.cause is ServiceException)
//        assertEquals(412, (exception.cause as ServiceException).statusCode)
//        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
//    }

//    @OptIn(ExperimentalTime::class)
//    @Test
//    fun testCopyObjectWithCopySourceIfModifiedSince() = runTest {
//        val key = randomObjectKey()
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        delay(100)
//        val exception = assertFails {
//            defaultClient.copyObject(CopyObjectRequest {
//                bucket = bucketName
//                this.key = "new-$key"
//                sourceBucket = bucketName
//                sourceKey = key
//                copySourceIfModifiedSince = Clock.System.now().format(DateTimeComponents.Formats.RFC_1123)
//            })
//        }
//        assertTrue(exception.cause is ServiceException)
//        assertEquals(304, (exception.cause as ServiceException).statusCode)
//        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
//    }

    @Test
    fun testCopyObjectWithAcl() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            sourceBucket = bucketName
            sourceKey = key
            objectAcl = "private"
        })
        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            this.key = "new-$key"
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
    }

    @Test
    fun testCopyObjectWithStorageClass() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            sourceBucket = bucketName
            sourceKey = key
            storageClass = "IA"
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
        })
        assertEquals("IA", result.storageClass)
    }

    @Test
    fun testCopyObjectWithTagging() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            sourceBucket = bucketName
            sourceKey = key
            tagging = "a=b"
            taggingDirective = "Replace"
        })
        val result = defaultClient.getObjectTagging(GetObjectTaggingRequest {
            bucket = bucketName
            this.key = "new-$key"
        })
        assertEquals(1, result.tagging?.tagSet?.tags?.size)
        assertEquals("a", result.tagging?.tagSet?.tags?.first()?.key)
        assertEquals("b", result.tagging?.tagSet?.tags?.first()?.value)
    }

    @Test
    fun testCopyObjectWithMetadata() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
            sourceBucket = bucketName
            sourceKey = key
            metadata = mapOf(Pair("a", "b"))
            metadataDirective = "Replace"
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = "new-$key"
        })
        assertEquals(1, result.metadata?.size)
        assertEquals("a", result.metadata?.entries?.first()?.key)
        assertEquals("b", result.metadata?.entries?.first()?.value)
    }

    @Test
    fun testCopyObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.copyObject(CopyObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.copyObject(CopyObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.sourceKey is required")

        exception = assertFails { invalidClient.copyObject(CopyObjectRequest {
            bucket = bucketName
            key = "objectKey"
            sourceKey = "new-$key"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testAppendObject() = runTest {
        val key = randomObjectKey()

        val result = defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = 0
            body = ByteStream.fromString("Hello")
        })
        defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = result.nextAppendPosition
            body = ByteStream.fromString(" oss.")
        })
        val getResult = defaultClient.getObject(GetObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("Hello oss.", getResult.body?.toByteArray()?.let { String(it) })
    }

    @Test
    fun testAppendObjectWithCrc() = runTest {
        val key = randomObjectKey()
        OSSClient.create(ClientConfiguration.loadDefault().apply{
            region = OSS_TEST_REGION
            endpoint = OSS_TEST_ENDPOINT
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
            disableUploadCRC64Check = false
        }).use { client ->
            val result = client.appendObject(AppendObjectRequest {
                bucket = bucketName
                this.key = key
                position = 0
                body = ByteStream.fromString("Hello")
            })
            client.appendObject(AppendObjectRequest {
                bucket = bucketName
                this.key = key
                position = result.nextAppendPosition
                body = ByteStream.fromString(" oss.")
                initHashCRC64 = result.hashCrc64ecma?.toULong()?.toLong()
            })
            val getResult = defaultClient.getObject(GetObjectRequest {
                bucket = bucketName
                this.key = key
            })
            assertEquals("Hello oss.", getResult.body?.toByteArray()?.let { String(it) })
        }

    }

    @Test
    fun testAppendObjectWithProgress() = runTest {
        val key = randomObjectKey()
        var totalBytesTransferred: Long = 0

        defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = 0
            body = ByteStream.fromSource(Buffer().also { it0 ->
                repeat(1024) {
                    it0.write("Hello oss.".toByteArray())
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
    fun testAppendObjectWithObjectAcl() = runTest {
        val key = randomObjectKey()

        defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = 0
            body = ByteStream.fromString("Hello oss.")
            objectAcl = "private"
        })
        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
    }

    @Test
    fun testAppendObjectWithStorageClass() = runTest {
        val key = randomObjectKey()

        defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = 0
            body = ByteStream.fromString("Hello oss.")
            storageClass = "IA"
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("IA", result.storageClass)
    }

    @Test
    fun testAppendObjectWithMetadata() = runTest {
        val key = randomObjectKey()

        defaultClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            this.key = key
            position = 0
            body = ByteStream.fromString("Hello oss.")
            metadata = mapOf(Pair("a", "b"))
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals(1, result.metadata?.size)
        assertEquals("a", result.metadata?.entries?.first()?.key)
        assertEquals("b", result.metadata?.entries?.first()?.value)
    }

    @Test
    fun testAppendObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.appendObject(AppendObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.appendObject(AppendObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.appendObject(AppendObjectRequest {
            bucket = bucketName
            key = "objectKey"
            position = 0
            body = ByteStream.fromString("Hello oss.")
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteObject() =runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.deleteObject(DeleteObjectRequest {
            bucket = bucketName
            this.key = key
        })
        val exception = assertFails {
            defaultClient.headObject(HeadObjectRequest {
                bucket = bucketName
                this.key = key
            })
        }
        assertEquals(404, (exception.cause as ServiceException).statusCode)
    }

    @Test
    fun testDeleteObjectWithVersionId() =runTest {
        val key = randomObjectKey()
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        val result = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        val exception = assertFails {
            defaultClient.headObject(HeadObjectRequest {
                this.bucket = bucket
                this.key = key
            })
        }
        assertEquals(404, (exception.cause as ServiceException).statusCode)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testDeleteObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.deleteObject(DeleteObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.deleteObject(DeleteObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.deleteObject(DeleteObjectRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testHeadObject() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.headObject(HeadObjectRequest {
            bucket = bucketName
            this.key = key
        })
        assertEquals("Standard", result.storageClass)
        assertNotNull(result.hashCrc64ecma)
        assertNotNull(result.lastModified)
        assertNotNull(result.objectType)
        assertNotNull(result.contentMd5)
        assertNotNull(result.contentType)
        assertNotNull(result.eTag)
        assertNotNull(result.contentLength)
    }

//    @OptIn(ExperimentalTime::class)
//    @Test
//    fun testHeadObjectWithIfModifiedSince() = runTest {
//        val key = randomObjectKey()
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        val exception = assertFails {
//            defaultClient.headObject(HeadObjectRequest {
//                bucket = bucketName
//                this.key = key
//                ifModifiedSince = Clock.System.now().plus(60.minutes).format(DateTimeComponents.Formats.RFC_1123)
//            })
//        }
//        assertEquals(304, (exception.cause as ServiceException).statusCode)
//        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
//    }

    @Test
    fun testHeadObjectWithIfMatch() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.headObject(HeadObjectRequest {
                bucket = bucketName
                this.key = key
                ifMatch = "error"
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(412, (exception.cause as ServiceException).statusCode)
        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testHeadObjectWithIfNoneMatch() = runTest {
        val key = randomObjectKey()

        val result = defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.headObject(HeadObjectRequest {
                bucket = bucketName
                this.key = key
                ifNoneMatch = result.headers["ETag"]
            })
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(304, (exception.cause as ServiceException).statusCode)
        assertEquals("BadErrorResponse", (exception.cause as ServiceException).errorCode)
    }

//    @Test
//    fun testHeadObjectWithIfUnmodifiedSince() = runTest {
//        val key = randomObjectKey()
//
//        defaultClient.putObject(PutObjectRequest {
//            bucket = bucketName
//            this.key = key
//            body = ByteStream.fromString("Hello oss.")
//        })
//        val exception = assertFails {
//            val r = defaultClient.headObject(HeadObjectRequest {
//                bucket = bucketName
//                this.key = key
//                ifUnmodifiedSince = "Mon, 03 Nov 2025 04:48:15 GMT"
//            })
//        }
//        assertTrue(exception.cause is ServiceException)
//        assertEquals(412, (exception.cause as ServiceException).statusCode)
//        assertEquals("PreconditionFailed", (exception.cause as ServiceException).errorCode)
//    }

    @Test
    fun testHeadObjectWithVersionId() = runTest {
        val key = randomObjectKey()
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        val result = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val headResult = defaultClient.headObject(HeadObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        assertEquals(200, headResult.statusCode)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testHeadObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.headObject(HeadObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.headObject(HeadObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.headObject(HeadObjectRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetObjectMeta() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val result = defaultClient.getObjectMeta(GetObjectMetaRequest {
            bucket = bucketName
            this.key = key
        })
        assertNotNull(result.lastModified)
        assertNotNull(result.eTag)
        assertNotNull(result.contentLength)
    }

    @Test
    fun testGetObjectMetaWithVersionId() =runTest {
        val key = randomObjectKey()
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        val result = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        val headResult = defaultClient.getObjectMeta(GetObjectMetaRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        assertEquals(200, headResult.statusCode)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testGetObjectMetaWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectMeta(GetObjectMetaRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectMeta(GetObjectMetaRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.getObjectMeta(GetObjectMetaRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testRestoreObject() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            storageClass = "Archive"
        })
        defaultClient.restoreObject(RestoreObjectRequest {
            bucket = bucketName
            this.key = key
            restoreRequest = RestoreRequest {
                days = 1
            }
        })
    }

    @Test
    fun testRestoreObjectWithColdArchive() = runTest {
        val key = randomObjectKey()

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            storageClass = "ColdArchive"
        })
        defaultClient.restoreObject(RestoreObjectRequest {
            bucket = bucketName
            this.key = key
            restoreRequest = RestoreRequest {
                days = 1
                jobParameters = JobParameters {
                    tier = "Standard"
                }
            }
        })
    }

    @Test
    fun testRestoreObjectWithVersionId() = runTest {
        val key = randomObjectKey()
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        val result = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
            storageClass = "Archive"
        })
        val headResult = defaultClient.restoreObject(RestoreObjectRequest {
            this.bucket = bucket
            this.key = key
            restoreRequest = RestoreRequest {
                days = 7
            }
            versionId = result.versionId
        })
        assertEquals(202, headResult.statusCode)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testRestoreObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.restoreObject(RestoreObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.restoreObject(RestoreObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.restoreObject(RestoreObjectRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.restoreRequest is required")

        exception = assertFails { invalidClient.restoreObject(RestoreObjectRequest {
            bucket = bucketName
            key = "objectKey"
            restoreRequest = RestoreRequest {}
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testCleanRestoredObjectWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.cleanRestoredObject(CleanRestoredObjectRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.cleanRestoredObject(CleanRestoredObjectRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.cleanRestoredObject(CleanRestoredObjectRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteMultipleObjects() = runTest {
        val key = randomObjectKey()

        val objectIdentifiers: MutableList<ObjectIdentifier> = mutableListOf()
        for (i in 1..10) {
            defaultClient.putObject(PutObjectRequest {
                bucket = bucketName
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
            objectIdentifiers.add(ObjectIdentifier {
                this.key = "$key-$i"
            })
        }

        val result = defaultClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {
            bucket = bucketName
            delete = Delete {
                this.objects = objectIdentifiers
            }
        })
        assertEquals(200, result.statusCode)
        assertEquals(10, result.deletedObjects?.size)
    }

    @Test
    fun testDeleteMultipleObjectsWithQuiet() = runTest {
        val key = randomObjectKey()

        val objectIdentifiers: MutableList<ObjectIdentifier> = mutableListOf()
        for (i in 1..10) {
            defaultClient.putObject(PutObjectRequest {
                bucket = bucketName
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
            objectIdentifiers.add(ObjectIdentifier {
                this.key = "$key-$i"
            })
        }

        val result = defaultClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {
            bucket = bucketName
            delete = Delete {
                quiet = true
                this.objects = objectIdentifiers
            }
        })
        assertEquals(200, result.statusCode)
        assertNull(result.deletedObjects)
    }

    @Test
    fun testDeleteMultipleObjectsWithVersionId() = runTest {
        val key = randomObjectKey()

        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            bucket = bucketName
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        val objectIdentifiers: MutableList<ObjectIdentifier> = mutableListOf()
        for (i in 1..10) {
            val result = defaultClient.putObject(PutObjectRequest {
                bucket = bucketName
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
            objectIdentifiers.add(ObjectIdentifier {
                this.key = "$key-$i"
                versionId = result.versionId
            })
        }

        val result = defaultClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {
            bucket = bucketName
            delete = Delete {
               this.objects = objectIdentifiers
            }
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testDeleteMultipleObjectsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.delete is required")

        exception = assertFails { invalidClient.deleteMultipleObjects(DeleteMultipleObjectsRequest {
            bucket = bucketName
            delete = Delete {
                objects = listOf()
            }
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
