package com.aliyun.kotlin.sdk.service.oss2.agentic.operations

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
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
import com.aliyun.kotlin.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic

/**
 * The basic AgenticBucket operations, delegating transport to the wrapped [OSSClient].
 */
internal object AgenticBucketBasic {

    internal suspend fun createAgenticBucket(
        client: OSSClient,
        request: CreateAgenticBucketRequest,
        options: OperationOptions?,
    ): CreateAgenticBucketResult {
        val input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toCreateAgenticBucket(output)
    }

    internal suspend fun deleteAgenticBucket(
        client: OSSClient,
        request: DeleteAgenticBucketRequest,
        options: OperationOptions?,
    ): DeleteAgenticBucketResult {
        val input = SerdeAgenticBucketBasic.fromDeleteAgenticBucket(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toDeleteAgenticBucket(output)
    }

    internal suspend fun getAgenticBucket(
        client: OSSClient,
        request: GetAgenticBucketRequest,
        options: OperationOptions?,
    ): GetAgenticBucketResult {
        val input = SerdeAgenticBucketBasic.fromGetAgenticBucket(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toGetAgenticBucket(output)
    }

    internal suspend fun listAgenticBuckets(
        client: OSSClient,
        request: ListAgenticBucketsRequest,
        options: OperationOptions?,
    ): ListAgenticBucketsResult {
        val input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toListAgenticBuckets(output)
    }

    internal suspend fun putAgenticBucketStatus(
        client: OSSClient,
        request: PutAgenticBucketStatusRequest,
        options: OperationOptions?,
    ): PutAgenticBucketStatusResult {
        val input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toPutAgenticBucketStatus(output)
    }

    internal suspend fun listBucketSpaces(
        client: OSSClient,
        request: ListBucketSpacesRequest,
        options: OperationOptions?,
    ): ListBucketSpacesResult {
        val input = SerdeAgenticBucketBasic.fromListBucketSpaces(request)
        val output = client.invokeOperation(input, options)
        return SerdeAgenticBucketBasic.toListBucketSpaces(output)
    }
}
