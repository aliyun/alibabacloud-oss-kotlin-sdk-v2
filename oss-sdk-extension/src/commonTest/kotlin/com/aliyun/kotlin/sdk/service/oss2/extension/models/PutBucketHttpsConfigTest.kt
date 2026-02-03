package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketHttpsConfigTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketHttpsConfigRequest {}
        assertNull(request.bucket)
        assertNull(request.httpsConfiguration)

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
        val configuration = HttpsConfiguration {
            tls = TLS {
                enable = true
                tlsVersions = listOf(
                    "TLSv1.2",
                    "TLSv1.3"
                )
            }
        }
        val request = PutBucketHttpsConfigRequest {
            bucket = "bucket"
            this.httpsConfiguration = configuration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.httpsConfiguration)

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
        val configuration = HttpsConfiguration {
            tls = TLS {
                enable = true
                tlsVersions = listOf(
                    "TLSv1.2",
                    "TLSv1.3"
                )
            }
        }
        val builder = PutBucketHttpsConfigRequest.Builder()
        builder.bucket = "bucket"
        builder.httpsConfiguration = configuration

        val request = PutBucketHttpsConfigRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.httpsConfiguration)

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
        val result = PutBucketHttpsConfigResult {}
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
        val result = PutBucketHttpsConfigResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = null
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = PutBucketHttpsConfigResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketHttpsConfigResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
