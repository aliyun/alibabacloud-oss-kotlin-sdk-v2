package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteBucketLogging
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteUserDefinedLogFieldsConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketLogging
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getUserDefinedLogFieldsConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketLogging
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putUserDefinedLogFieldsConfig
import com.aliyun.kotlin.sdk.service.oss2.extension.models.BucketLoggingStatus
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketLoggingRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteUserDefinedLogFieldsConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketLoggingRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetUserDefinedLogFieldsConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.HeaderSet
import com.aliyun.kotlin.sdk.service.oss2.extension.models.LoggingEnabled
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ParamSet
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketLoggingRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutUserDefinedLogFieldsConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.UserDefinedLogFieldsConfiguration
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

class BucketLoggingTest: TestBase() {

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
    fun testPutAndGetBucketLogging() = runTest {
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = bucketName
                targetPrefix = "targetPrefix"
            }
        }
        val putResult = defaultClient.putBucketLogging(PutBucketLoggingRequest {
            bucket = bucketName
            this.bucketLoggingStatus = bucketLoggingStatus
        })
        assertEquals(200, putResult.statusCode)

        val getResult = defaultClient.getBucketLogging(GetBucketLoggingRequest {
            bucket = bucketName
        })
        assertEquals(200, getResult.statusCode)
        assertEquals(bucketLoggingStatus, getResult.bucketLoggingStatus)
    }

    @Test
    fun testPutBucketLoggingWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketLogging(PutBucketLoggingRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketLogging(PutBucketLoggingRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.bucketLoggingStatus is required")

        exception = assertFails {
            invalidClient.putBucketLogging(PutBucketLoggingRequest {
                bucket = bucketName
                bucketLoggingStatus = BucketLoggingStatus{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketLoggingWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketLogging(GetBucketLoggingRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketLogging(GetBucketLoggingRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucketLogging() = runTest {
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = bucketName
                targetPrefix = "targetPrefix"
            }
        }
        val putResult = defaultClient.putBucketLogging(PutBucketLoggingRequest {
            bucket = bucketName
            this.bucketLoggingStatus = bucketLoggingStatus
        })
        assertEquals(200, putResult.statusCode)

        val deleteResult = defaultClient.deleteBucketLogging(DeleteBucketLoggingRequest {
            bucket = bucketName
        })
        assertEquals(204, deleteResult.statusCode)
    }

    @Test
    fun testDeleteBucketLoggingWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteBucketLogging(DeleteBucketLoggingRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteBucketLogging(DeleteBucketLoggingRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testPutAndGetUserDefinedLogFieldsConfig() = runTest {
        val configuration = UserDefinedLogFieldsConfiguration {
            headerSet = HeaderSet {
                headers = listOf(
                    "header1",
                    "header2",
                    "header3"
                )
            }
            paramSet = ParamSet {
                parameters = listOf(
                    "param1",
                    "param2"
                )
            }
        }
        val putResult = defaultClient.putUserDefinedLogFieldsConfig(
            PutUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
                this.userDefinedLogFieldsConfiguration = configuration
            })
        assertEquals(200, putResult.statusCode)

        val getResult = defaultClient.getUserDefinedLogFieldsConfig(
            GetUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
            })
        assertEquals(200, getResult.statusCode)
        assertEquals(configuration, getResult.userDefinedLogFieldsConfiguration)
    }

    @Test
    fun testPutUserDefinedLogFieldsConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putUserDefinedLogFieldsConfig(PutUserDefinedLogFieldsConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putUserDefinedLogFieldsConfig(PutUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.userDefinedLogFieldsConfiguration is required")

        exception = assertFails {
            invalidClient.putUserDefinedLogFieldsConfig(PutUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
                userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetUserDefinedLogFieldsConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getUserDefinedLogFieldsConfig(GetUserDefinedLogFieldsConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getUserDefinedLogFieldsConfig(GetUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteUserDefinedLogFieldsConfig() = runTest {
        val configuration = UserDefinedLogFieldsConfiguration {
            headerSet = HeaderSet {
                headers = listOf(
                    "header1",
                    "header2",
                    "header3"
                )
            }
            paramSet = ParamSet {
                parameters = listOf(
                    "param1",
                    "param2"
                )
            }
        }
        val putResult = defaultClient.putUserDefinedLogFieldsConfig(PutUserDefinedLogFieldsConfigRequest {
            bucket = bucketName
            this.userDefinedLogFieldsConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val deleteResult = defaultClient.deleteUserDefinedLogFieldsConfig(
            DeleteUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
            })
        assertEquals(204, deleteResult.statusCode)
    }

    @Test
    fun testDeleteUserDefinedLogFieldsConfigWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteUserDefinedLogFieldsConfig(DeleteUserDefinedLogFieldsConfigRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.deleteUserDefinedLogFieldsConfig(DeleteUserDefinedLogFieldsConfigRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
