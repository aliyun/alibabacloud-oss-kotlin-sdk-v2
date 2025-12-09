package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ObjectSymlinkTest: TestBase() {

    val bucketName: String = randomBucketName()
    val objectKey: String = randomObjectKey()

    @BeforeTest
    fun putBucket() = runTest {
        defaultClient.putBucket(PutBucketRequest {
            bucket = bucketName
        })
        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            key = objectKey
            body = ByteStream.fromString("Hello oss.")
        })
    }

    @AfterTest
    fun cleanAndDeleteBucket() = runTest {
        defaultClient.listObjectsV2Paginator(
            ListObjectsV2Request {
                bucket = bucketName
            }
        ).collect {
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
    fun testPutSymlink() = runTest {
        val symlink = "symlink-$objectKey"
        val result = defaultClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = symlink
            this.symlinkTarget = objectKey
        })
        assertEquals(200, result.statusCode)
    }

    @Test
    fun testPutSymlinkWithObjectAcl() = runTest {
        val symlink = "symlink-$objectKey"
        defaultClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = symlink
            this.symlinkTarget = objectKey
            objectAcl = "private"
        })

        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            key = symlink
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
    }

    @Test
    fun testPutSymlinkWithStorageClass() = runTest {
        val symlink = "symlink-$objectKey"
        defaultClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = symlink
            this.symlinkTarget = objectKey
            storageClass = "IA"
        })
    }

    @Test
    fun testPutSymlinkWithForbidOverwrite() = runTest {
        val symlink = "symlink-$objectKey"

        defaultClient.putObject(PutObjectRequest {
            bucket = bucketName
            key = symlink
            body = ByteStream.fromString("Hello oss.")
        })
        val exception = assertFails {
            defaultClient.putSymlink(PutSymlinkRequest {
                bucket = bucketName
                key = symlink
                this.symlinkTarget = objectKey
                forbidOverwrite = true
            })
        }
        assertEquals(409, (exception.cause as ServiceException).statusCode)
        assertEquals("FileAlreadyExists", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testPutSymlinkWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putSymlink(PutSymlinkRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertEquals(exception.message, "request.symlinkTarget is required")

        exception = assertFails { invalidClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = "objectKey"
            symlinkTarget = "symlinkTarget"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetSymlink() = runTest {
        val symlink = "symlink-$objectKey"
        defaultClient.putSymlink(PutSymlinkRequest {
            bucket = bucketName
            key = symlink
            this.symlinkTarget = objectKey
        })
        val result = defaultClient.getSymlink(GetSymlinkRequest {
            bucket = bucketName
            key = symlink
        })
        assertEquals(objectKey, result.symlinkTarget)
    }

    @Test
    fun testGetSymlinkWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getSymlink(GetSymlinkRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.getSymlink(GetSymlinkRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.getSymlink(GetSymlinkRequest {
            bucket = bucketName
            key = "objectKey"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}