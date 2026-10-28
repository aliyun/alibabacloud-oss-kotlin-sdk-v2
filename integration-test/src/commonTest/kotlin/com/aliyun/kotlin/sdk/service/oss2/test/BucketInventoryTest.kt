package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketInventory
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketInventory
import com.aliyun.kotlin.sdk.service.oss2.extension.api.listBucketInventory
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketInventory
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketInventoryRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketInventoryRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InventoryConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InventoryDestination
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InventoryFilter
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InventoryOSSBucketDestination
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InventorySchedule
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ListBucketInventoryRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OptionalFields
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketInventoryRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BucketInventoryTest: TestBase() {

    @Test
    fun testPutAndGetBucketInventory() = bucketTest { bucketName ->
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

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
            id = "inventoryId"
            isEnabled = true
            destination = InventoryDestination {
                oSSBucketDestination = InventoryOSSBucketDestination {
                    this.bucket = "acs:oss:::$bucket"
                    prefix = "prefix1"
                    format = "CSV"
                    accountId = OSS_TEST_RAM_UID
                    roleArn = OSS_TEST_RAM_ROLE_ARN
                }
            }
            schedule = InventorySchedule {
                frequency = "Daily"
            }
            filter = InventoryFilter {
                lastModifyEndTimeStamp = 1638347592
                lastModifyBeginTimeStamp = 1637883649
                lowerSizeBound = 1024
                storageClass = "IA"
                prefix = "filterPrefix/"
                upperSizeBound = 1048576
            }
        }
        val putResult = defaultClient.putBucketInventory(PutBucketInventoryRequest {
            this.bucket = bucketName
            inventoryConfiguration = configuration
            inventoryId = "inventoryId"
        })
        assertEquals(200, putResult.statusCode)

        waitForCacheExpiration(1)

        val getResult = defaultClient.getBucketInventory(GetBucketInventoryRequest {
            this.bucket = bucketName
            inventoryId = "inventoryId"
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(configuration, getResult.inventoryConfiguration)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutBucketInventoryWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketInventory(PutBucketInventoryRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketInventory(PutBucketInventoryRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.inventoryId is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketInventory(PutBucketInventoryRequest {
                bucket = bucketName
                inventoryId = "inventoryId"
            })
        }
        assertEquals(exception.message, "request.inventoryConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketInventory(PutBucketInventoryRequest {
                bucket = bucketName
                inventoryId = "inventoryId"
                inventoryConfiguration = InventoryConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketInventoryWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketInventory(GetBucketInventoryRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketInventory(GetBucketInventoryRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.inventoryId is required")

        exception = assertFails {
            invalidClient.getBucketInventory(GetBucketInventoryRequest {
                bucket = bucketName
                inventoryId = "inventoryId"
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testListBucketInventory() = bucketTest { bucketName ->
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

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
            id = "inventoryId"
            isEnabled = true
            destination = InventoryDestination {
                oSSBucketDestination = InventoryOSSBucketDestination {
                    this.bucket = "acs:oss:::$bucket"
                    prefix = "prefix1"
                    format = "CSV"
                    accountId = OSS_TEST_RAM_UID
                    roleArn = OSS_TEST_RAM_ROLE_ARN
                }
            }
            schedule = InventorySchedule {
                frequency = "Daily"
            }
            filter = InventoryFilter {
                lastModifyEndTimeStamp = 1638347592
                lastModifyBeginTimeStamp = 1637883649
                lowerSizeBound = 1024
                storageClass = "IA"
                prefix = "filterPrefix/"
                upperSizeBound = 1048576
            }
        }
        val putResult = defaultClient.putBucketInventory(PutBucketInventoryRequest {
            this.bucket = bucketName
            inventoryConfiguration = configuration
            inventoryId = "inventoryId"
        })
        assertEquals(200, putResult.statusCode)

        waitForCacheExpiration(1)

        val listResult = defaultClient.listBucketInventory(ListBucketInventoryRequest {
            this.bucket = bucketName
        })
        assertEquals(200, listResult.statusCode)
        assertEquals(false, listResult.isTruncated)
        assertNull(listResult.nextContinuationToken)
        assertEquals(1, listResult.inventoryConfigurations?.size)
        assertEquals(configuration, listResult.inventoryConfigurations?.get(0))

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListBucketInventoryWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.listBucketInventory(ListBucketInventoryRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.listBucketInventory(ListBucketInventoryRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketInventory() = bucketTest { bucketName ->
        val bucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

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
            id = "inventoryId"
            isEnabled = true
            destination = InventoryDestination {
                oSSBucketDestination = InventoryOSSBucketDestination {
                    this.bucket = "acs:oss:::$bucket"
                    prefix = "prefix1"
                    format = "CSV"
                    accountId = OSS_TEST_RAM_UID
                    roleArn = OSS_TEST_RAM_ROLE_ARN
                }
            }
            schedule = InventorySchedule {
                frequency = "Daily"
            }
            filter = InventoryFilter {
                lastModifyEndTimeStamp = 1638347592
                lastModifyBeginTimeStamp = 1637883649
                lowerSizeBound = 1024
                storageClass = "IA"
                prefix = "filterPrefix/"
                upperSizeBound = 1048576
            }
        }
        val putResult = defaultClient.putBucketInventory(PutBucketInventoryRequest {
            this.bucket = bucketName
            inventoryConfiguration = configuration
            inventoryId = "inventoryId"
        })
        assertEquals(200, putResult.statusCode)

        val deleteResult = defaultClient.deleteBucketInventory(DeleteBucketInventoryRequest {
            this.bucket = bucketName
            inventoryId = "inventoryId"
        })
        assertEquals(204, deleteResult.statusCode)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testDeleteBucketInventoryWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketInventory(DeleteBucketInventoryRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketInventory(DeleteBucketInventoryRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.inventoryId is required")

        exception = assertFails {
            invalidClient.deleteBucketInventory(DeleteBucketInventoryRequest {
                bucket = bucketName
                inventoryId = "inventoryId"
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
