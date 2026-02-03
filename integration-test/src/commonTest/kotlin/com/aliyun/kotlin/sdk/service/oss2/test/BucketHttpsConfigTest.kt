package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketHttpsConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketHttpsConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketHttpsConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.HttpsConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketHttpsConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.TLS
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

class BucketHttpsConfigTest: TestBase() {

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
    fun testPutAndGetBucketHttpsConfig() = runTest {
        val configuration = HttpsConfiguration {
            tls = TLS {
                enable = true
                tlsVersions = listOf(
                    "TLSv1.2",
                    "TLSv1.3"
                )
            }
        }
        val putResult = defaultClient.putBucketHttpsConfig(PutBucketHttpsConfigRequest {
            bucket = bucketName
            this.httpsConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketHttpsConfig(GetBucketHttpsConfigRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.httpsConfiguration)
    }

    @Test
    fun testPutBucketHttpsConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketHttpsConfig(PutBucketHttpsConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketHttpsConfig(PutBucketHttpsConfigRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.httpsConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketHttpsConfig(PutBucketHttpsConfigRequest {
                bucket = bucketName
                httpsConfiguration = HttpsConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketHttpsConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketHttpsConfig(GetBucketHttpsConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketHttpsConfig(GetBucketHttpsConfigRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
