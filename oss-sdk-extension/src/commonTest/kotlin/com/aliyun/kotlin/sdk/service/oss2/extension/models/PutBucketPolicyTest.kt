package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketPolicyTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketPolicyRequest {}
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
        val policy = """
            {
                "Version":"1",
                "Statement":[
                {
                    "Action":[
                        "oss:PutObject",
                        "oss:GetObject"
                    ],
                    "Effect":"Deny",
                    "Principal":["1234567890"],
                    "Resource":["acs:oss:*:1234567890:*/*"]
               }
               ]
            }
        """.trimIndent().replace("\n", "")
        val request = PutBucketPolicyRequest {
            bucket = "bucket"
            this.body = ByteStream.fromString(policy)
        }

        assertEquals("bucket", request.bucket)
        assertNotNull(request.body)

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
        val policy = """
            {
                "Version":"1",
                "Statement":[
                {
                    "Action":[
                        "oss:PutObject",
                        "oss:GetObject"
                    ],
                    "Effect":"Deny",
                    "Principal":["1234567890"],
                    "Resource":["acs:oss:*:1234567890:*/*"]
               }
               ]
            }
        """.trimIndent().replace("\n", "")
        val builder = PutBucketPolicyRequest.Builder()
        builder.bucket = "bucket"
        builder.body = ByteStream.fromString(policy)

        val request = PutBucketPolicyRequest(builder)
        assertEquals("bucket", request.bucket)
        assertNotNull(request.body)

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
        val result = PutBucketPolicyResult {}
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
        val result = PutBucketPolicyResult {
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
        val builder = PutBucketPolicyResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketPolicyResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
