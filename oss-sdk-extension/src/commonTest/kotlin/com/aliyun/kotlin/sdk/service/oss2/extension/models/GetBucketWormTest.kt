package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketWormTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketWormRequest {}
        assertNull(request.bucket)

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
        val request = GetBucketWormRequest {
            bucket = "bucket"
        }

        assertEquals("bucket", request.bucket)

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
    fun buildRequestFromBuilder() {
        val builder = GetBucketWormRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketWormRequest(builder)
        assertEquals("bucket", request.bucket)

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
    fun buildResultWithEmptyValues() {
        val result = GetBucketWormResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val configuration = WormConfiguration {
            expirationDate = "2020-10-15T15:50:33"
            wormId = "1666E2CFB2B3418****"
            state = "Locked"
            retentionPeriodInDays = 10
            creationDate = "2020-10-15T15:50:32"
        }
        val result = GetBucketWormResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration.expirationDate, result.wormConfiguration?.expirationDate)
        assertEquals(configuration.wormId, result.wormConfiguration?.wormId)
        assertEquals(configuration.state, result.wormConfiguration?.state)
        assertEquals(configuration.retentionPeriodInDays, result.wormConfiguration?.retentionPeriodInDays)
        assertEquals(configuration.creationDate, result.wormConfiguration?.creationDate)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = WormConfiguration {
            expirationDate = "2020-10-15T15:50:33"
            wormId = "1666E2CFB2B3418****"
            state = "Locked"
            retentionPeriodInDays = 10
            creationDate = "2020-10-15T15:50:32"
        }
        val builder = GetBucketWormResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketWormResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration.expirationDate, result.wormConfiguration?.expirationDate)
        assertEquals(configuration.wormId, result.wormConfiguration?.wormId)
        assertEquals(configuration.state, result.wormConfiguration?.state)
        assertEquals(configuration.retentionPeriodInDays, result.wormConfiguration?.retentionPeriodInDays)
        assertEquals(configuration.creationDate, result.wormConfiguration?.creationDate)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
