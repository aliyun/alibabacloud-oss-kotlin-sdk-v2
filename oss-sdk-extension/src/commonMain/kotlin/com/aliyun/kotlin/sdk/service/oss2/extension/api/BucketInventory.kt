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


public suspend fun OSSClient.putBucketInventory(request: PutBucketInventoryRequest, options: OperationOptions? = null): PutBucketInventoryResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.inventoryId) {"request.inventoryId is required"}
    requireNotNull(request.inventoryConfiguration) {"request.inventoryConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketInventory"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("inventory", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.inventoryConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("inventory")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketInventoryResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

public suspend fun OSSClient.getBucketInventory(request: GetBucketInventoryRequest, options: OperationOptions? = null): GetBucketInventoryResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.inventoryId) {"request.inventoryId is required"}

    val input = OperationInput {
        opName = "GetBucketInventory"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("inventory", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("inventory")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketInventoryResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<InventoryConfiguration>(body)
    }
}

public suspend fun OSSClient.listBucketInventory(request: ListBucketInventoryRequest, options: OperationOptions? = null): ListBucketInventoryResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "ListBucketInventory"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("inventory", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("inventory")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return ListBucketInventoryResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<ListInventoryConfigurationsResult>(body)
    }
}

public suspend fun OSSClient.deleteBucketInventory(request: DeleteBucketInventoryRequest, options: OperationOptions? = null): DeleteBucketInventoryResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.inventoryId) {"request.inventoryId is required"}

    val input = OperationInput {
        opName = "DeleteBucketInventory"
        method = "DELETE"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("inventory", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("inventory")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketInventoryResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

