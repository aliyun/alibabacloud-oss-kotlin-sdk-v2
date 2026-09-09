package com.aliyun.kotlin.sdk.service.oss2.extension.api

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.extension.models.AbortBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.AbortBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CompleteBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.CompleteBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ExtendBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ExtendBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InitiateBucketWormRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.InitiateBucketWormResult
import com.aliyun.kotlin.sdk.service.oss2.extension.models.WormConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.types.toByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public suspend fun OSSClient.initiateBucketWorm(request: InitiateBucketWormRequest, options: OperationOptions? = null): InitiateBucketWormResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    requireNotNull(request.initiateWormConfiguration) { "request.initiateWormConfiguration is required" }

    val input = OperationInput {
        opName = "InitiateBucketWorm"
        method = "POST"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("worm", "")
        }
        bucket = request.bucket
        // body
        body = SerdeUtils.serializeXmlBody(request.initiateWormConfiguration).toByteStream()
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("worm")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return InitiateBucketWormResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.abortBucketWorm(request: AbortBucketWormRequest, options: OperationOptions? = null): AbortBucketWormResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "AbortBucketWorm"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("worm", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("worm")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return AbortBucketWormResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.completeBucketWorm(request: CompleteBucketWormRequest, options: OperationOptions? = null): CompleteBucketWormResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    requireNotNull(request.wormId) { "request.wormId is required" }

    val input = OperationInput {
        opName = "CompleteBucketWorm"
        method = "POST"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        bucket = request.bucket
    }

    // opMetadata

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return CompleteBucketWormResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.extendBucketWorm(request: ExtendBucketWormRequest, options: OperationOptions? = null): ExtendBucketWormResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    requireNotNull(request.wormId) { "request.wormId is required" }
    requireNotNull(request.extendWormConfiguration) { "request.extendWormConfiguration is required" }

    val input = OperationInput {
        opName = "ExtendBucketWorm"
        method = "POST"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("wormExtend", "")
        }
        bucket = request.bucket
        // body
        body = SerdeUtils.serializeXmlBody(request.extendWormConfiguration).toByteStream()
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("wormExtend")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return ExtendBucketWormResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.getBucketWorm(request: GetBucketWormRequest, options: OperationOptions? = null): GetBucketWormResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "GetBucketWorm"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("worm", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("worm")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketWormResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<WormConfiguration>(body)
    }
}
