package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.hash.CRC64Observer
import com.aliyun.kotlin.sdk.service.oss2.internal.ChecksumUploadResponseHandler
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsResult
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsResult
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyResult
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartResult
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressObserver
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentType
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlCompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlCopyPartResult
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlInitiateMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlListMultipartUploadsResult
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlListPartResult
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlCompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import com.aliyun.kotlin.sdk.service.oss2.types.appendValue
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.urlEncodePath

internal object ObjectMultipart {

    internal suspend fun initiateMultipartUpload(impl: ClientImpl, request: InitiateMultipartUploadRequest, options: OperationOptions?): InitiateMultipartUploadResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "InitiateMultipartUpload"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap()
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("uploads", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("uploads")

        serializeInput(request, input) {
            addContentMd5(this)
            if (impl.featureFlags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE)) {
                addContentType(input)
            }
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return InitiateMultipartUploadResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlInitiateMultipartUpload(body)
        }
    }

    internal suspend fun uploadPart(impl: ClientImpl, request: UploadPartRequest, options: OperationOptions?): UploadPartResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.uploadId) { "request.uploadId is required" }
        requireNotNull(request.partNumber) { "request.partNumber is required" }

        val input = OperationInput {
            opName = "UploadPart"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
            key = request.key
            // body
            body = request.body
        }

        // opMetadata
        // progress
        request.progressListener?.let {
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(
                ProgressObserver(it, request.body?.contentLength)
            )
        }

        if (impl.featureFlags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)) {
            val observer = CRC64Observer()
            input.opMetadata.appendValue(OperationMetadataKey.UPLOAD_OBSERVER, observer)
            input.opMetadata.appendValue(
                OperationMetadataKey.RESPONSE_HANDLE,
                ChecksumUploadResponseHandler(observer.checksum)
            )
        }

        serializeInput(request, input)

        val output = impl.execute(input, options)

        return UploadPartResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun completeMultipartUpload(impl: ClientImpl, request: CompleteMultipartUploadRequest, options: OperationOptions?): CompleteMultipartUploadResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.uploadId) { "request.uploadId is required" }

        val input = OperationInput {
            opName = "CompleteMultipartUpload"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
            key = request.key
            // body
            body = request.completeMultipartUpload?.let { toXmlCompleteMultipartUpload(it) }
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return CompleteMultipartUploadResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlCompleteMultipartUpload(body)
        }
    }

    internal suspend fun uploadPartCopy(impl: ClientImpl, request: UploadPartCopyRequest, options: OperationOptions?): UploadPartCopyResult {
        val bucket = requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        val sourceBucket = request.sourceBucket ?: bucket
        val sourceKey = requireNotNull(request.sourceKey) { "request.sourceKey is required" }
        val copySource = "/$sourceBucket/${sourceKey.urlEncodePath()}"
        requireNotNull(request.uploadId) { "request.uploadId is required" }

        val input = OperationInput {
            opName = "UploadPartCopy"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
                put("x-oss-copy-source", copySource)
            }
            this.bucket = request.bucket
            key = request.key
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return UploadPartCopyResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlCopyPartResult(body)
        }
    }

    internal suspend fun abortMultipartUpload(impl: ClientImpl, request: AbortMultipartUploadRequest, options: OperationOptions?): AbortMultipartUploadResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.uploadId) { "request.uploadId is required" }

        val input = OperationInput {
            opName = "AbortMultipartUpload"
            method = "DELETE"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return AbortMultipartUploadResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun listMultipartUploads(impl: ClientImpl, request: ListMultipartUploadsRequest, options: OperationOptions?): ListMultipartUploadsResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "ListMultipartUploads"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("uploads", "")
                put("encoding-type", "url")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("uploads")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return ListMultipartUploadsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlListMultipartUploadsResult(body)
        }
    }

    internal suspend fun listParts(impl: ClientImpl, request: ListPartsRequest, options: OperationOptions?): ListPartsResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.uploadId) { "request.uploadId is required" }

        val input = OperationInput {
            opName = "ListParts"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("encoding-type", "url")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return ListPartsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlListPartResult(body)
        }
    }
}
