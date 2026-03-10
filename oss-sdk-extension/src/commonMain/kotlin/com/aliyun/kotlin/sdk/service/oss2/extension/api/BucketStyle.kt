package com.aliyun.kotlin.sdk.service.oss2.extension.api

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.extension.models.*
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.types.toByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public suspend fun OSSClient.putStyle(request: PutStyleRequest, options: OperationOptions? = null): PutStyleResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    val styleName = requireNotNull(request.styleName) { "request.styleName is required" }
    requireNotNull(request.style) { "request.style is required" }

    val input = OperationInput {
        opName = "PutStyle"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("style", "")
            put("styleName", styleName)
        }
        bucket = request.bucket
        // body
        body = SerdeUtils.serializeXmlBody(request.style).toByteStream()
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("style")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutStyleResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.listStyle(request: ListStyleRequest, options: OperationOptions? = null): ListStyleResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "ListStyle"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("style", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("style")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return ListStyleResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<StyleList>(body)
    }
}

public suspend fun OSSClient.getStyle(request: GetStyleRequest, options: OperationOptions? = null): GetStyleResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    val styleName = requireNotNull(request.styleName) { "request.styleName is required" }

    val input = OperationInput {
        opName = "GetStyle"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("style", "")
            put("styleName", styleName)
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("style")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetStyleResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<StyleInfo>(body)
    }
}

public suspend fun OSSClient.deleteStyle(request: DeleteStyleRequest, options: OperationOptions? = null): DeleteStyleResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    val styleName = requireNotNull(request.styleName) { "request.styleName is required" }

    val input = OperationInput {
        opName = "DeleteStyle"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("style", "")
            put("styleName", styleName)
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("style")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteStyleResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}
