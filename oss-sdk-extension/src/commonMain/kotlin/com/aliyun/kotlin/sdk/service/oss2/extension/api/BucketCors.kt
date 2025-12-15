package com.aliyun.kotlin.sdk.service.oss2.extension.api

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CORSConfiguration
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteBucketCorsResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutBucketCorsResult
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public suspend fun OSSClient.putBucketCors(
    request: PutBucketCorsRequest,
    options: OperationOptions? = null
): PutBucketCorsResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    val corsConfiguration = requireNotNull(request.corsConfiguration) { "request.corsConfiguration is required" }

    val input = OperationInput {
        opName = "PutBucketCors"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("cors", "")
        }
        bucket = request.bucket
        // body
        body = ByteStream.fromBytes(SerdeUtils.serializeXmlBody(corsConfiguration))
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("cors")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketCorsResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.getBucketCors(
    request: GetBucketCorsRequest,
    options: OperationOptions? = null
): GetBucketCorsResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "GetBucketCors"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("cors", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("cors")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketCorsResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<CORSConfiguration>(body)
    }
}

public suspend fun OSSClient.deleteBucketCors(
    request: DeleteBucketCorsRequest,
    options: OperationOptions? = null
): DeleteBucketCorsResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "DeleteBucketCors"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("cors", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("cors")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketCorsResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}
