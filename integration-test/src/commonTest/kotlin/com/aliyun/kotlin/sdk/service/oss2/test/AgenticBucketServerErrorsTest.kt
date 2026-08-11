package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Error propagation with invalid credentials. No bucket is created: every call is expected to fail.
 *
 * The status codes are not uniform, so only Create is pinned: the service answers Create with 403
 * but Get with 404 under an invalid access key, hence the relaxed "some service error with a
 * non-zero status" assertions for the remaining operations.
 */
class AgenticBucketServerErrorsTest : AgenticTestBase() {

    @Test
    fun testInvalidCredentials() = runTest {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        val bucketName = randomAgenticBucketName()

        // Create: 403 with a request id that identifies the rejected call.
        var exception = assertFails {
            invalidAkAgenticClient.createAgenticBucket(CreateAgenticBucketRequest { bucket = bucketName })
        }
        var serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertEquals(403, serviceError.statusCode)
        assertTrue(serviceError.requestId.isNotEmpty(), "the service error should carry a request id")

        // Get: answered with 404 rather than 403, so only assert that it failed on the server.
        exception = assertFails {
            invalidAkAgenticClient.getAgenticBucket(GetAgenticBucketRequest { bucket = bucketName })
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertTrue(serviceError.statusCode != 0, "expected a server status code")

        // List.
        exception = assertFails {
            invalidAkAgenticClient.listAgenticBuckets(ListAgenticBucketsRequest {})
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertTrue(serviceError.statusCode != 0, "expected a server status code")

        // ListBucketSpaces.
        exception = assertFails {
            invalidAkAgenticClient.listBucketSpaces(ListBucketSpacesRequest { bucket = bucketName })
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertTrue(serviceError.statusCode != 0, "expected a server status code")
    }
}
