package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketCorsTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketCorsRequest {}
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
        val request = GetBucketCorsRequest {
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
        val builder = GetBucketCorsRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketCorsRequest(builder)
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
        val result = GetBucketCorsResult {}
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
        val corsConfiguration = CORSConfiguration {
            corsRules = listOf(
                CORSRule {
                    allowedOrigins = listOf("http://example.com", "http://example.net")
                    allowedMethods = listOf("GET")
                    allowedHeaders = listOf("authorization", "x-oss-test", "x-oss-test1")
                    exposeHeaders = listOf("x-oss-test2")
                    maxAgeSeconds = 100
                }
            )
            responseVary = true
        }
        val result = GetBucketCorsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = corsConfiguration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.corsConfiguration?.corsRules?.size)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedOrigins, result.corsConfiguration?.corsRules?.first()?.allowedOrigins)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedMethods, result.corsConfiguration?.corsRules?.first()?.allowedMethods)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedHeaders, result.corsConfiguration?.corsRules?.first()?.allowedHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.exposeHeaders, result.corsConfiguration?.corsRules?.first()?.exposeHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.maxAgeSeconds, result.corsConfiguration?.corsRules?.first()?.maxAgeSeconds)
        assertEquals(corsConfiguration.responseVary, result.corsConfiguration?.responseVary)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val corsConfiguration = CORSConfiguration {
            corsRules = listOf(
                CORSRule {
                    allowedOrigins = listOf("http://example.com", "http://example.net")
                    allowedMethods = listOf("GET")
                    allowedHeaders = listOf("authorization", "x-oss-test", "x-oss-test1")
                    exposeHeaders = listOf("x-oss-test2")
                    maxAgeSeconds = 100
                }
            )
            responseVary = true
        }
        val builder = GetBucketCorsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = corsConfiguration

        val result = GetBucketCorsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.corsConfiguration?.corsRules?.size)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedOrigins, result.corsConfiguration?.corsRules?.first()?.allowedOrigins)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedMethods, result.corsConfiguration?.corsRules?.first()?.allowedMethods)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedHeaders, result.corsConfiguration?.corsRules?.first()?.allowedHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.exposeHeaders, result.corsConfiguration?.corsRules?.first()?.exposeHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.maxAgeSeconds, result.corsConfiguration?.corsRules?.first()?.maxAgeSeconds)
        assertEquals(corsConfiguration.responseVary, result.corsConfiguration?.responseVary)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
