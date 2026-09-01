package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

/**
 * The two-phase deletion of an agentic bucket, on its own bucket so that disabling it cannot
 * disturb the other scenarios.
 *
 * `PutAgenticBucketStatus(Disabled)` succeeds, but the bucket only becomes deletable roughly 24
 * hours later, so the `DeleteAgenticBucket` that follows immediately is answered with
 * 409 / `AgenticBucketNotReady`. A create-then-delete round trip is therefore impossible in a
 * single run and the 409 is what gets asserted; the reaper reclaims the bucket in a later run.
 */
class AgenticBucketLifecycleTest : AgenticTestBase() {

    private val bucketName: String = randomAgenticBucketName()
    private var bucketCreated: Boolean = false

    @AfterTest
    fun reapBuckets() = runTest(timeout = 5.minutes) {
        if (!bucketCreated) return@runTest
        // The test already disabled the bucket; just run the reaper.
        disableAndReap(bucketName)
    }

    @Test
    fun testDisableThenDelete() = runTest(timeout = 5.minutes) {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        // Flag first: if the create fails after the server made the bucket, teardown must still
        // disable it, otherwise it stays Enabled and the reaper can never reclaim it.
        bucketCreated = true
        val createResult = agenticClient.createAgenticBucket(CreateAgenticBucketRequest { bucket = bucketName })
        assertEquals(200, createResult.statusCode)

        val putStatusResult = agenticClient.putAgenticBucketStatus(
            PutAgenticBucketStatusRequest {
                bucket = bucketName
                agenticBucketStatus = AgenticBucketStatus { status = "Disabled" }
            },
        )
        assertEquals(200, putStatusResult.statusCode)

        val exception = assertFails {
            agenticClient.deleteAgenticBucket(DeleteAgenticBucketRequest { bucket = bucketName })
        }
        val serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertTrue(
            serviceError.statusCode == 409 || serviceError.errorCode == "AgenticBucketNotReady",
            "expected AgenticBucketNotReady/409, got code=${serviceError.errorCode} " +
                "status=${serviceError.statusCode}",
        )
    }
}
