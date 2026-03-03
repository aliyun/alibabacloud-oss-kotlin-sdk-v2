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

public suspend fun OSSClient.putBucketLogging(request: PutBucketLoggingRequest, options: OperationOptions? = null): PutBucketLoggingResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    requireNotNull(request.bucketLoggingStatus) { "request.bucketLoggingStatus is required" }

    val input = OperationInput {
        opName = "PutBucketLogging"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("logging", "")
        }
        bucket = request.bucket
        // body
        body = SerdeUtils.serializeXmlBody(request.bucketLoggingStatus).toByteStream()
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("logging")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketLoggingResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.getBucketLogging(request: GetBucketLoggingRequest, options: OperationOptions? = null): GetBucketLoggingResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "GetBucketLogging"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("logging", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("logging")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketLoggingResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<BucketLoggingStatus>(body)
    }
}

public suspend fun OSSClient.deleteBucketLogging(request: DeleteBucketLoggingRequest, options: OperationOptions? = null): DeleteBucketLoggingResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "DeleteBucketLogging"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("logging", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("logging")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketLoggingResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.putUserDefinedLogFieldsConfig(request: PutUserDefinedLogFieldsConfigRequest, options: OperationOptions? = null): PutUserDefinedLogFieldsConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }
    requireNotNull(request.userDefinedLogFieldsConfiguration) { "request.userDefinedLogFieldsConfiguration is required" }

    val input = OperationInput {
        opName = "PutUserDefinedLogFieldsConfig"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("userDefinedLogFieldsConfig", "")
        }
        bucket = request.bucket
        // body
        body = SerdeUtils.serializeXmlBody(request.userDefinedLogFieldsConfiguration).toByteStream()
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("userDefinedLogFieldsConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutUserDefinedLogFieldsConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}

public suspend fun OSSClient.getUserDefinedLogFieldsConfig(request: GetUserDefinedLogFieldsConfigRequest, options: OperationOptions? = null): GetUserDefinedLogFieldsConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "GetUserDefinedLogFieldsConfig"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("userDefinedLogFieldsConfig", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("userDefinedLogFieldsConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetUserDefinedLogFieldsConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
        innerBody = SerdeUtils.deserializeXmlBody<UserDefinedLogFieldsConfiguration>(body)
    }
}

public suspend fun OSSClient.deleteUserDefinedLogFieldsConfig(request: DeleteUserDefinedLogFieldsConfigRequest, options: OperationOptions? = null): DeleteUserDefinedLogFieldsConfigResult {
    requireNotNull(request.bucket) { "request.bucket is required" }

    val input = OperationInput {
        opName = "DeleteUserDefinedLogFieldsConfig"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }
        // parameters
        parameters = MapUtils.parametersMap().apply {
            put("userDefinedLogFieldsConfig", "")
        }
        bucket = request.bucket
    }

    // opMetadata
    input.opMetadata[SUB_RESOURCE] = listOf("userDefinedLogFieldsConfig")

    serializeInput(request, input) {
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteUserDefinedLogFieldsConfigResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode
    }
}
