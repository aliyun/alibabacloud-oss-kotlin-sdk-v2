package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketInfoTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketInfoRequest {}
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
        val request = GetBucketInfoRequest {
            bucket = "my-bucket"
        }

        assertEquals("my-bucket", request.bucket)

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
        val builder = GetBucketInfoRequest.Builder()
        builder.bucket = "my-bucket"

        val request = GetBucketInfoRequest(builder)
        assertEquals("my-bucket", request.bucket)

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
        val result = GetBucketInfoResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.bucketInfo)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = GetBucketInfoResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = BucketInfo {
                bucket = Bucket {
                    blockPublicAccess = true
                    crossRegionReplication = "Disabled"
                    intranetEndpoint = "oss-cn-hangzhou-internal.aliyuncs.com"
                    location = "oss-cn-hangzhou"
                    versioning = "Enabled"
                    serverSideEncryptionRule = ServerSideEncryptionRule {
                        sSEAlgorithm = "KMS"
                        kMSMasterKeyID = "kMSMasterKeyID"
                        kMSDataEncryption = "SM4"
                    }
                    accessMonitor = "Enabled"
                    creationDate = "2013-07-31T10:56:21.000Z"
                    resourceGroupId = "rg-aek27tc********"
                    transferAcceleration = "Disabled"
                    accessControlList = AccessControlList {
                        grant = "private"
                    }
                    storageClass = "IA"
                    owner = Owner {
                        id = "27183473914****"
                        displayName = "username"
                    }
                    bucketPolicy = BucketPolicy {
                        logBucket = "examplebucket"
                        logPrefix = "log/"
                    }
                    dataRedundancyType = "LRS"
                    comment = "test"
                    extranetEndpoint = "oss-cn-hangzhou.aliyuncs.com"
                    name = "oss-example"
                }
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(true, result.bucketInfo?.bucket?.blockPublicAccess)
        assertEquals("Disabled", result.bucketInfo?.bucket?.crossRegionReplication)
        assertEquals("oss-cn-hangzhou-internal.aliyuncs.com", result.bucketInfo?.bucket?.intranetEndpoint)
        assertEquals("oss-cn-hangzhou", result.bucketInfo?.bucket?.location)
        assertEquals("Enabled", result.bucketInfo?.bucket?.versioning)
        assertEquals("kMSMasterKeyID", result.bucketInfo?.bucket?.serverSideEncryptionRule?.kMSMasterKeyID)
        assertEquals("KMS", result.bucketInfo?.bucket?.serverSideEncryptionRule?.sSEAlgorithm)
        assertEquals("SM4", result.bucketInfo?.bucket?.serverSideEncryptionRule?.kMSDataEncryption)
        assertEquals("Enabled", result.bucketInfo?.bucket?.accessMonitor)
        assertEquals("2013-07-31T10:56:21.000Z", result.bucketInfo?.bucket?.creationDate)
        assertEquals("rg-aek27tc********", result.bucketInfo?.bucket?.resourceGroupId)
        assertEquals("Disabled", result.bucketInfo?.bucket?.transferAcceleration)
        assertEquals("private", result.bucketInfo?.bucket?.accessControlList?.grant)
        assertEquals("IA", result.bucketInfo?.bucket?.storageClass)
        assertEquals("27183473914****", result.bucketInfo?.bucket?.owner?.id)
        assertEquals("username", result.bucketInfo?.bucket?.owner?.displayName)
        assertEquals("examplebucket", result.bucketInfo?.bucket?.bucketPolicy?.logBucket)
        assertEquals("log/", result.bucketInfo?.bucket?.bucketPolicy?.logPrefix)
        assertEquals("LRS", result.bucketInfo?.bucket?.dataRedundancyType)
        assertEquals("test", result.bucketInfo?.bucket?.comment)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result.bucketInfo?.bucket?.extranetEndpoint)
        assertEquals("oss-example", result.bucketInfo?.bucket?.name)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetBucketInfoResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = BucketInfo {
            bucket = Bucket {
                blockPublicAccess = true
                crossRegionReplication = "Disabled"
                intranetEndpoint = "oss-cn-hangzhou-internal.aliyuncs.com"
                location = "oss-cn-hangzhou"
                versioning = "Enabled"
                serverSideEncryptionRule = ServerSideEncryptionRule {
                    sSEAlgorithm = "KMS"
                    kMSMasterKeyID = "kMSMasterKeyID"
                    kMSDataEncryption = "SM4"
                }
                accessMonitor = "Enabled"
                creationDate = "2013-07-31T10:56:21.000Z"
                resourceGroupId = "rg-aek27tc********"
                transferAcceleration = "Disabled"
                accessControlList = AccessControlList {
                    grant = "private"
                }
                storageClass = "IA"
                owner = Owner {
                    id = "27183473914****"
                    displayName = "username"
                }
                bucketPolicy = BucketPolicy {
                    logBucket = "examplebucket"
                    logPrefix = "log/"
                }
                dataRedundancyType = "LRS"
                comment = "test"
                extranetEndpoint = "oss-cn-hangzhou.aliyuncs.com"
                name = "oss-example"
            }
        }

        val result = GetBucketInfoResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(true, result.bucketInfo?.bucket?.blockPublicAccess)
        assertEquals("Disabled", result.bucketInfo?.bucket?.crossRegionReplication)
        assertEquals("oss-cn-hangzhou-internal.aliyuncs.com", result.bucketInfo?.bucket?.intranetEndpoint)
        assertEquals("oss-cn-hangzhou", result.bucketInfo?.bucket?.location)
        assertEquals("Enabled", result.bucketInfo?.bucket?.versioning)
        assertEquals("kMSMasterKeyID", result.bucketInfo?.bucket?.serverSideEncryptionRule?.kMSMasterKeyID)
        assertEquals("KMS", result.bucketInfo?.bucket?.serverSideEncryptionRule?.sSEAlgorithm)
        assertEquals("SM4", result.bucketInfo?.bucket?.serverSideEncryptionRule?.kMSDataEncryption)
        assertEquals("Enabled", result.bucketInfo?.bucket?.accessMonitor)
        assertEquals("2013-07-31T10:56:21.000Z", result.bucketInfo?.bucket?.creationDate)
        assertEquals("rg-aek27tc********", result.bucketInfo?.bucket?.resourceGroupId)
        assertEquals("Disabled", result.bucketInfo?.bucket?.transferAcceleration)
        assertEquals("private", result.bucketInfo?.bucket?.accessControlList?.grant)
        assertEquals("IA", result.bucketInfo?.bucket?.storageClass)
        assertEquals("27183473914****", result.bucketInfo?.bucket?.owner?.id)
        assertEquals("username", result.bucketInfo?.bucket?.owner?.displayName)
        assertEquals("examplebucket", result.bucketInfo?.bucket?.bucketPolicy?.logBucket)
        assertEquals("log/", result.bucketInfo?.bucket?.bucketPolicy?.logPrefix)
        assertEquals("LRS", result.bucketInfo?.bucket?.dataRedundancyType)
        assertEquals("test", result.bucketInfo?.bucket?.comment)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result.bucketInfo?.bucket?.extranetEndpoint)
        assertEquals("oss-example", result.bucketInfo?.bucket?.name)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
