package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketReplication
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketReplication
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketReplicationLocation
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketReplicationProgress
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketReplication
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketRtc
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketReplicationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketReplicationLocationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketReplicationProgressRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketReplicationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketReplicationRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketRtcRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RTC
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ReplicationConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ReplicationDestination
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ReplicationPrefixSet
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ReplicationRule
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ReplicationRules
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RtcConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketReplicationTest: TestBase() {

    val bucketName: String = randomBucketName()

    @BeforeTest
    fun putBucket() = runTest {
        defaultClient.putBucket(PutBucketRequest {
            bucket = bucketName
        })
    }

    @AfterTest
    fun cleanAndDeleteBucket() = runTest {
        defaultClient.deleteBucket(DeleteBucketRequest {
            bucket = bucketName
        })
    }

    @Test
    fun testPutAndGetBucketReplication() = runTest {
        val destBucket = randomBucketName()
        defaultClient.putBucket(PutBucketRequest {
            bucket = destBucket
        })

        val configuration = ReplicationConfiguration {
            rules = listOf(
                ReplicationRule {
                    prefixSet = ReplicationPrefixSet {
                        prefixes = listOf(
                            "prefix_1",
                            "prefix_2"
                        )
                    }
                    destination = ReplicationDestination {
                        bucket = destBucket
                        location = "oss-$OSS_TEST_REGION"
                    }
                    historicalObjectReplication = "disabled"
                    id = "id"
                    action = "ALL"
                    status = "Enabled"
                }
            )
        }
        val putResult = defaultClient.putBucketReplication(PutBucketReplicationRequest {
            bucket = bucketName
            replicationConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val getResult = defaultClient.getBucketReplication(GetBucketReplicationRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(
            ReplicationConfiguration {
                rules = listOf(
                    ReplicationRule {
                        prefixSet = ReplicationPrefixSet {
                            prefixes = listOf(
                                "prefix_1",
                                "prefix_2"
                            )
                        }
                        destination = ReplicationDestination {
                            bucket = destBucket
                            location = "oss-$OSS_TEST_REGION"
                        }
                        historicalObjectReplication = "disabled"
                        id = "id"
                        action = "ALL"
                        status = "starting"
                    }
                )
            },
            getResult.replicationConfiguration
        )

        defaultClient.deleteBucket(DeleteBucketRequest {
            bucket = destBucket
        })
    }

    @Test
    fun testPutBucketReplicationWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketReplication(PutBucketReplicationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.replicationConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
                replicationConfiguration = ReplicationConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketReplicationWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketReplication(GetBucketReplicationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketReplication(GetBucketReplicationRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testPutBucketRTC() = runTest {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            region = "cn-beijing"
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        }).use { client ->
            val destBucket = randomBucketName()
            client.putBucket(PutBucketRequest {
                bucket = destBucket
            })

            val configuration = ReplicationConfiguration {
                rules = listOf(
                    ReplicationRule {
                        prefixSet = ReplicationPrefixSet {
                            prefixes = listOf(
                                "prefix_1",
                                "prefix_2"
                            )
                        }
                        destination = ReplicationDestination {
                            bucket = destBucket
                            location = "oss-cn-beijing"
                        }
                        historicalObjectReplication = "disabled"
                        id = "id"
                        action = "ALL"
                        status = "Enabled"
                    }
                )
            }
            val putResult = defaultClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
                replicationConfiguration = configuration
            })
            assertEquals(200, putResult.statusCode)

            val result = defaultClient.putBucketRtc(PutBucketRtcRequest {
                bucket = bucketName
                rtcConfiguration = RtcConfiguration {
                    rtc = RTC {
                        status = "Enabled"
                    }
                    id = "id"
                }
            })
            assertEquals(200, result.statusCode)

            client.deleteBucket(DeleteBucketRequest {
                bucket = destBucket
            })
        }
    }

    @Test
    fun testPutBucketRTCWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketRtc(PutBucketRtcRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketRtc(PutBucketRtcRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.rtcConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketRtc(PutBucketRtcRequest {
                bucket = bucketName
                rtcConfiguration = RtcConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketReplicationLocation() = runTest {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            region = "cn-beijing"
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        }).use { client ->
            val destBucket = randomBucketName()
            client.putBucket(PutBucketRequest {
                bucket = destBucket
            })

            val configuration = ReplicationConfiguration {
                rules = listOf(
                    ReplicationRule {
                        prefixSet = ReplicationPrefixSet {
                            prefixes = listOf(
                                "prefix_1",
                                "prefix_2"
                            )
                        }
                        destination = ReplicationDestination {
                            bucket = destBucket
                            location = "oss-cn-beijing"
                        }
                        historicalObjectReplication = "disabled"
                        id = "id"
                        action = "ALL"
                        status = "Enabled"
                    }
                )
            }
            val putResult = defaultClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
                replicationConfiguration = configuration
            })
            assertEquals(200, putResult.statusCode)

            val result = defaultClient.getBucketReplicationLocation(GetBucketReplicationLocationRequest {
                bucket = bucketName
            })
            assertEquals(200, result.statusCode)
            assertEquals(true, result.replicationLocation?.locations?.isNotEmpty())
            assertEquals(true, result.replicationLocation?.locationRTCConstraint?.locations?.isNotEmpty())

            client.deleteBucket(DeleteBucketRequest {
                bucket = destBucket
            })
        }
    }

    @Test
    fun testGetBucketReplicationLocationWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketReplicationLocation(GetBucketReplicationLocationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketReplicationLocation(GetBucketReplicationLocationRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketReplicationProgress() = runTest {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            region = "cn-beijing"
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        }).use { client ->
            val destBucket = randomBucketName()
            client.putBucket(PutBucketRequest {
                bucket = destBucket
            })

            val configuration = ReplicationConfiguration {
                rules = listOf(
                    ReplicationRule {
                        prefixSet = ReplicationPrefixSet {
                            prefixes = listOf(
                                "prefix_1",
                                "prefix_2"
                            )
                        }
                        destination = ReplicationDestination {
                            bucket = destBucket
                            location = "oss-cn-beijing"
                        }
                        historicalObjectReplication = "disabled"
                        id = "id"
                        action = "ALL"
                        status = "Enabled"
                    }
                )
            }
            val putResult = defaultClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
                replicationConfiguration = configuration
            })
            assertEquals(200, putResult.statusCode)

            val result = defaultClient.getBucketReplicationProgress(
                GetBucketReplicationProgressRequest {
                    bucket = bucketName
                })
            assertEquals(200, result.statusCode)
            assertEquals(1, result.replicationProgress?.rules?.size)
            assertEquals(configuration.rules?.get(0)?.id, result.replicationProgress?.rules?.get(0)?.id)
            assertEquals(configuration.rules?.get(0)?.action, result.replicationProgress?.rules?.get(0)?.action)
            assertEquals(configuration.rules?.get(0)?.historicalObjectReplication, result.replicationProgress?.rules?.get(0)?.historicalObjectReplication)
            assertEquals(configuration.rules?.get(0)?.destination, result.replicationProgress?.rules?.get(0)?.destination)
            assertEquals(configuration.rules?.get(0)?.prefixSet, result.replicationProgress?.rules?.get(0)?.prefixSet)

            client.deleteBucket(DeleteBucketRequest {
                bucket = destBucket
            })
        }
    }

    @Test
    fun testGetBucketReplicationProgressWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketReplicationProgress(GetBucketReplicationProgressRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketReplicationProgress(GetBucketReplicationProgressRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketReplication() = runTest {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            region = "cn-beijing"
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        }).use { client ->
            val destBucket = randomBucketName()
            client.putBucket(PutBucketRequest {
                bucket = destBucket
            })

            val configuration = ReplicationConfiguration {
                rules = listOf(
                    ReplicationRule {
                        prefixSet = ReplicationPrefixSet {
                            prefixes = listOf(
                                "prefix_1",
                                "prefix_2"
                            )
                        }
                        destination = ReplicationDestination {
                            bucket = destBucket
                            location = "oss-cn-beijing"
                        }
                        historicalObjectReplication = "disabled"
                        id = "id"
                        action = "ALL"
                        status = "Enabled"
                    }
                )
            }
            val putResult = defaultClient.putBucketReplication(PutBucketReplicationRequest {
                bucket = bucketName
                replicationConfiguration = configuration
            })
            assertEquals(200, putResult.statusCode)

            val result = defaultClient.deleteBucketReplication(DeleteBucketReplicationRequest {
                bucket = bucketName
                replicationRules = ReplicationRules {
                    id = "id"
                }
            })
            assertEquals(200, result.statusCode)

            client.deleteBucket(DeleteBucketRequest {
                bucket = destBucket
            })
        }
    }

    @Test
    fun testDeleteBucketReplicationException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketReplication(DeleteBucketReplicationRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketReplication(DeleteBucketReplicationRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.replicationRules is required")

        exception = assertFails {
            invalidClient.deleteBucketReplication(DeleteBucketReplicationRequest {
                bucket = bucketName
                replicationRules = ReplicationRules{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
