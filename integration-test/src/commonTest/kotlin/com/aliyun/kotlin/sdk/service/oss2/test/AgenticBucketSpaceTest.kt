package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.agentic.BucketSpaceHelper
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.paginator.listBucketSpacesPaginator
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

/**
 * The bucket space scenario: ListBucketSpaces, the bucket and object interfaces through the bucket
 * space client, and the [BucketSpaceHelper] name builder driving a plain client.
 *
 * Creating a bucket space needs the full name of the parent agentic bucket in
 * `x-oss-agentic-bucket`; the short name alone is not accepted. Everything runs in a single test
 * function so that only one agentic bucket is created per class (see [AgenticBucketBasicTest]).
 */
class AgenticBucketSpaceTest : AgenticTestBase() {

    private val bucketName: String = randomAgenticBucketName()
    private var bucketCreated: Boolean = false
    private var spaceCreated: Boolean = false

    @AfterTest
    fun disableAndReapBucket() = runTest(timeout = 5.minutes) {
        if (spaceCreated) {
            deleteBucketSpaceQuietly(bucketName)
        }
        if (!bucketCreated) return@runTest
        disableAndReap(bucketName)
    }

    @Test
    fun testAgenticBucketSpace() = runTest(timeout = 10.minutes) {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        // Flag first: if the create fails after the server made the bucket, teardown must still
        // disable it, otherwise it stays Enabled and the reaper can never reclaim it.
        bucketCreated = true
        val createResult = agenticClient.createAgenticBucket(CreateAgenticBucketRequest { bucket = bucketName })
        assertEquals(200, createResult.statusCode)

        // One bucket space, shared by the checks below. The short name is expanded by the bucket
        // space client, but the parent must be given as a full name.
        spaceCreated = true
        val putBucketResult = bucketSpaceClient.putBucket(
            PutBucketRequest {
                bucket = bucketName
                agenticBucket = buildFullName(bucketName, AGENTIC_BUCKET_SUFFIX)
            },
        )
        assertEquals(200, putBucketResult.statusCode)

        // ListBucketSpaces, directly and through the paginator.
        val listResult = agenticClient.listBucketSpaces(ListBucketSpacesRequest { bucket = bucketName })
        assertEquals(200, listResult.statusCode)

        var pages = 0
        agenticClient.listBucketSpacesPaginator(ListBucketSpacesRequest { bucket = bucketName }).collect {
            assertEquals(200, it.statusCode)
            pages++
        }
        assertTrue(pages >= 1, "the paginator should emit at least one page")

        // Bucket interfaces through the bucket space client.
        val putAclResult = bucketSpaceClient.putBucketAcl(
            PutBucketAclRequest {
                bucket = bucketName
                acl = "private"
            },
        )
        assertEquals(200, putAclResult.statusCode)

        val getAclResult = bucketSpaceClient.getBucketAcl(GetBucketAclRequest { bucket = bucketName })
        assertEquals(200, getAclResult.statusCode)
        assertEquals("private", getAclResult.accessControlPolicy?.accessControlList?.grant)

        // Object interfaces through the bucket space client.
        val objectKey = randomObjectKey()
        val putObjectResult = bucketSpaceClient.putObject(
            PutObjectRequest {
                bucket = bucketName
                key = objectKey
                body = ByteStream.fromString("hello world")
            },
        )
        assertEquals(200, putObjectResult.statusCode)

        bucketSpaceClient.getObject(
            GetObjectRequest {
                bucket = bucketName
                key = objectKey
            },
        ).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals("hello world", result.body?.toByteArray()?.decodeToString())
        }

        bucketSpaceClient.deleteObject(
            DeleteObjectRequest {
                bucket = bucketName
                key = objectKey
            },
        )

        // Drive the same space through a plain client using a helper built full name.
        val helper = BucketSpaceHelper(agenticTestConfig())
        val fullName = helper.toBucketName(bucketName)
        assertEquals(buildFullName(bucketName, BUCKET_SPACE_SUFFIX), fullName)

        val getInfoResult = defaultClient.getBucketInfo(GetBucketInfoRequest { bucket = fullName })
        assertEquals(200, getInfoResult.statusCode)

        val helperKey = randomObjectKey()
        val helperPutResult = defaultClient.putObject(
            PutObjectRequest {
                bucket = fullName
                key = helperKey
                body = ByteStream.fromString("hello helper")
            },
        )
        assertEquals(200, helperPutResult.statusCode)

        defaultClient.getObject(
            GetObjectRequest {
                bucket = fullName
                key = helperKey
            },
        ).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals("hello helper", result.body?.toByteArray()?.decodeToString())
        }

        defaultClient.deleteObject(
            DeleteObjectRequest {
                bucket = fullName
                key = helperKey
            },
        )
    }
}
