package com.aliyun.kotlin.sdk.service.oss2.agentic.internal

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import kotlin.test.Test
import kotlin.test.assertEquals

class AgenticProviderTest {

    private val endpoint = "https://oss-cn-hangzhou.aliyuncs.com"

    private fun provider(suffix: String) =
        AgenticProvider(endpoint, accountId = "1250000000", region = "cn-hangzhou", suffix = suffix)

    @Test
    fun testBuildBucketNameAgentic() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }
        assertEquals("example-1250000000-cn-hangzhou-ab-apsr", provider("ab-apsr").buildBucketName(input))
    }

    @Test
    fun testBuildBucketNameBucketSpace() {
        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            bucket = "space"
        }
        assertEquals("space-1250000000-cn-hangzhou-bs-apsr", provider("bs-apsr").buildBucketName(input))
    }

    @Test
    fun testBuildBucketNameNullBucket() {
        val input = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
        }
        assertEquals("", provider("ab-apsr").buildBucketName(input))
    }

    @Test
    fun testBuildUrlWithBucketAndKey() {
        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            bucket = "space"
            key = "dir/obj.txt"
        }
        assertEquals(
            "https://space-1250000000-cn-hangzhou-bs-apsr.oss-cn-hangzhou.aliyuncs.com/dir/obj.txt",
            provider("bs-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlWithBucketNoKey() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }
        assertEquals(
            "https://example-1250000000-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/",
            provider("ab-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlNoBucket() {
        val input = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/",
            provider("ab-apsr").buildURL(input),
        )
    }
}
