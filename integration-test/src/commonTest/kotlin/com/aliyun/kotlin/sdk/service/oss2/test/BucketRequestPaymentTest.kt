package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketRequestPayment
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putBucketRequestPayment
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketRequestPaymentRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketRequestPaymentRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RequestPaymentConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BucketRequestPaymentTest : TestBase() {

    @Test
    fun testPutAndGetBucketRequestPayment() = bucketTest { bucketName ->
        val configuration = RequestPaymentConfiguration {
            payer = "Requester"
        }
        val putResult = defaultClient.putBucketRequestPayment(PutBucketRequestPaymentRequest {
            bucket = bucketName
            this.requestPaymentConfiguration = configuration
        })
        assertEquals(200, putResult.statusCode)

        val result = defaultClient.getBucketRequestPayment(GetBucketRequestPaymentRequest {
            bucket = bucketName
        })
        assertEquals(200, result.statusCode)
        assertEquals(configuration, result.requestPaymentConfiguration)
    }

    @Test
    fun testPutBucketRequestPaymentWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketRequestPayment(PutBucketRequestPaymentRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putBucketRequestPayment(PutBucketRequestPaymentRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.requestPaymentConfiguration is required")

        exception = assertFails {
            invalidClient.putBucketRequestPayment(PutBucketRequestPaymentRequest {
                bucket = bucketName
                requestPaymentConfiguration = RequestPaymentConfiguration {}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetBucketRequestPaymentWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getBucketRequestPayment(GetBucketRequestPaymentRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.getBucketRequestPayment(GetBucketRequestPaymentRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
