package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListBucketInventoryTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListBucketInventoryRequest {}
        assertNull(request.bucket)
        assertNull(request.continuationToken)

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
        val request = ListBucketInventoryRequest {
            bucket = "bucket"
            continuationToken = "continuationToken"
        }

        assertEquals("bucket", request.bucket)
        assertEquals("continuationToken", request.continuationToken)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("continuation-token")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListBucketInventoryRequest.Builder()
        builder.bucket = "bucket"
        builder.continuationToken = "continuationToken"

        val request = ListBucketInventoryRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals("continuationToken", request.continuationToken)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("continuation-token")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListBucketInventoryResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.inventoryConfigurations)
        assertNull(result.isTruncated)
        assertNull(result.nextContinuationToken)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val listInventoryConfigurationsResult = ListInventoryConfigurationsResult {
            inventoryConfigurations = listOf(
                InventoryConfiguration {
                    includedObjectVersions = "All"
                    optionalFields = OptionalFields {
                        fields = listOf(
                            "Size",
                            "LastModifiedDate",
                            "TransitionTime",
                            "ETag",
                            "StorageClass",
                            "IsMultipartUploaded",
                            "EncryptionStatus"
                        )
                    }
                    id = "report1"
                    isEnabled = true
                    destination = InventoryDestination {
                        oSSBucketDestination = InventoryOSSBucketDestination {
                            bucket = "acs:oss:::destination-bucket"
                            prefix = "prefix1"
                            encryption = InventoryEncryption {
                                sseOss = "sseOss"
                                sseKms = SSEKMS {
                                    keyId = "keyId"
                                }
                            }
                            format = "CSV"
                            accountId = "1000000000000000"
                            roleArn = "acs:ram::1000000000000000:role/AliyunOSSRole"
                        }
                    }
                    schedule = InventorySchedule {
                        frequency = "Daily"
                    }
                    filter = InventoryFilter {
                        lastModifyEndTimeStamp = 1638347592
                        lastModifyBeginTimeStamp = 1637883649
                        lowerSizeBound = 1024
                        storageClass = "Standard,IA"
                        prefix = "filterPrefix/"
                        upperSizeBound = 1048576
                    }
                }
            )
            isTruncated = true
            nextContinuationToken = "nextContinuationToken"
        }
        val result = ListBucketInventoryResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = listInventoryConfigurationsResult
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(listInventoryConfigurationsResult.inventoryConfigurations, result.inventoryConfigurations)
        assertEquals(listInventoryConfigurationsResult.isTruncated, result.isTruncated)
        assertEquals(listInventoryConfigurationsResult.nextContinuationToken, result.nextContinuationToken)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val listInventoryConfigurationsResult = ListInventoryConfigurationsResult {
            inventoryConfigurations = listOf(
                InventoryConfiguration {
                    includedObjectVersions = "All"
                    optionalFields = OptionalFields {
                        fields = listOf(
                            "Size",
                            "LastModifiedDate",
                            "TransitionTime",
                            "ETag",
                            "StorageClass",
                            "IsMultipartUploaded",
                            "EncryptionStatus"
                        )
                    }
                    id = "report1"
                    isEnabled = true
                    destination = InventoryDestination {
                        oSSBucketDestination = InventoryOSSBucketDestination {
                            bucket = "acs:oss:::destination-bucket"
                            prefix = "prefix1"
                            encryption = InventoryEncryption {
                                sseOss = "sseOss"
                                sseKms = SSEKMS {
                                    keyId = "keyId"
                                }
                            }
                            format = "CSV"
                            accountId = "1000000000000000"
                            roleArn = "acs:ram::1000000000000000:role/AliyunOSSRole"
                        }
                    }
                    schedule = InventorySchedule {
                        frequency = "Daily"
                    }
                    filter = InventoryFilter {
                        lastModifyEndTimeStamp = 1638347592
                        lastModifyBeginTimeStamp = 1637883649
                        lowerSizeBound = 1024
                        storageClass = "Standard,IA"
                        prefix = "filterPrefix/"
                        upperSizeBound = 1048576
                    }
                }
            )
            isTruncated = true
            nextContinuationToken = "nextContinuationToken"
        }
        val builder = ListBucketInventoryResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = listInventoryConfigurationsResult

        val result = ListBucketInventoryResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(listInventoryConfigurationsResult.inventoryConfigurations, result.inventoryConfigurations)
        assertEquals(listInventoryConfigurationsResult.isTruncated, result.isTruncated)
        assertEquals(listInventoryConfigurationsResult.nextContinuationToken, result.nextContinuationToken)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
