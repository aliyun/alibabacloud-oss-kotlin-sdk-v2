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


public suspend fun OSSClient.getBucketResourceGroup(request: GetBucketResourceGroupRequest, options: OperationOptions? = null): GetBucketResourceGroupResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketResourceGroup"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("resourceGroup", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("resourceGroup")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketResourceGroupResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<BucketResourceGroupConfiguration>(body)
    }
}

public suspend fun OSSClient.putBucketResourceGroup(request: PutBucketResourceGroupRequest, options: OperationOptions? = null): PutBucketResourceGroupResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.bucketResourceGroupConfiguration) {"request.bucketResourceGroupConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketResourceGroup"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("resourceGroup", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.bucketResourceGroupConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("resourceGroup")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketResourceGroupResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

