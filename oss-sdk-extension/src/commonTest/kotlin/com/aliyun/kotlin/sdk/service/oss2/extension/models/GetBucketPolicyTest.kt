package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketPolicyTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketPolicyRequest {}
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
        val request = GetBucketPolicyRequest {
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
        val builder = GetBucketPolicyRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketPolicyRequest(builder)
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
        val result = GetBucketPolicyResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() = runTest {
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
        val result = GetBucketPolicyResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ByteStream.fromString(policy)
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(policy, result.body?.toByteArray()?.decodeToString())

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() = runTest {
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
        val builder = GetBucketPolicyResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ByteStream.fromString(policy)

        val result = GetBucketPolicyResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(policy, result.body?.toByteArray()?.decodeToString())

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
