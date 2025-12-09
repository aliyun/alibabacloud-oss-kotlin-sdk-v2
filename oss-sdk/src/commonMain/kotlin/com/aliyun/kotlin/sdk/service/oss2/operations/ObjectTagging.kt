package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlTagging
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlTagging
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object ObjectTagging {

    internal suspend fun putObjectTagging(impl: ClientImpl, request: PutObjectTaggingRequest, options: OperationOptions?): PutObjectTaggingResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        val tagging = requireNotNull(request.tagging) { "request.tagging is required" }

        val input = OperationInput {
            opName = "PutObjectTagging"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("tagging", "")
            }
            bucket = request.bucket
            key = request.key
            // body
            body = toXmlTagging(tagging)
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("tagging")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutObjectTaggingResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun getObjectTagging(impl: ClientImpl, request: GetObjectTaggingRequest, options: OperationOptions?): GetObjectTaggingResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "GetObjectTagging"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("tagging", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("tagging")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetObjectTaggingResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlTagging(body)
        }
    }

    internal suspend fun deleteObjectTagging(impl: ClientImpl, request: DeleteObjectTaggingRequest, options: OperationOptions?): DeleteObjectTaggingResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "DeleteObjectTagging"
            method = "DELETE"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("tagging", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("tagging")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return DeleteObjectTaggingResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }
}
