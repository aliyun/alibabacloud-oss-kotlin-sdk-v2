package com.aliyun.kotlin.sdk.service.oss2.agentic.internal

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AgenticProviderTest {

    private val endpoint = "https://oss-cn-hangzhou.aliyuncs.com"

    private fun provider(suffix: String) =
        AgenticProvider(endpoint, accountId = "1250000000", region = "cn-hangzhou", suffix = suffix)

    private fun pathStyleProvider(suffix: String) =
        AgenticProvider(endpoint, accountId = "1250000000", region = "cn-hangzhou", suffix = suffix, addressStyle = AddressStyleType.Path)

    private fun aliasStyleProvider(suffix: String, accountId: String = "1250000000", region: String = "cn-hangzhou") =
        AgenticProvider(endpoint, accountId = accountId, region = region, suffix = suffix, addressStyle = AddressStyleType.VirtualHostedAlias)

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

    @Test
    fun testBuildUrlPathStyleWithBucketNoKey() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/example-1250000000-cn-hangzhou-ab-apsr/",
            pathStyleProvider("ab-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlPathStyleWithBucketAndKey() {
        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            bucket = "space"
            key = "dir/obj.txt"
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/space-1250000000-cn-hangzhou-bs-apsr/dir/obj.txt",
            pathStyleProvider("bs-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlPathStyleNoBucket() {
        val input = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/",
            pathStyleProvider("ab-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlAliasStyleWithBucketNoKey() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }
        assertEquals(
            "https://example-alias-ab-apsr.oss-cn-hangzhou.aliyuncs.com/",
            aliasStyleProvider("ab-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlAliasStyleWithBucketAndKey() {
        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            bucket = "space"
            key = "dir/obj.txt"
        }
        assertEquals(
            "https://space-alias-bs-apsr.oss-cn-hangzhou.aliyuncs.com/dir/obj.txt",
            aliasStyleProvider("bs-apsr").buildURL(input),
        )
    }

    @Test
    fun testBuildUrlAliasStyleNoBucket() {
        val input = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/",
            aliasStyleProvider("ab-apsr").buildURL(input),
        )
    }

    @Test
    fun testAliasStyleSignsWithFullName() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }

        // the short label only shows up in the host, signing keeps the full name
        assertEquals(
            "example-1250000000-cn-hangzhou-ab-apsr",
            aliasStyleProvider("ab-apsr").buildBucketName(input),
        )

        // so accountId / region stay required
        val noAccount = aliasStyleProvider("ab-apsr", accountId = "")
        assertEquals(true, assertFailsWith<IllegalArgumentException> { noAccount.buildBucketName(input) }.message!!.contains("AccountId"))

        val noRegion = aliasStyleProvider("ab-apsr", region = "")
        assertEquals(true, assertFailsWith<IllegalArgumentException> { noRegion.buildBucketName(input) }.message!!.contains("Region"))
    }

    @Test
    fun testAliasHostLabelTooLong() {
        // alias label = "{bucket}-alias-ab-apsr" -> len(bucket) + 14
        val suffixPart = "-alias-ab-apsr"

        // boundary: label == 63 (bucket 49) is allowed, far more room than the full name has
        val okName = "a".repeat(49)
        assertEquals(63, (okName + suffixPart).length)
        assertEquals(
            "https://$okName$suffixPart.oss-cn-hangzhou.aliyuncs.com/",
            aliasStyleProvider("ab-apsr").buildURL(
                OperationInput {
                    opName = "GetAgenticBucket"
                    method = "GET"
                    bucket = okName
                }
            ),
        )

        // over limit: label == 64 (bucket 50) is rejected
        val longName = "a".repeat(50)
        assertEquals(64, (longName + suffixPart).length)
        val ex = assertFailsWith<IllegalArgumentException> {
            aliasStyleProvider("ab-apsr").buildURL(
                OperationInput {
                    opName = "GetAgenticBucket"
                    method = "GET"
                    bucket = longName
                }
            )
        }
        assertEquals(true, ex.message!!.contains("exceeds the maximum length of 63 characters"))
    }

    @Test
    fun testMissingRequiredFields() {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            bucket = "example"
        }

        // missing accountId
        val p1 = AgenticProvider(endpoint, accountId = "", region = "cn-hangzhou", suffix = "ab-apsr")
        assertEquals(true, assertFailsWith<IllegalArgumentException> { p1.buildURL(input) }.message!!.contains("AccountId"))
        assertEquals(true, assertFailsWith<IllegalArgumentException> { p1.buildBucketName(input) }.message!!.contains("AccountId"))

        // missing region
        val p2 = AgenticProvider(endpoint, accountId = "1250000000", region = "", suffix = "ab-apsr")
        assertEquals(true, assertFailsWith<IllegalArgumentException> { p2.buildURL(input) }.message!!.contains("Region"))
        assertEquals(true, assertFailsWith<IllegalArgumentException> { p2.buildBucketName(input) }.message!!.contains("Region"))

        // no bucket: validation is skipped, no error
        val noBucket = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
        }
        assertEquals("", p2.buildBucketName(noBucket))
    }

    @Test
    fun testHostLabelTooLong() {
        // full name = "{bucket}-1250000000-cn-hangzhou-ab-apsr" -> len(bucket) + 31
        val suffixPart = "-1250000000-cn-hangzhou-ab-apsr"

        // boundary: full name == 63 (bucket 32) is allowed in virtual-hosted style
        val okName = "a".repeat(32)
        assertEquals(63, (okName + suffixPart).length)
        assertEquals(
            "https://$okName$suffixPart.oss-cn-hangzhou.aliyuncs.com/",
            provider("ab-apsr").buildURL(
                OperationInput {
                    opName = "GetAgenticBucket"
                    method = "GET"
                    bucket = okName
                }
            ),
        )

        // over limit: full name == 64 (bucket 33) is rejected in virtual-hosted style
        val longName = "a".repeat(33)
        assertEquals(64, (longName + suffixPart).length)
        val ex = assertFailsWith<IllegalArgumentException> {
            provider("ab-apsr").buildURL(
                OperationInput {
                    opName = "GetAgenticBucket"
                    method = "GET"
                    bucket = longName
                }
            )
        }
        assertEquals(true, ex.message!!.contains("exceeds the maximum length of 63 characters"))

        // path style has no DNS label limit, so the same long name is fine
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/$longName$suffixPart/",
            pathStyleProvider("ab-apsr").buildURL(
                OperationInput {
                    opName = "GetAgenticBucket"
                    method = "GET"
                    bucket = longName
                }
            ),
        )
    }
}
