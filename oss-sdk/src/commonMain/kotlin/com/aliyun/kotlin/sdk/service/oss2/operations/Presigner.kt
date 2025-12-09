@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.PresignOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PresignResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import kotlin.time.ExperimentalTime

internal object Presigner {
    private suspend fun doPresign(impl: ClientImpl, input: OperationInput, options: PresignOptions?): PresignResult {
        options?.expirationInEpoch?.let {
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = it
        }

        val result = impl.presignInner(input, OperationOptions.Default)

        return PresignResult {
            url = result.url
            method = result.method
            expirationInEpoch = result.expiration?.epochSeconds
            signedHeaders = result.signedHeaders
        }
    }

    suspend fun getObject(impl: ClientImpl, request: GetObjectRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "GetObject"
            method = "GET"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun putObject(impl: ClientImpl, request: PutObjectRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun headObject(impl: ClientImpl, request: HeadObjectRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "HeadObject"
            method = "HEAD"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun initiateMultipartUpload(impl: ClientImpl, request: InitiateMultipartUploadRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "InitiateMultipartUpload"
            method = "POST"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
            parameters = MapUtils.parametersMap().apply {
                put("uploads", "")
            }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun uploadPart(impl: ClientImpl, request: UploadPartRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "UploadPart"
            method = "PUT"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun completeMultipartUpload(impl: ClientImpl, request: CompleteMultipartUploadRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "CompleteMultipartUpload"
            method = "POST"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }

    suspend fun abortMultipartUploadRequest(impl: ClientImpl, request: AbortMultipartUploadRequest, options: PresignOptions?): PresignResult {
        val input = OperationInput {
            opName = "AbortMultipartUpload"
            method = "DELETE"
            bucket = requireNotNull(request.bucket) { "request.bucket is required" }
            key = requireNotNull(request.key) { "request.key is required" }
        }
        serializeInput(request, input)
        return doPresign(impl, input, options)
    }
}
