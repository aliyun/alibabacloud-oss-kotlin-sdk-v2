package com.aliyun.kotlin.sdk.service.oss2.agentic.internal

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.internal.parseUrl
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
) : EndpointProvider, BucketNameResolver {

    private val scheme: String
    private val authority: String

    init {
        val parsed = parseUrl(endpoint)
        scheme = parsed?.get("scheme") ?: "https"
        authority = parsed?.get("authority") ?: ""
    }

    private fun fullName(bucket: String): String = "$bucket-$accountId-$region-$suffix"

    override fun buildBucketName(input: OperationInput): String {
        val bucket = input.bucket
        return if (bucket.isNullOrEmpty()) bucket.orEmpty() else fullName(bucket)
    }

    override fun buildURL(input: OperationInput): String {
        val host = input.bucket?.let { "${fullName(it)}.$authority" } ?: authority
        val path = input.key?.let { HttpUtils.urlEncodePath(it) } ?: ""
        return "$scheme://$host/$path"
    }
}
