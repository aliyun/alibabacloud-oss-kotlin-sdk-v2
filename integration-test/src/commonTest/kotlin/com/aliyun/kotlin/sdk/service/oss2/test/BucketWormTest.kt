package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.abortBucketWorm
import com.aliyun.kotlin.sdk.service.oss2.extension.api.completeBucketWorm
import com.aliyun.kotlin.sdk.service.oss2.extension.api.extendBucketWorm
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketWorm
import com.aliyun.kotlin.sdk.service.oss2.extension.api.initiateBucketWorm
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.models.AbortBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CompleteBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CompleteBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ExtendBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ExtendWormConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InitiateBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InitiateWormConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BucketWormTest: TestBase() {

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
    fun testInitiateAndGetBucketWorm() = runTest {
        val initiateResult = defaultClient.initiateBucketWorm(InitiateBucketWormRequest{
            bucket = bucketName
            initiateWormConfiguration = InitiateWormConfiguration {
                retentionPeriodInDays = 1
            }
        })
        assertEquals(200, initiateResult.statusCode)

        val getResult = defaultClient.getBucketWorm(GetBucketWormRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(1, getResult.wormConfiguration?.retentionPeriodInDays)
        assertEquals(initiateResult.wormId, getResult.wormConfiguration?.wormId)
        assertEquals("InProgress", getResult.wormConfiguration?.state)
        assertNotNull(getResult.wormConfiguration?.creationDate)
        assertNotNull(getResult.wormConfiguration?.expirationDate)
    }

    @Test
    fun testInitiateBucketWormWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.initiateBucketWorm(InitiateBucketWormRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.initiateBucketWorm(InitiateBucketWormRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.initiateWormConfiguration is required")

        exception = assertFails {
            invalidClient.initiateBucketWorm(InitiateBucketWormRequest {
                bucket = bucketName
                initiateWormConfiguration = InitiateWormConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketWormWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketWorm(GetBucketWormRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketWorm(GetBucketWormRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testCompleteAndExtendBucketWorm() = runTest {
        val initiateResult = defaultClient.initiateBucketWorm(InitiateBucketWormRequest{
            bucket = bucketName
            initiateWormConfiguration = InitiateWormConfiguration {
                retentionPeriodInDays = 1
            }
        })
        assertEquals(200, initiateResult.statusCode)

        val completeResult = defaultClient.completeBucketWorm(CompleteBucketWormRequest {
            bucket = bucketName
            wormId = initiateResult.wormId
        })
        assertEquals(200, completeResult.statusCode)

        defaultClient.extendBucketWorm(ExtendBucketWormRequest {
            bucket = bucketName
            wormId = initiateResult.wormId
            extendWormConfiguration = ExtendWormConfiguration {
                retentionPeriodInDays = 10
            }
        })

        val getResult = defaultClient.getBucketWorm(GetBucketWormRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(10, getResult.wormConfiguration?.retentionPeriodInDays)
        assertEquals(initiateResult.wormId, getResult.wormConfiguration?.wormId)
        assertEquals("Locked", getResult.wormConfiguration?.state)
        assertNotNull(getResult.wormConfiguration?.creationDate)
    }

    @Test
    fun testCompleteBucketWormWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.completeBucketWorm(CompleteBucketWormRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.completeBucketWorm(CompleteBucketWormRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.wormId is required")

        exception = assertFails {
            invalidClient.completeBucketWorm(CompleteBucketWormRequest {
                bucket = bucketName
                wormId = "wormId"
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testExtendBucketWormWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.extendBucketWorm(ExtendBucketWormRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.extendBucketWorm(ExtendBucketWormRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.wormId is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.extendBucketWorm(ExtendBucketWormRequest {
                bucket = bucketName
                wormId = "wormId"
            })
        }
        assertEquals(exception.message, "request.extendWormConfiguration is required")

        exception = assertFails {
            invalidClient.extendBucketWorm(ExtendBucketWormRequest {
                bucket = bucketName
                wormId = "wormId"
                extendWormConfiguration = ExtendWormConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testAbortBucketWorm() = runTest {
        val initiateResult = defaultClient.initiateBucketWorm(InitiateBucketWormRequest{
            bucket = bucketName
            initiateWormConfiguration = InitiateWormConfiguration {
                retentionPeriodInDays = 1
            }
        })
        assertEquals(200, initiateResult.statusCode)

        val result = defaultClient.abortBucketWorm(AbortBucketWormRequest {
            bucket = bucketName
        })
        assertEquals(204, result.statusCode)
    }

    @Test
    fun testAbortBucketWormWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.abortBucketWorm(AbortBucketWormRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.abortBucketWorm(AbortBucketWormRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
