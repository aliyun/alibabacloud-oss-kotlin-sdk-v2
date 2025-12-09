package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RestoreObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = RestoreObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.versionId)
        assertNull(request.restoreRequest)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestWithFullValuesFromDsl() {
        val request = RestoreObjectRequest {
            bucket = "my-bucket"
            key = "key1"
            restoreRequest = RestoreRequest {
                days = 1
                jobParameters = JobParameters {
                    tier = "Expedited"
                }
            }
            versionId = "versionId1"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertNotNull(request.restoreRequest)
        assertEquals("versionId1", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = RestoreObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key1"
        builder.restoreRequest = RestoreRequest {
            days = 1
            jobParameters = JobParameters {
                tier = "Expedited"
            }
        }
        builder.versionId = "versionId1"

        val request = RestoreObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key1", request.key)
        assertNotNull(request.restoreRequest)
        assertEquals("versionId1", request.versionId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = RestoreObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.versionId)
        assertNull(result.objectRestorePriority)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = RestoreObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "versionId",
                "x-oss-object-restore-priority" to "Standard"
            )
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)
        assertEquals("Standard", result.objectRestorePriority)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
        assertEquals("Standard", result.headers["x-oss-object-restore-priority"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = RestoreObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "versionId",
            "x-oss-object-restore-priority" to "Standard"
        )

        val result = RestoreObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("versionId", result.versionId)
        assertEquals("Standard", result.objectRestorePriority)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals("versionId", result.headers["x-oss-version-id"])
        assertEquals("Standard", result.headers["x-oss-object-restore-priority"])
    }
}
