package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketRefererTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketRefererRequest {}
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
        val request = GetBucketRefererRequest {
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
        val builder = GetBucketRefererRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketRefererRequest(builder)
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
        val result = GetBucketRefererResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.refererConfiguration)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val configuration = RefererConfiguration {
            allowEmptyReferer = false
            allowTruncateQueryString = true
            truncatePath = true
            refererList = RefererList {
                referrers = listOf(
                    "http://www.aliyun.com",
                    "https://www.aliyun.com",
                    "http://www.*.com",
                    "https://www.?.aliyuncs.com"
                )
            }
            refererBlacklist = RefererBlacklist {
                referrers = listOf(
                    "http://www.refuse.com",
                    "https://*.hack.com",
                    "http://ban.*.com",
                    "https://www.?.deny.com"
                )
            }
        }
        val result = GetBucketRefererResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.refererConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = RefererConfiguration {
            allowEmptyReferer = false
            allowTruncateQueryString = true
            truncatePath = true
            refererList = RefererList {
                referrers = listOf(
                    "http://www.aliyun.com",
                    "https://www.aliyun.com",
                    "http://www.*.com",
                    "https://www.?.aliyuncs.com"
                )
            }
            refererBlacklist = RefererBlacklist {
                referrers = listOf(
                    "http://www.refuse.com",
                    "https://*.hack.com",
                    "http://ban.*.com",
                    "https://www.?.deny.com"
                )
            }
        }
        val builder = GetBucketRefererResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketRefererResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.refererConfiguration)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
