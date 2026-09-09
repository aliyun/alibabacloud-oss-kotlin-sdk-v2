package com.aliyun.kotlin.sdk.service.oss2.extension.api

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketOverwriteConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketOverwriteConfigResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketOverwriteConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketOverwriteConfigResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.OverwriteConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketOverwriteConfigRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketOverwriteConfigResult
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public suspend fun OSSClient.putBucketOverwriteConfig(
    request: PutBucketOverwriteConfigRequest,
    options: OperationOptions? = null
): PutBucketOverwriteConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    val corsConfiguration = requireNotNull(request.overwriteConfiguration) { "request.overwriteConfiguration is required" }

    val input = OperationInput {
        opName = "PutBucketOverwriteConfig"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("overwriteConfig", "")
        }
        bucket = request.bucket
        // body
        body = ByteStream.fromBytes(SerdeUtils.serializeXmlBody(corsConfiguration))
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("overwriteConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketOverwriteConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.getBucketOverwriteConfig(
    request: GetBucketOverwriteConfigRequest,
    options: OperationOptions? = null
): GetBucketOverwriteConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "GetBucketOverwriteConfig"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("overwriteConfig", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("overwriteConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketOverwriteConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<OverwriteConfiguration>(body)
    }
}

public suspend fun OSSClient.deleteBucketOverwriteConfig(
    request: DeleteBucketOverwriteConfigRequest,
    options: OperationOptions? = null
): DeleteBucketOverwriteConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "DeleteBucketOverwriteConfig"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("overwriteConfig", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("overwriteConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketOverwriteConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}
