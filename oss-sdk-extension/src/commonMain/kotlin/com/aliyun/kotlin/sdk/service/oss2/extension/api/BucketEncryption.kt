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


public suspend fun OSSClient.putBucketEncryption(request: PutBucketEncryptionRequest, options: OperationOptions? = null): PutBucketEncryptionResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.serverSideEncryptionRule) {"request.serverSideEncryptionRule is required"}

    val input = OperationInput {
        opName = "PutBucketEncryption"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("encryption", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.serverSideEncryptionRule).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("encryption")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketEncryptionResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

public suspend fun OSSClient.getBucketEncryption(request: GetBucketEncryptionRequest, options: OperationOptions? = null): GetBucketEncryptionResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketEncryption"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("encryption", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("encryption")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketEncryptionResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<ServerSideEncryptionRule>(body)
    }
}

public suspend fun OSSClient.deleteBucketEncryption(request: DeleteBucketEncryptionRequest, options: OperationOptions? = null): DeleteBucketEncryptionResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "DeleteBucketEncryption"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("encryption", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("encryption")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketEncryptionResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

