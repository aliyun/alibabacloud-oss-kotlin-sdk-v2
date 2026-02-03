package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketResourceGroup
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketResourceGroup
import com.aliyun.kotlin.sdk.service.oss2.extension.models.BucketResourceGroupConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketResourceGroupRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketResourceGroupRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketResourceGroupTest: TestBase() {

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
    fun testPutAndGetBucketResourceGroup() = runTest {
        val resourceGroupId = defaultClient.getBucketInfo(GetBucketInfoRequest {
            bucket = bucketName
        }).bucketInfo?.bucket?.resourceGroupId
        val configuration = BucketResourceGroupConfiguration {
            this.resourceGroupId = resourceGroupId
        }
        val putResult = defaultClient.putBucketResourceGroup(PutBucketResourceGroupRequest {
            bucket = bucketName
            this.bucketResourceGroupConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketResourceGroup(GetBucketResourceGroupRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.bucketResourceGroupConfiguration)
    }

    @Test
    fun testPutBucketResourceGroupWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketResourceGroup(PutBucketResourceGroupRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketResourceGroup(PutBucketResourceGroupRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.bucketResourceGroupConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketResourceGroup(PutBucketResourceGroupRequest {
                bucket = bucketName
                bucketResourceGroupConfiguration = BucketResourceGroupConfiguration{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketResourceGroupWithException() = runTest {
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketResourceGroup(GetBucketResourceGroupRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketResourceGroup(GetBucketResourceGroupRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
