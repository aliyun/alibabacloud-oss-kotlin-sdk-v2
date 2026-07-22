package com.aliyun.kotlin.sdk.service.oss2.agentic

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.ClientOptions
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.internal.AgenticProvider
import com.aliyun.kotlin.sdk.service.oss2.agentic.internal.updateAgenticUserAgent

/**
 * Factory for an [OSSClient] that operates on the bucket spaces of an agentic bucket.
 *
 * Pass the short bucket name in each request; the returned client resolves it to the physical
 * form `{bucket}-{accountId}-{region}-bs-apsr`, where `accountId` and `region` come from the
 * [ClientConfiguration]. Use the standard object operations of the returned [OSSClient].
 */
public object BucketSpaceClient {

    /**
     * Creates an [OSSClient] whose request pipeline resolves bucket-space names.
     */
    public fun create(
        config: ClientConfiguration,
        optFns: List<(ClientOptions) -> ClientOptions>? = null,
    ): OSSClient {
        updateAgenticUserAgent(config)
        val accountId = config.accountId ?: ""
        val region = config.region ?: ""
        val bsFn: (ClientOptions) -> ClientOptions = { opts ->
            val provider = AgenticProvider(opts.endpoint, accountId, region, "bs-apsr", opts.addressStyle)
            opts.copy {
                endpointProvider = provider
                bucketNameResolver = provider
            }
        }
        return OSSClient.create(config, (optFns ?: emptyList()) + bsFn)
    }
}

/**
 * Builds full bucket space names for use with a plain [OSSClient].
 */
public class BucketSpaceHelper(
    private val accountId: String,
    private val region: String,
) {
    public constructor(config: ClientConfiguration) : this(config.accountId ?: "", config.region ?: "")

    /**
     * Builds the full bucket space name `{prefix}-{accountId}-{region}-bs-apsr` from a short prefix.
     */
    public fun toBucketName(prefix: String): String = "$prefix-$accountId-$region-bs-apsr"
}
