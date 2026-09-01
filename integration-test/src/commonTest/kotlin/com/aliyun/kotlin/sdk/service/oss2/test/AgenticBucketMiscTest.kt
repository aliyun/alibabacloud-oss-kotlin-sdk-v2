package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectResult
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.minutes

/**
 * Miscellaneous agentic scenarios that belong to none of the Basic, Lifecycle or Space suites.
 *
 * Path-style addressing puts the resolved full name in the request path instead of the leftmost
 * host label. The endpoint may refuse it with `SecondLevelDomainForbidden`, which is an endpoint
 * capability and not an SDK defect, so each probe skips instead of failing in that case.
 */
class AgenticBucketMiscTest : AgenticTestBase() {

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
    fun testPathStyle() = runTest(timeout = 10.minutes) {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        // Create the bucket with the default (virtual-hosted) client so the fixture stands
        // regardless of whether path-style turns out to be allowed.
        // Flag first: if the create fails after the server made the bucket, teardown must still
        // disable it, otherwise it stays Enabled and the reaper can never reclaim it.
        bucketCreated = true
        val createResult = agenticClient.createAgenticBucket(CreateAgenticBucketRequest { bucket = bucketName })
        assertEquals(200, createResult.statusCode)

        // Probe: a path-style GET carrying the bucket. ListAgenticBuckets is service level (no
        // bucket label) so its URL is identical in both styles and cannot probe path-style;
        // GetAgenticBucket carries the bucket and does.
        val getResult: GetAgenticBucketResult = try {
            pathStyleAgenticClient.getAgenticBucket(GetAgenticBucketRequest { bucket = bucketName })
        } catch (error: Throwable) {
            if (isSecondLevelDomainForbidden(error)) {
                skip("path-style addressing is not allowed on this endpoint (SecondLevelDomainForbidden)")
                return@runTest
            }
            throw error
        }
        assertEquals(200, getResult.statusCode)
        assertNotNull(getResult.agenticBucketInfo)

        // Agentic bucket client over path-style.
        val listResult = pathStyleAgenticClient.listBucketSpaces(ListBucketSpacesRequest { bucket = bucketName })
        assertEquals(200, listResult.statusCode)

        // One bucket space, created with the default client, for the path-style checks below.
        spaceCreated = true
        val putBucketResult = bucketSpaceClient.putBucket(
            PutBucketRequest {
                bucket = bucketName
                agenticBucket = buildFullName(bucketName, AGENTIC_BUCKET_SUFFIX)
            },
        )
        assertEquals(200, putBucketResult.statusCode)

        // Bucket space client over path-style. Path-style may be forbidden on the bucket space
        // endpoint independently of the agentic bucket endpoint (different domain), so guard the
        // first bucket-space call separately; a pass here implies the rest is fine too.
        val objectKey = randomObjectKey()
        val putObjectResult: PutObjectResult = try {
            pathStyleBucketSpaceClient.putObject(
                PutObjectRequest {
                    bucket = bucketName
                    key = objectKey
                    body = ByteStream.fromString("hello path-style")
                },
            )
        } catch (error: Throwable) {
            if (isSecondLevelDomainForbidden(error)) {
                skip(
                    "path-style addressing is not allowed for the bucket space endpoint " +
                        "(SecondLevelDomainForbidden)",
                )
                return@runTest
            }
            throw error
        }
        assertEquals(200, putObjectResult.statusCode)

        pathStyleBucketSpaceClient.getObject(
            GetObjectRequest {
                bucket = bucketName
                key = objectKey
            },
        ).use { result ->
            assertEquals(200, result.statusCode)
            assertEquals("hello path-style", result.body?.toByteArray()?.decodeToString())
        }

        pathStyleBucketSpaceClient.deleteObject(
            DeleteObjectRequest {
                bucket = bucketName
                key = objectKey
            },
        )

        val getAclResult = pathStyleBucketSpaceClient.getBucketAcl(GetBucketAclRequest { bucket = bucketName })
        assertEquals(200, getAclResult.statusCode)
    }
}
