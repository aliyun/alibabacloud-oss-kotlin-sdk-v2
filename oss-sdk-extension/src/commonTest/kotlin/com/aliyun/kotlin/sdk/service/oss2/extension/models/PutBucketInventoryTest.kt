package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PutBucketInventoryTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = PutBucketInventoryRequest {}
        assertNull(request.bucket)
        assertNull(request.inventoryId)
        assertNull(request.inventoryConfiguration)

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
        val configuration = InventoryConfiguration {
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
        val request = PutBucketInventoryRequest {
            bucket = "bucket"
            this.inventoryConfiguration = configuration
            inventoryId = "inventoryId"
        }

        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.inventoryConfiguration)
        assertEquals("inventoryId", request.inventoryId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("inventoryId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val configuration = InventoryConfiguration {
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
        val builder = PutBucketInventoryRequest.Builder()
        builder.bucket = "bucket"
        builder.inventoryConfiguration = configuration
        builder.inventoryId = "inventoryId"

        val request = PutBucketInventoryRequest(builder)
        assertEquals("bucket", request.bucket)
        assertEquals(configuration, request.inventoryConfiguration)
        assertEquals("inventoryId", request.inventoryId)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.contains("inventoryId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = PutBucketInventoryResult {}
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
        val result = PutBucketInventoryResult {
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
        val builder = PutBucketInventoryResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")

        val result = PutBucketInventoryResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
