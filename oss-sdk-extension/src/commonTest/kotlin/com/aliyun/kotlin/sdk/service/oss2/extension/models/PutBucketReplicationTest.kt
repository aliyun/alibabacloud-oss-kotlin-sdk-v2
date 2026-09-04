package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketReplicationTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketReplicationRequest {}
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
        val configuration = ReplicationConfiguration {
            rules = listOf(
                ReplicationRule {
                    rtc = RTC {
                        status = "Enabled"
                    }
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
                    syncRole = "aliyunramrole"
                    sourceSelectionCriteria = ReplicationSourceSelectionCriteria {
                        sseKmsEncryptedObjects = SseKmsEncryptedObjects {
                            status = "Enabled"
                        }
                    }
                    encryptionConfiguration = ReplicationEncryptionConfiguration {
                        replicaKmsKeyID = "kmsID"
                    }
                    id = "id"
                    action = "ALL"
                    status = "Enabled"
                }
            )
        }
        val request = PutBucketReplicationRequest {
            bucket = "bucket"
            this.replicationConfiguration = configuration
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.replicationConfiguration)

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
        val configuration = ReplicationConfiguration {
            rules = listOf(
                ReplicationRule {
                    rtc = RTC {
                        status = "Enabled"
                    }
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
                    syncRole = "aliyunramrole"
                    sourceSelectionCriteria = ReplicationSourceSelectionCriteria {
                        sseKmsEncryptedObjects = SseKmsEncryptedObjects {
                            status = "Enabled"
                        }
                    }
                    encryptionConfiguration = ReplicationEncryptionConfiguration {
                        replicaKmsKeyID = "kmsID"
                    }
                    id = "id"
                    action = "ALL"
                    status = "Enabled"
                }
            )
        }
        val builder = PutBucketReplicationRequest.Builder()
        builder.bucket = "bucket"
        builder.replicationConfiguration = configuration

        val request = PutBucketReplicationRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.replicationConfiguration)

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
        val result = PutBucketReplicationResult {}
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
        val result = PutBucketReplicationResult {
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
        val builder = PutBucketReplicationResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketReplicationResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
