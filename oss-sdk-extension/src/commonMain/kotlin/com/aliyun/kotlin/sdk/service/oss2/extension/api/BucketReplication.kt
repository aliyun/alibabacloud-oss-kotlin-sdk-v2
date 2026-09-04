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


public suspend fun OSSClient.putBucketRtc(request: PutBucketRtcRequest, options: OperationOptions? = null): PutBucketRtcResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.rtcConfiguration) {"request.rtcConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketRtc"
        method = "PUT"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("rtc", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.rtcConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("rtc")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketRtcResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

public suspend fun OSSClient.putBucketReplication(request: PutBucketReplicationRequest, options: OperationOptions? = null): PutBucketReplicationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.replicationConfiguration) {"request.replicationConfiguration is required"}

    val input = OperationInput {
        opName = "PutBucketReplication"
        method = "POST"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("comp", "add")
            put("replication", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.replicationConfiguration).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("replication", "comp")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return PutBucketReplicationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode  
    }
}

public suspend fun OSSClient.getBucketReplication(request: GetBucketReplicationRequest, options: OperationOptions? = null): GetBucketReplicationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketReplication"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("replication", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("replication")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketReplicationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<ReplicationConfiguration>(body)
    }
}

public suspend fun OSSClient.getBucketReplicationLocation(request: GetBucketReplicationLocationRequest, options: OperationOptions? = null): GetBucketReplicationLocationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketReplicationLocation"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("replicationLocation", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("replicationLocation")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketReplicationLocationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<ReplicationLocation>(body)
    }
}

public suspend fun OSSClient.getBucketReplicationProgress(request: GetBucketReplicationProgressRequest, options: OperationOptions? = null): GetBucketReplicationProgressResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}

    val input = OperationInput {
        opName = "GetBucketReplicationProgress"
        method = "GET"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("replicationProgress", "")
        } 
        bucket = request.bucket 
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("replicationProgress")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)
    val body = output.body?.toByteArray()

    return GetBucketReplicationProgressResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
        innerBody = SerdeUtils.deserializeXmlBody<ReplicationProgress>(body)
    }
}

public suspend fun OSSClient.deleteBucketReplication(request: DeleteBucketReplicationRequest, options: OperationOptions? = null): DeleteBucketReplicationResult {
    
    requireNotNull(request.bucket) {"request.bucket is required"}
    requireNotNull(request.replicationRules) {"request.replicationRules is required"}

    val input = OperationInput {
        opName = "DeleteBucketReplication"
        method = "POST"
        // default headers
        headers = MapUtils.headersMap().apply {
            put("Content-Type", "application/xml")
        }  
        // parameters
        parameters = MapUtils.parametersMap().apply { 
            put("comp", "delete")
            put("replication", "")
        } 
        bucket = request.bucket 
        // body
        body = SerdeUtils.serializeXmlBody(request.replicationRules).toByteStream()
    }

    // opMetadata 
    input.opMetadata[SUB_RESOURCE] = listOf("comp", "replication")
    
    
    serializeInput(request, input) { 
        addContentMd5(this)
    }

    val output = this.invokeOperation(input, options)

    return DeleteBucketReplicationResult {
        headers = output.headers
        status = output.status
        statusCode = output.statusCode 
    }
}

