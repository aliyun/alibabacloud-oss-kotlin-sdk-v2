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


public suspend fun OSSClient.putBucketAccessMonitor(request: PutBucketAccessMonitorRequest, options: OperationOptions? = null): PutBucketAccessMonitorResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.accessMonitorConfiguration) {"request.accessMonitorConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketAccessMonitor"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("accessmonitor", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.accessMonitorConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("accessmonitor")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketAccessMonitorResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

public suspend fun OSSClient.getBucketAccessMonitor(request: GetBucketAccessMonitorRequest, options: OperationOptions? = null): GetBucketAccessMonitorResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketAccessMonitor"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("accessmonitor", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("accessmonitor")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketAccessMonitorResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<AccessMonitorConfiguration>(body)
    }
}

