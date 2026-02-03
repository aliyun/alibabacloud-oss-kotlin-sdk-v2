package com.aliyun.kotlin.sdk.service.oss2.extension.api


import com.aliyun.kotlin.sdk.service.oss2.extension.models.*
import com.aliyun.kotlin.sdk.service.oss2.OSSClient

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.extension.api.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.types.toByteStream


public suspend fun OSSClient.putBucketTransferAcceleration(request: PutBucketTransferAccelerationRequest, options: OperationOptions? = null): PutBucketTransferAccelerationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.transferAccelerationConfiguration) {"request.transferAccelerationConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketTransferAcceleration"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("transferAcceleration", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.transferAccelerationConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("transferAcceleration")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketTransferAccelerationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

public suspend fun OSSClient.getBucketTransferAcceleration(request: GetBucketTransferAccelerationRequest, options: OperationOptions? = null): GetBucketTransferAccelerationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketTransferAcceleration"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("transferAcceleration", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("transferAcceleration")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketTransferAccelerationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<TransferAccelerationConfiguration>(body)
    }
}

