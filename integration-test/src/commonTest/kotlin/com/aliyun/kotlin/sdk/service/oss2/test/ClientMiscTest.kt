package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class ClientMiscTest: TestBase() {

    @Test
    fun testInvokeOperation() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        // put object
        val putResult = defaultClient.invokeOperation(OperationInput {
            method = "PUT"
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })
        assertEquals(200, putResult.statusCode)

        // get object
        val getResult = defaultClient.invokeOperation(OperationInput {
            method = "GET"
            this.bucket = bucket
            this.key = key
        })
        assertEquals(200, getResult.statusCode)
        assertEquals("Hello oss.", getResult.body?.decodeToString())

        // head object
        val headResult = defaultClient.invokeOperation(OperationInput {
            method = "HEAD"
            this.bucket = bucket
            this.key = key
        })
        assertEquals(200, headResult.statusCode)

        // delete object
        val deleteResult = defaultClient.invokeOperation(OperationInput {
            method = "DELETE"
            this.bucket = bucket
            this.key = key
        })
        assertEquals(204, deleteResult.statusCode)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testDoesBucketExist() = runTest {
        val bucket = randomBucketName()

        // bucket is not exist
        assertEquals(false, defaultClient.doesBucketExist(bucket))

        // bucket is exist
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        assertEquals(true, defaultClient.doesBucketExist(bucket))

        // throw exception
        val exception = assertFails {
            invalidClient.doesBucketExist(bucket)
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(403, (exception.cause as ServiceException).statusCode)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testDoesObjectExist() = runTest {
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
        // object is not exist
        assertEquals(false, defaultClient.doesObjectExist(bucket, key))

        // object is exist
        val result = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss")
        })
        assertEquals(true, defaultClient.doesObjectExist(bucket, key, result.versionId))

        // throw exception
        val exception = assertFails {
            invalidClient.doesObjectExist(bucket, key)
        }
        assertTrue(exception.cause is ServiceException)
        assertEquals(403, (exception.cause as ServiceException).statusCode)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            versionId = result.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }
}