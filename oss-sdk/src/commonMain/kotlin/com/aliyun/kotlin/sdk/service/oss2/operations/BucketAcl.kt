package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlAccessControlPolicy
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object BucketAcl {

    suspend fun putBucketAcl(impl: ClientImpl, request: PutBucketAclRequest, options: OperationOptions?): PutBucketAclResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "PutBucketAcl"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("acl", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("acl")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutBucketAclResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    suspend fun getBucketAcl(impl: ClientImpl, request: GetBucketAclRequest, options: OperationOptions?): GetBucketAclResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "GetBucketAcl"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("acl", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("acl")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetBucketAclResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlAccessControlPolicy(body)
        }
    }
}
