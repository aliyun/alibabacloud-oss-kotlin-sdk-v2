package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketHttpsConfigTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketHttpsConfigRequest {}
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
        val request = GetBucketHttpsConfigRequest {
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
        val builder = GetBucketHttpsConfigRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketHttpsConfigRequest(builder)
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
        val result = GetBucketHttpsConfigResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.httpsConfiguration)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val configuration = HttpsConfiguration {
            tls = TLS {
                enable = true
                tlsVersions = listOf(
                    "TLSv1.2",
                    "TLSv1.3"
                )
            }
        }
        val result = GetBucketHttpsConfigResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.httpsConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = HttpsConfiguration {
            tls = TLS {
                enable = true
                tlsVersions = listOf(
                    "TLSv1.2",
                    "TLSv1.3"
                )
            }
        }
        val builder = GetBucketHttpsConfigResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketHttpsConfigResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.httpsConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
