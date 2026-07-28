package com.aliyun.kotlin.sdk.service.oss2.agentic.internal

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.internal.parseUrl
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.types.BucketNameResolver
import com.aliyun.kotlin.sdk.service.oss2.types.EndpointProvider
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils

/**
 * Resolves the physical bucket name and request URL for agentic buckets and bucket spaces.
 *
 * The physical name is `{bucket}-{accountId}-{region}-{suffix}` where suffix is `ab-apsr`
 * for agentic buckets or `bs-apsr` for bucket spaces.
 */
internal class AgenticProvider(
    endpoint: String,
    private val accountId: String,
    private val region: String,
    private val suffix: String,
    private val addressStyle: AddressStyleType = AddressStyleType.VirtualHosted,
) : EndpointProvider, BucketNameResolver {

    private val scheme: String
    private val authority: String

    init {
        val parsed = parseUrl(endpoint)
        scheme = parsed?.get("scheme") ?: "https"
        authority = parsed?.get("authority") ?: ""
    }

    private fun fullName(bucket: String): String {
        require(accountId.isNotEmpty()) { "missing required field, AccountId" }
        require(region.isNotEmpty()) { "missing required field, Region" }
        return "$bucket-$accountId-$region-$suffix"
    }

    override fun buildBucketName(input: OperationInput): String {
        val bucket = input.bucket
        return if (bucket.isNullOrEmpty()) bucket.orEmpty() else fullName(bucket)
    }

    override fun buildURL(input: OperationInput): String {
        val paths: MutableList<String> = mutableListOf()
        var host = authority
        val bucket = input.bucket

        if (!bucket.isNullOrEmpty()) {
            when (addressStyle) {
                AddressStyleType.Path -> {
                    paths.add(fullName(bucket))
                    if (input.key == null) {
                        paths.add("")
                    }
                }
                else -> {
                    val name = fullName(bucket)
                    require(name.length <= 63) {
                        "the host label \"$name\" exceeds the maximum length of 63 characters"
                    }
                    host = "$name.$authority"
                }
            }
        }

        if (input.key != null) {
            paths.add(HttpUtils.urlEncodePath(input.key))
        }

        return "$scheme://$host/${paths.joinToString("/")}"
    }
}
