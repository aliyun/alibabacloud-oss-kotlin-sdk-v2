package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlListVersionsResult
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlVersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlVersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object BucketVersioning {

    internal suspend fun putBucketVersioning(impl: ClientImpl, request: PutBucketVersioningRequest, options: OperationOptions?): PutBucketVersioningResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        val versioningConfiguration =
            requireNotNull(request.versioningConfiguration) { "request.versioningConfiguration is required" }

        val input = OperationInput {
            opName = "PutBucketVersioning"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("versioning", "")
            }
            bucket = request.bucket
            // body
            body = toXmlVersioningConfiguration(versioningConfiguration)
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("versioning")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutBucketVersioningResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun getBucketVersioning(impl: ClientImpl, request: GetBucketVersioningRequest, options: OperationOptions?): GetBucketVersioningResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "GetBucketVersioning"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("versioning", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("versioning")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetBucketVersioningResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlVersioningConfiguration(body)
        }
    }

    internal suspend fun listObjectVersions(impl: ClientImpl, request: ListObjectVersionsRequest, options: OperationOptions?): ListObjectVersionsResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "ListObjectVersions"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("versions", "")
                put("encoding-type", "url")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("versions")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return ListObjectVersionsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlListVersionsResult(body)
        }
    }
}
