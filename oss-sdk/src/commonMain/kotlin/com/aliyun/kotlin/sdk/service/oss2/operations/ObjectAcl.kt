package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclResult
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlObjectAccessControlPolicy
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object ObjectAcl {

    internal suspend fun putObjectAcl(impl: ClientImpl, request: PutObjectAclRequest, options: OperationOptions?): PutObjectAclResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.objectAcl) { "request.objectAcl is required" }

        val input = OperationInput {
            opName = "PutObjectAcl"
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
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("acl")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutObjectAclResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun getObjectAcl(impl: ClientImpl, request: GetObjectAclRequest, options: OperationOptions?): GetObjectAclResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "GetObjectAcl"
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
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("acl")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetObjectAclResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlObjectAccessControlPolicy(body)
        }
    }
}
