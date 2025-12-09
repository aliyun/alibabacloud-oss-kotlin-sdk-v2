package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object ObjectSymlink {

    internal suspend fun putSymlink(impl: ClientImpl, request: PutSymlinkRequest, options: OperationOptions?): PutSymlinkResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.symlinkTarget) { "request.symlinkTarget is required" }

        val input = OperationInput {
            opName = "PutSymlink"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("symlink", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("symlink")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutSymlinkResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun getSymlink(impl: ClientImpl, request: GetSymlinkRequest, options: OperationOptions?): GetSymlinkResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "GetSymlink"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("symlink", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("symlink")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return GetSymlinkResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }
}
