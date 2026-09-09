package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketOverwriteConfigTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketOverwriteConfigRequest {}
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
        val request = PutBucketOverwriteConfigRequest {
            bucket = "bucket"
            this.overwriteConfiguration = overwriteConfiguration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(overwriteConfiguration, request.overwriteConfiguration)

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
        val builder = PutBucketOverwriteConfigRequest.Builder()
        builder.bucket = "bucket"
        builder.overwriteConfiguration = overwriteConfiguration

        val request = PutBucketOverwriteConfigRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(overwriteConfiguration, request.overwriteConfiguration)

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
        val result = PutBucketOverwriteConfigResult {}
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
        val result = PutBucketOverwriteConfigResult {
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
        val builder = PutBucketOverwriteConfigResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketOverwriteConfigResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
