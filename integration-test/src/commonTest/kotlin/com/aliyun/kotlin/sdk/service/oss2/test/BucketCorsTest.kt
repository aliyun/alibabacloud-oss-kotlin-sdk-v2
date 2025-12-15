package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSRule
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest
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

class BucketCorsTest: TestBase() {

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
    fun testPutAndGetBucketCors() = runTest {
        val corsConfiguration = CORSConfiguration {
            corsRules = listOf(
                CORSRule {
                    allowedOrigins = listOf("http://example.com", "http://example.net")
                    allowedMethods = listOf("GET")
                    allowedHeaders = listOf("authorization", "x-oss-test", "x-oss-test1")
                    exposeHeaders = listOf("x-oss-test2")
                    maxAgeSeconds = 100
                }
            )
            responseVary = true
        }
        defaultClient.putBucketCors(PutBucketCorsRequest {
            bucket = bucketName
            this.corsConfiguration = corsConfiguration
        })
        val result = defaultClient.getBucketCors(GetBucketCorsRequest {
            bucket = bucketName
        })
        assertEquals(1, result.corsConfiguration?.corsRules?.size)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedOrigins, result.corsConfiguration?.corsRules?.first()?.allowedOrigins)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedMethods, result.corsConfiguration?.corsRules?.first()?.allowedMethods)
        assertEquals(corsConfiguration.corsRules?.first()?.allowedHeaders, result.corsConfiguration?.corsRules?.first()?.allowedHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.exposeHeaders, result.corsConfiguration?.corsRules?.first()?.exposeHeaders)
        assertEquals(corsConfiguration.corsRules?.first()?.maxAgeSeconds, result.corsConfiguration?.corsRules?.first()?.maxAgeSeconds)
        assertEquals(corsConfiguration.responseVary, result.corsConfiguration?.responseVary)
    }

    @Test
    fun testPutBucketCorsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketCors(PutBucketCorsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketCors(PutBucketCorsRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.corsConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketCors(PutBucketCorsRequest {
                bucket = bucketName
                corsConfiguration = CORSConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketCorsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketCors(GetBucketCorsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketCors(GetBucketCorsRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketCors() = runTest {
        defaultClient.putBucketCors(PutBucketCorsRequest {
            bucket = bucketName
            corsConfiguration = CORSConfiguration {
                corsRules = listOf(
                    CORSRule {
                        allowedOrigins = listOf("http://example.com", "http://example.net")
                        allowedMethods = listOf("GET")
                        allowedHeaders = listOf("authorization", "x-oss-test", "x-oss-test1")
                        exposeHeaders = listOf("x-oss-test2")
                        maxAgeSeconds = 100
                    }
                )
                responseVary = true
            }
        })
        defaultClient.deleteBucketCors(DeleteBucketCorsRequest {
            bucket = bucketName
        })
        val exception = assertFails { defaultClient.getBucketCors(GetBucketCorsRequest {
            bucket = bucketName
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals(404, (exception.cause as ServiceException).statusCode)
        assertEquals("NoSuchCORSConfiguration", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testDeleteBucketCorsWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketCors(DeleteBucketCorsRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketCors(DeleteBucketCorsRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
