package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketReplicationTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketReplicationRequest {}
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
        val request = GetBucketReplicationRequest {
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
        val builder = GetBucketReplicationRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketReplicationRequest(builder)
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
        val result = GetBucketReplicationResult {}
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
        val result = GetBucketReplicationResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.replicationConfiguration?.rules?.size)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.rtc?.status)
        assertEquals(2, result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.size)
        assertEquals("prefix_1", result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.get(0))
        assertEquals("prefix_2", result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.get(1))
        assertEquals("dest-bucket", result.replicationConfiguration?.rules?.get(0)?.destination?.bucket)
        assertEquals("oss-cn-hangzhou", result.replicationConfiguration?.rules?.get(0)?.destination?.location)
        assertEquals("oss_acc", result.replicationConfiguration?.rules?.get(0)?.destination?.transferType)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.historicalObjectReplication)
        assertEquals("aliyunramrole", result.replicationConfiguration?.rules?.get(0)?.syncRole)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.sourceSelectionCriteria?.sseKmsEncryptedObjects?.status)
        assertEquals("kmsID", result.replicationConfiguration?.rules?.get(0)?.encryptionConfiguration?.replicaKmsKeyID)
        assertEquals("id", result.replicationConfiguration?.rules?.get(0)?.id)
        assertEquals("ALL", result.replicationConfiguration?.rules?.get(0)?.action)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.status)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
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
        val builder = GetBucketReplicationResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketReplicationResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.replicationConfiguration?.rules?.size)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.rtc?.status)
        assertEquals(2, result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.size)
        assertEquals("prefix_1", result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.get(0))
        assertEquals("prefix_2", result.replicationConfiguration?.rules?.get(0)?.prefixSet?.prefixes?.get(1))
        assertEquals("dest-bucket", result.replicationConfiguration?.rules?.get(0)?.destination?.bucket)
        assertEquals("oss-cn-hangzhou", result.replicationConfiguration?.rules?.get(0)?.destination?.location)
        assertEquals("oss_acc", result.replicationConfiguration?.rules?.get(0)?.destination?.transferType)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.historicalObjectReplication)
        assertEquals("aliyunramrole", result.replicationConfiguration?.rules?.get(0)?.syncRole)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.sourceSelectionCriteria?.sseKmsEncryptedObjects?.status)
        assertEquals("kmsID", result.replicationConfiguration?.rules?.get(0)?.encryptionConfiguration?.replicaKmsKeyID)
        assertEquals("id", result.replicationConfiguration?.rules?.get(0)?.id)
        assertEquals("ALL", result.replicationConfiguration?.rules?.get(0)?.action)
        assertEquals("Enabled", result.replicationConfiguration?.rules?.get(0)?.status)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
