package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.paginator.listAgenticBucketsPaginator
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

/**
 * The basic agentic bucket scenario: Create/Get/List/paginator/PutStatus(Enabled)/ListBucketSpaces
 * over one bucket, plus the argument validation paths.
 *
 * The whole positive flow lives in a single test function so that the class only ever creates one
 * agentic bucket: the service cannot delete a bucket within ~24 hours of disabling it, so every
 * extra bucket is backlog for the reaper. Disable + delete is asserted in
 * [AgenticBucketLifecycleTest], on its own bucket.
 */
class AgenticBucketBasicTest : AgenticTestBase() {

    private val bucketName: String = randomAgenticBucketName()
    private var bucketCreated: Boolean = false

    @AfterTest
    fun disableAndReapBucket() = runTest(timeout = 5.minutes) {
        if (!bucketCreated) return@runTest
        disableAndReap(bucketName)
    }

    @Test
    fun testAgenticBucketBasic() = runTest(timeout = 10.minutes) {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        // Flag first: if the create fails after the server made the bucket, teardown must still
        // disable it, otherwise it stays Enabled and the reaper can never reclaim it.
        bucketCreated = true
        val createResult = agenticClient.createAgenticBucket(
            CreateAgenticBucketRequest {
                bucket = bucketName
                createAgenticBucketConfiguration = CreateAgenticBucketConfiguration {
                    storageClass = "Standard"
                    dataRedundancyType = "LRS"
                }
            },
        )
        assertEquals(200, createResult.statusCode)

        // Get
        val getResult = agenticClient.getAgenticBucket(GetAgenticBucketRequest { bucket = bucketName })
        assertEquals(200, getResult.statusCode)
        val info = assertNotNull(getResult.agenticBucketInfo)
        assertTrue(info.name?.contains(bucketName) == true, "unexpected name ${info.name}")

        // List
        val listResult = agenticClient.listAgenticBuckets(ListAgenticBucketsRequest {})
        assertEquals(200, listResult.statusCode)

        // List via the paginator
        var pages = 0
        agenticClient.listAgenticBucketsPaginator(ListAgenticBucketsRequest { maxKeys = 1L }).collect {
            assertEquals(200, it.statusCode)
            pages++
        }
        assertTrue(pages >= 1, "the paginator should emit at least one page")

        // Find the created bucket in the listing. The listing is eventually consistent, so poll
        // with a generous budget; the existence of the bucket is asserted strongly by Get above,
        // so a lagging listing is skipped rather than failed.
        var found = false
        for (attempt in 0 until 12) {
            if (attempt > 0) {
                sleepSeconds(10)
            }
            agenticClient.listAgenticBucketsPaginator(ListAgenticBucketsRequest {}).collect { page ->
                page.agenticBuckets?.forEach { summary ->
                    if (summary.name?.contains(bucketName) == true) {
                        found = true
                    }
                }
            }
            if (found) break
        }
        if (!found) {
            skip("created agentic bucket has not appeared in the listing yet (eventual consistency)")
        }

        // PutStatus(Enabled)
        val putStatusResult = agenticClient.putAgenticBucketStatus(
            PutAgenticBucketStatusRequest {
                bucket = bucketName
                agenticBucketStatus = AgenticBucketStatus { status = "Enabled" }
            },
        )
        assertEquals(200, putStatusResult.statusCode)

        // ListBucketSpaces; the bucket has no space yet, a valid response is enough.
        val spacesResult = agenticClient.listBucketSpaces(ListBucketSpacesRequest { bucket = bucketName })
        assertEquals(200, spacesResult.statusCode)
    }

    /** Required fields are rejected by the client before any request reaches the service. */
    @Test
    fun testRequiredFieldValidation() = runTest {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            agenticClient.createAgenticBucket(CreateAgenticBucketRequest {})
        }
        assertEquals("request.bucket is required", exception.message)

        exception = assertFailsWith<IllegalArgumentException> {
            agenticClient.getAgenticBucket(GetAgenticBucketRequest {})
        }
        assertEquals("request.bucket is required", exception.message)

        exception = assertFailsWith<IllegalArgumentException> {
            agenticClient.deleteAgenticBucket(DeleteAgenticBucketRequest {})
        }
        assertEquals("request.bucket is required", exception.message)

        exception = assertFailsWith<IllegalArgumentException> {
            agenticClient.listBucketSpaces(ListBucketSpacesRequest {})
        }
        assertEquals("request.bucket is required", exception.message)

        exception = assertFailsWith<IllegalArgumentException> {
            agenticClient.putAgenticBucketStatus(PutAgenticBucketStatusRequest {})
        }
        assertEquals("request.bucket is required", exception.message)

        // The status itself is required too.
        exception = assertFailsWith<IllegalArgumentException> {
            agenticClient.putAgenticBucketStatus(PutAgenticBucketStatusRequest { bucket = bucketName })
        }
        assertEquals("request.agenticBucketStatus.status is required", exception.message)
    }

    /**
     * An account id that is not pure digits is a deferred configuration error: the client is
     * constructed, and the first invoked operation fails without touching the network.
     */
    @Test
    fun testInvalidAccountId() = runTest {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        AgenticBucketClient(agenticTestConfig().apply { accountId = "bad-account" }).use { client ->
            val exception = assertFails {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = bucketName })
            }
            val messages = messageChain(exception)
            assertTrue(messages.contains("invalid account id"), messages)
        }
    }
}
