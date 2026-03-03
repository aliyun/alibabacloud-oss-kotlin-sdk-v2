package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketLoggingTest {

    @Test
    fun deserializeXmlBodyIncludesLoggingRole() {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <BucketLoggingStatus>
            <LoggingEnabled>
            <TargetBucket>targetBucket</TargetBucket>
            <TargetPrefix>targetPrefix</TargetPrefix>
            <LoggingRole>loggingRole</LoggingRole>
            </LoggingEnabled>
            </BucketLoggingStatus>
        """.trimIndent()
        val status = SerdeUtils.deserializeXmlBody<BucketLoggingStatus>(xml.encodeToByteArray())
        assertEquals("targetBucket", status.loggingEnabled?.targetBucket)
        assertEquals("targetPrefix", status.loggingEnabled?.targetPrefix)
        assertEquals("loggingRole", status.loggingEnabled?.loggingRole)
    }

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketLoggingRequest {}
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
        val request = GetBucketLoggingRequest {
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
        val builder = GetBucketLoggingRequest.Builder()
        builder.bucket = "bucket"

        val request = GetBucketLoggingRequest(builder)
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
        val result = GetBucketLoggingResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.bucketLoggingStatus)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = "targetBucket"
                targetPrefix = "targetPrefix"
                loggingRole = "loggingRole"
            }
        }
        val result = GetBucketLoggingResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = bucketLoggingStatus
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(bucketLoggingStatus, result.bucketLoggingStatus)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val bucketLoggingStatus = BucketLoggingStatus {
            loggingEnabled = LoggingEnabled {
                targetBucket = "targetBucket"
                targetPrefix = "targetPrefix"
                loggingRole = "loggingRole"
            }
        }
        val builder = GetBucketLoggingResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = bucketLoggingStatus

        val result = GetBucketLoggingResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(bucketLoggingStatus, result.bucketLoggingStatus)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
