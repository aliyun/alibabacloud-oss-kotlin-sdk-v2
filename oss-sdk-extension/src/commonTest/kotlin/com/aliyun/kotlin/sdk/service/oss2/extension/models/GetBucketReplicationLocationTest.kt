package com.aliyun.kotlin.sdk.service.oss2.extension.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketReplicationLocationTest {
    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketReplicationLocationRequest {}
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
        val request = GetBucketReplicationLocationRequest {
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
        val builder = GetBucketReplicationLocationRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketReplicationLocationRequest(builder)
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
        val result = GetBucketReplicationLocationResult {}
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
        val configuration = ReplicationLocation {
            locations = listOf(
                "oss-cn-beijing",
                "oss-cn-qingdao",
                "oss-cn-shenzhen",
                "oss-cn-hongkong",
                "oss-us-west-1"
            )
            locationTransferTypeConstraint = LocationTransferTypeConstraint {
                locationTransferTypes = listOf(
                    LocationTransferType {
                        transferTypes = TransferTypes {
                            types = listOf(
                                "oss_acc"
                            )
                        }
                        location = "oss-cn-hongkong"
                    },
                    LocationTransferType {
                        transferTypes = TransferTypes {
                            types = listOf(
                                "oss_acc"
                            )
                        }
                        location = "oss-us-west-1"
                    }
                )
            }
            locationRTCConstraint = LocationRTCConstraint {
                locations = listOf(
                    "oss-cn-hongkong"
                )
            }
        }
        val result = GetBucketReplicationLocationResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = configuration
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.replicationLocation)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val configuration = ReplicationLocation {
            locations = listOf(
                "oss-cn-beijing",
                "oss-cn-qingdao",
                "oss-cn-shenzhen",
                "oss-cn-hongkong",
                "oss-us-west-1"
            )
            locationTransferTypeConstraint = LocationTransferTypeConstraint {
                locationTransferTypes = listOf(
                    LocationTransferType {
                        transferTypes = TransferTypes {
                            types = listOf(
                                "oss_acc"
                            )
                        }
                        location = "oss-cn-hongkong"
                    },
                    LocationTransferType {
                        transferTypes = TransferTypes {
                            types = listOf(
                                "oss_acc"
                            )
                        }
                        location = "oss-us-west-1"
                    }
                )
            }
            locationRTCConstraint = LocationRTCConstraint {
                locations = listOf(
                    "oss-cn-hongkong"
                )
            }
        }
        val builder = GetBucketReplicationLocationResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = configuration

        val result = GetBucketReplicationLocationResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(configuration, result.replicationLocation)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
