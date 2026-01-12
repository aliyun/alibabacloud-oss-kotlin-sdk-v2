package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketRefererTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketRefererRequest {}
        assertNull(request.bucket)
        assertNull(request.refererConfiguration)

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
        val request = PutBucketRefererRequest {
            bucket = "bucket"
            this.refererConfiguration = configuration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.refererConfiguration)

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
        val builder = PutBucketRefererRequest.Builder()
        builder.bucket = "bucket"
        builder.refererConfiguration = configuration

        val request = PutBucketRefererRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.refererConfiguration)

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
        val result = PutBucketRefererResult {}
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
        val result = PutBucketRefererResult {
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
        val builder = PutBucketRefererResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketRefererResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
