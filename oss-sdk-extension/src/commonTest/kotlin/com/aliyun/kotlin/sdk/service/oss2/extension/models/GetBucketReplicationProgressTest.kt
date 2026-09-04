package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketReplicationProgressTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketReplicationProgressRequest {}
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
        val request = GetBucketReplicationProgressRequest {
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
        val builder = GetBucketReplicationProgressRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketReplicationProgressRequest(builder)
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
        val result = GetBucketReplicationProgressResult {}
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
        val progress = ReplicationProgress {
            rules = listOf(
                ReplicationProgressRule {
                    prefixSet = ReplicationPrefixSet {
                        prefixes = listOf(
                            "prefix_1",
                            "prefix_2"
                        )
                    }
                    destination = ReplicationDestination {
                        bucket = "dest-bucket"
                        location = "oss-cn-hangzhou"
                        transferType = "oss_acc"
                    }
                    historicalObjectReplication = "Enabled"
                    id = "id"
                    action = "ALL"
                    status = "Enabled"
                    progress = Progress {
                        historicalObject = "0.85"
                        newObject = "2015-09-24T15:28:14.000Z"
                    }
                }
            )
        }
        val result = GetBucketReplicationProgressResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = progress
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(progress, result.replicationProgress)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val progress = ReplicationProgress {
            rules = listOf(
                ReplicationProgressRule {
                    prefixSet = ReplicationPrefixSet {
                        prefixes = listOf(
                            "prefix_1",
                            "prefix_2"
                        )
                    }
                    destination = ReplicationDestination {
                        bucket = "dest-bucket"
                        location = "oss-cn-hangzhou"
                        transferType = "oss_acc"
                    }
                    historicalObjectReplication = "Enabled"
                    id = "id"
                    action = "ALL"
                    status = "Enabled"
                    progress = Progress {
                        historicalObject = "0.85"
                        newObject = "2015-09-24T15:28:14.000Z"
                    }
                }
            )
        }
        val builder = GetBucketReplicationProgressResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = progress

        val result = GetBucketReplicationProgressResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(progress, result.replicationProgress)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
