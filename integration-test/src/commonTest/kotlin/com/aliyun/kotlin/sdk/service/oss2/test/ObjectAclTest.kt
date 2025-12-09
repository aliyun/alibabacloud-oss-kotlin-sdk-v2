package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ObjectAclTest: TestBase() {

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
    fun testPutAndGetObjectAcl() = runTest {
        defaultClient.putObjectAcl(PutObjectAclRequest {
            bucket = bucketName
            key = objectKey
            objectAcl = "private"
        })
        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            key = objectKey
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
        assertNotNull(result.accessControlPolicy?.owner?.id)
        assertNotNull(result.accessControlPolicy?.owner?.displayName)
    }

    @Test
    fun testPutAndGetObjectAclWithVersionId() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        val objectResult = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        defaultClient.putObjectAcl(PutObjectAclRequest {
            this.bucket = bucket
            this.key = key
            objectAcl = "private"
            versionId = objectResult.versionId
        })
        val result = defaultClient.getObjectAcl(GetObjectAclRequest {
            this.bucket = bucket
            this.key = key
            versionId = objectResult.versionId
        })
        assertEquals("private", result.accessControlPolicy?.accessControlList?.grant)
        assertNotNull(result.accessControlPolicy?.owner?.id)
        assertNotNull(result.accessControlPolicy?.owner?.displayName)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = objectResult.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutObjectAclWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putObjectAcl(PutObjectAclRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.putObjectAcl(PutObjectAclRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.putObjectAcl(PutObjectAclRequest {
            bucket = bucketName
            key = objectKey
        }) }
        assertEquals(exception.message, "request.objectAcl is required")

        exception = assertFails { invalidClient.putObjectAcl(PutObjectAclRequest {
            bucket = bucketName
            key = objectKey
            objectAcl = "private"
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetObjectAclWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectAcl(GetObjectAclRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> { invalidClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
        }) }
        assertEquals(exception.message, "request.key is required")

        exception = assertFails { invalidClient.getObjectAcl(GetObjectAclRequest {
            bucket = bucketName
            key = objectKey
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}