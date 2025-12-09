package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketRequest {}
        assertNull(request.bucket)
        assertNull(request.acl)
        assertNull(request.resourceGroupId)
        assertNull(request.bucketTagging)
        assertNull(request.createBucketConfiguration)

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
        val request = PutBucketRequest {
            bucket = "my-bucket"
            acl = "private"
            resourceGroupId = "resourceGroupId1"
            bucketTagging = "A=B"
            createBucketConfiguration = CreateBucketConfiguration {
                storageClass = "IA"
                dataRedundancyType = "ZRS"
            }
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("private", request.acl)
        assertEquals("resourceGroupId1", request.resourceGroupId)
        assertEquals("A=B", request.bucketTagging)
        assertEquals("IA", request.createBucketConfiguration?.storageClass)
        assertEquals("ZRS", request.createBucketConfiguration?.dataRedundancyType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-acl")
            request.headers.containsKey("x-oss-resource-group-id")
            request.headers.containsKey("x-oss-bucket-tagging")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = PutBucketRequest.Builder()
        builder.bucket = "my-bucket"
        builder.acl = "private"
        builder.resourceGroupId = "resourceGroupId1"
        builder.bucketTagging = "A=B"
        builder.createBucketConfiguration = CreateBucketConfiguration {
            storageClass = "IA"
            dataRedundancyType = "ZRS"
        }

        val request = PutBucketRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("private", request.acl)
        assertEquals("resourceGroupId1", request.resourceGroupId)
        assertEquals("A=B", request.bucketTagging)
        assertEquals("IA", request.createBucketConfiguration?.storageClass)
        assertEquals("ZRS", request.createBucketConfiguration?.dataRedundancyType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-acl")
            request.headers.containsKey("x-oss-resource-group-id")
            request.headers.containsKey("x-oss-bucket-tagging")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutBucketResult {}
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
        val result = PutBucketResult {
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
        val builder = PutBucketResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
