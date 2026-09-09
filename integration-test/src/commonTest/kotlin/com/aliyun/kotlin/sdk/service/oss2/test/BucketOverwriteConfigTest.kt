package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketOverwriteConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketOverwriteConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketOverwriteConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSRule
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketOverwriteConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketOverwriteConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OverwriteConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OverwriteConfiguration.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OverwriteRule
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OverwriteRule.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.Principals
import com.aliyun.kotlin.sdk.service.oss2.extension.models.Principals.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketOverwriteConfigRequest
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

class BucketOverwriteConfigTest: TestBase() {

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
    fun testPutAndGetBucketOverwriteConfig() = runTest {
        val overwriteConfiguration = OverwriteConfiguration {
            rules = listOf(
                OverwriteRule {
                    id = "rule-002"
                    prefix = "images"
                    action = "forbid"
                },
                OverwriteRule {
                    id = "rule-003"
                    suffix = ".jpg"
                    action = "forbid"
                }
            )
        }
        defaultClient.putBucketOverwriteConfig(PutBucketOverwriteConfigRequest {
            bucket = bucketName
            this.overwriteConfiguration = overwriteConfiguration
        })
        val result = defaultClient.getBucketOverwriteConfig(GetBucketOverwriteConfigRequest {
            bucket = bucketName
        })
        assertEquals(2, result.overwriteConfiguration?.rules?.size)
        assertEquals(overwriteConfiguration, result.overwriteConfiguration)
    }

    @Test
    fun testPutBucketOverwriteConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketOverwriteConfig(PutBucketOverwriteConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketOverwriteConfig(PutBucketOverwriteConfigRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.overwriteConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketOverwriteConfig(PutBucketOverwriteConfigRequest {
                bucket = bucketName
                overwriteConfiguration = OverwriteConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketOverwriteConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketOverwriteConfig(GetBucketOverwriteConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketOverwriteConfig(GetBucketOverwriteConfigRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketOverwriteConfig() = runTest {
        defaultClient.putBucketOverwriteConfig(PutBucketOverwriteConfigRequest {
            bucket = bucketName
            overwriteConfiguration = OverwriteConfiguration {
                rules = listOf(
                    OverwriteRule {
                        id = "rule-002"
                        prefix = "images"
                        action = "forbid"
                    },
                    OverwriteRule {
                        id = "rule-003"
                        suffix = ".jpg"
                        action = "forbid"
                    }
                )
            }
        })
        defaultClient.deleteBucketOverwriteConfig(DeleteBucketOverwriteConfigRequest {
            bucket = bucketName
        })
        val exception = assertFails { defaultClient.getBucketOverwriteConfig(GetBucketOverwriteConfigRequest {
            bucket = bucketName
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals(404, (exception.cause as ServiceException).statusCode)
        assertEquals("NoSuchBucketOverwriteConfig", (exception.cause as ServiceException).errorCode)
    }

    @Test
    fun testDeleteBucketOverwriteConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketOverwriteConfig(DeleteBucketOverwriteConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketOverwriteConfig(DeleteBucketOverwriteConfigRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
