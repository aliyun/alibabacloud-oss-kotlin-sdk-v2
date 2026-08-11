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
 * The status codes are not uniform: Create and ListAgenticBuckets are rejected with 403
 * InvalidAccessKeyId, while Get answers 404 NoSuchAgenticBucket because the service resolves
 * bucket existence before it validates the access key. The ec fields are only checked for
 * presence, they are server-internal diagnostics and not part of the contract.
 */
class AgenticBucketServerErrorsTest : AgenticTestBase() {

    @Test
    fun testInvalidCredentials() = runTest {
        if (!agenticConfigured) {
            skip("agentic tests require OSS_TEST_RAM_UID and OSS_TEST_REGION")
            return@runTest
        }

        val bucketName = randomAgenticBucketName()

        // Create: the access key itself is rejected.
        var exception = assertFails {
            invalidAkAgenticClient.createAgenticBucket(CreateAgenticBucketRequest { bucket = bucketName })
        }
        var serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertEquals(403, serviceError.statusCode)
        assertEquals("InvalidAccessKeyId", serviceError.errorCode)
        assertTrue(serviceError.ec.isNotEmpty(), "the service error should carry an ec")
        assertTrue(serviceError.requestId.isNotEmpty(), "the service error should carry a request id")

        // Get: answered with 404 rather than 403, the bucket is resolved before the access key.
        exception = assertFails {
            invalidAkAgenticClient.getAgenticBucket(GetAgenticBucketRequest { bucket = bucketName })
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertEquals(404, serviceError.statusCode)
        assertEquals("NoSuchAgenticBucket", serviceError.errorCode)
        assertTrue(serviceError.ec.isNotEmpty(), "the service error should carry an ec")
        assertTrue(serviceError.requestId.isNotEmpty(), "the service error should carry a request id")

        // List.
        exception = assertFails {
            invalidAkAgenticClient.listAgenticBuckets(ListAgenticBucketsRequest {})
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertEquals(403, serviceError.statusCode)
        assertEquals("InvalidAccessKeyId", serviceError.errorCode)
        assertTrue(serviceError.ec.isNotEmpty(), "the service error should carry an ec")
        assertTrue(serviceError.requestId.isNotEmpty(), "the service error should carry a request id")

        // ListBucketSpaces: the response identity has not been observed against real hardware,
        // so only assert that the server rejected the call.
        exception = assertFails {
            invalidAkAgenticClient.listBucketSpaces(ListBucketSpacesRequest { bucket = bucketName })
        }
        serviceError = assertNotNull(serviceErrorOf(exception), messageChain(exception))
        assertTrue(serviceError.statusCode != 0, "expected a server status code")
    }
}
