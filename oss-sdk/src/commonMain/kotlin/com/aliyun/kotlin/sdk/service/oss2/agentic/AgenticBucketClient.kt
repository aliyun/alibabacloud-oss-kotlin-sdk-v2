package com.aliyun.kotlin.sdk.service.oss2.agentic

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.ClientOptions
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.OperationOutput
import com.aliyun.kotlin.sdk.service.oss2.agentic.internal.AgenticProvider
import com.aliyun.kotlin.sdk.service.oss2.agentic.internal.updateAgenticUserAgent
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.operations.AgenticBucketBasic

/**
 * A client for managing agentic buckets and listing their bucket spaces.
 *
 * The client composes an internally created [OSSClient] whose request pipeline resolves the
 * logical bucket name passed to each operation into its physical form
 * `{bucket}-{accountId}-{region}-ab-apsr`. The `accountId` and `region` are read from [config].
 */
public class AgenticBucketClient(
    config: ClientConfiguration,
    optFns: List<(ClientOptions) -> ClientOptions>? = null,
) : AutoCloseable {

    private val client: OSSClient

    init {
        updateAgenticUserAgent(config)
        val accountId = config.accountId ?: ""
        val region = config.region ?: ""
        val agenticFn: (ClientOptions) -> ClientOptions = { opts ->
            val provider = AgenticProvider(opts.endpoint, accountId, region, "ab-apsr", opts.addressStyle)
            opts.copy {
                endpointProvider = provider
                bucketNameResolver = provider
            }
        }
        client = OSSClient.create(config, (optFns ?: emptyList()) + agenticFn)
    }

    /**
     * Invokes a raw operation against the underlying client.
     */
    public suspend fun invokeOperation(input: OperationInput, options: OperationOptions? = null): OperationOutput {
        return client.invokeOperation(input, options)
    }

    /**
     * Creates an agentic bucket.
     */
    public suspend fun createAgenticBucket(
        request: CreateAgenticBucketRequest,
        options: OperationOptions? = null,
    ): CreateAgenticBucketResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        return AgenticBucketBasic.createAgenticBucket(client, request, options)
    }

    /**
     * Deletes an agentic bucket.
     */
    public suspend fun deleteAgenticBucket(
        request: DeleteAgenticBucketRequest,
        options: OperationOptions? = null,
    ): DeleteAgenticBucketResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        return AgenticBucketBasic.deleteAgenticBucket(client, request, options)
    }

    /**
     * Queries the information about an agentic bucket.
     */
    public suspend fun getAgenticBucket(
        request: GetAgenticBucketRequest,
        options: OperationOptions? = null,
    ): GetAgenticBucketResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        return AgenticBucketBasic.getAgenticBucket(client, request, options)
    }

    /**
     * Lists agentic buckets. This operation targets the regional host and takes no bucket.
     */
    public suspend fun listAgenticBuckets(
        request: ListAgenticBucketsRequest,
        options: OperationOptions? = null,
    ): ListAgenticBucketsResult {
        return AgenticBucketBasic.listAgenticBuckets(client, request, options)
    }

    /**
     * Sets the status of an agentic bucket.
     */
    public suspend fun putAgenticBucketStatus(
        request: PutAgenticBucketStatusRequest,
        options: OperationOptions? = null,
    ): PutAgenticBucketStatusResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.agenticBucketStatus?.status) { "request.agenticBucketStatus.status is required" }
        return AgenticBucketBasic.putAgenticBucketStatus(client, request, options)
    }

    /**
     * Lists the bucket spaces of an agentic bucket.
     */
    public suspend fun listBucketSpaces(
        request: ListBucketSpacesRequest,
        options: OperationOptions? = null,
    ): ListBucketSpacesResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        return AgenticBucketBasic.listBucketSpaces(client, request, options)
    }

    override fun close() {
        client.close()
    }
}
