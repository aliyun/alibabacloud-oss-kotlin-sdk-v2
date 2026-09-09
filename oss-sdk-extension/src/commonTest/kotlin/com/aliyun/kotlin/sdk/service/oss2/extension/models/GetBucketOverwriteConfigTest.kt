package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketOverwriteConfigTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketOverwriteConfigRequest {}
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
        val request = GetBucketOverwriteConfigRequest {
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
        val builder = GetBucketOverwriteConfigRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketOverwriteConfigRequest(builder)
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
        val result = GetBucketOverwriteConfigResult {}
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
        val overwriteConfiguration = OverwriteConfiguration {
            rules = listOf(
                OverwriteRule {
                    id = "rule-001"
                    prefix = "a/"
                    suffix = ".txt"
                    action = "forbid"
                    principals = Principals {
                        principal = listOf("27737962156157xxxx")
                    }
                },
                OverwriteRule {
                    id = "rule-002"
                    prefix = "images"
                    action = "forbid"
                },
                OverwriteRule {
                    id = "rule-003"
                    suffix = ".jpg"
                    action = "forbid"
                }
            )
        }
        val result = GetBucketOverwriteConfigResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = overwriteConfiguration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(overwriteConfiguration, result.overwriteConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val overwriteConfiguration = OverwriteConfiguration {
            rules = listOf(
                OverwriteRule {
                    id = "rule-001"
                    prefix = "a/"
                    suffix = ".txt"
                    action = "forbid"
                    principals = Principals {
                        principal = listOf("27737962156157xxxx")
                    }
                },
                OverwriteRule {
                    id = "rule-002"
                    prefix = "images"
                    action = "forbid"
                },
                OverwriteRule {
                    id = "rule-003"
                    suffix = ".jpg"
                    action = "forbid"
                }
            )
        }
        val builder = GetBucketOverwriteConfigResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = overwriteConfiguration

        val result = GetBucketOverwriteConfigResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(overwriteConfiguration, result.overwriteConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
