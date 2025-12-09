package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.hash.CRC64Observer
import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64
import com.aliyun.kotlin.sdk.service.oss2.internal.ChecksumUploadResponseHandler
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.CleanRestoredObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CleanRestoredObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsResult
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaResult
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectResult
import com.aliyun.kotlin.sdk.service.oss2.models.SealAppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.SealAppendObjectResult
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressObserver
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentType
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlCopyObject
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlDeleteMultipleObjects
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlDeleteMultipleObjects
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlRestoreRequest
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import com.aliyun.kotlin.sdk.service.oss2.types.appendValue
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.urlEncodePath

internal object ObjectBasic {

    internal suspend fun putObject(impl: ClientImpl, request: PutObjectRequest, options: OperationOptions?): PutObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "PutObject"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap()
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

        // crc observer
        if (impl.featureFlags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)) {
            val observer = CRC64Observer()
            input.opMetadata.appendValue(OperationMetadataKey.UPLOAD_OBSERVER, observer)
            input.opMetadata.appendValue(
                OperationMetadataKey.RESPONSE_HANDLE,
                ChecksumUploadResponseHandler(observer.checksum)
            )
        }

        serializeInput(request, input) {
            if (impl.featureFlags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE)) {
                addContentType(input)
            }
        }

        val output = impl.execute(input, options)

        return PutObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = output.body
        }
    }

    internal suspend fun copyObject(impl: ClientImpl, request: CopyObjectRequest, options: OperationOptions?): CopyObjectResult {
        val bucket = requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        val sourceBucket = request.sourceBucket ?: bucket
        val sourceKey = requireNotNull(request.sourceKey) { "request.sourceKey is required" }
        val copySource = "/$sourceBucket/${sourceKey.urlEncodePath()}"

        val input = OperationInput {
            opName = "CopyObject"
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

        return CopyObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlCopyObject(body)
        }
    }

    internal suspend fun getObject(impl: ClientImpl, request: GetObjectRequest, returnAsStream: Boolean, options: OperationOptions?): GetObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "GetObject"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        if (returnAsStream) {
            input.opMetadata[OperationMetadataKey.RESPONSE_HEADERS_READ] = true
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return GetObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = output.body
        }
    }

    internal suspend fun appendObject(impl: ClientImpl, request: AppendObjectRequest, options: OperationOptions?): AppendObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "AppendObject"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap()
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("append", "")
            }
            bucket = request.bucket
            key = request.key
            // body
            body = request.body
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("append")

        // progress
        request.progressListener?.let {
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(
                ProgressObserver(it, request.body?.contentLength)
            )
        }

        // crc observer
        val crc64: Crc64? = request.initHashCRC64?.let {
            if (impl.featureFlags.contains(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD)) {
                val observer = CRC64Observer(it)
                input.opMetadata.appendValue(OperationMetadataKey.UPLOAD_OBSERVER, observer)
                observer.checksum
            } else {
                null
            }
        }

        serializeInput(request, input) {
            if (impl.featureFlags.contains(FeatureFlagsType.AUTO_DETECT_MIMETYPE)) {
                addContentType(input)
            }
        }

        val output = impl.execute(input, options)
        crc64?.let { crc ->
            output.headers["x-oss-hash-crc64ecma"]?.let {
                val serverCrc = it
                val clientCrc = crc.digestValue.toULong().toString()
                if (serverCrc != clientCrc) {
                    throw InconsistentException(clientCrc, serverCrc, output.headers)
                }
            }
        }

        return AppendObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun sealAppendObject(impl: ClientImpl, request: SealAppendObjectRequest, options: OperationOptions?): SealAppendObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        requireNotNull(request.position) { "request.position is required" }

        val input = OperationInput {
            opName = "SealAppendObject"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap()
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("seal", "")
            }
            bucket = request.bucket
            key = request.key
            // body
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("seal")

        serializeInput(request, input)

        val output = impl.execute(input, options)
        return SealAppendObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun deleteObject(impl: ClientImpl, request: DeleteObjectRequest, options: OperationOptions?): DeleteObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "DeleteObject"
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

        return DeleteObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun headObject(impl: ClientImpl, request: HeadObjectRequest, options: OperationOptions?): HeadObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "HeadObject"
            method = "HEAD"
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

        return HeadObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun getObjectMeta(impl: ClientImpl, request: GetObjectMetaRequest, options: OperationOptions?): GetObjectMetaResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "GetObjectMeta"
            method = "HEAD"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("objectMeta", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("objectMeta")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return GetObjectMetaResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun deleteMultipleObjects(impl: ClientImpl, request: DeleteMultipleObjectsRequest, options: OperationOptions?): DeleteMultipleObjectsResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        val delete = requireNotNull(request.delete) { "request.delete is required" }

        val input = OperationInput {
            opName = "DeleteMultipleObjects"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("delete", "")
            }
            bucket = request.bucket
            // body
            body = toXmlDeleteMultipleObjects(delete)
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("restore")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return DeleteMultipleObjectsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlDeleteMultipleObjects(body)
        }
    }

    internal suspend fun restoreObject(impl: ClientImpl, request: RestoreObjectRequest, options: OperationOptions?): RestoreObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }
        val restoreRequest = requireNotNull(request.restoreRequest) { "request.restoreRequest is required" }

        val input = OperationInput {
            opName = "RestoreObject"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("restore", "")
            }
            bucket = request.bucket
            key = request.key
            // body
            body = toXmlRestoreRequest(restoreRequest)
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("restore")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return RestoreObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun cleanRestoredObject(impl: ClientImpl, request: CleanRestoredObjectRequest, options: OperationOptions?): CleanRestoredObjectResult {
        requireNotNull(request.bucket) { "request.bucket is required" }
        requireNotNull(request.key) { "request.key is required" }

        val input = OperationInput {
            opName = "CleanRestoredObject"
            method = "POST"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("cleanRestoredObject", "")
            }
            bucket = request.bucket
            key = request.key
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("cleanRestoredObject")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return CleanRestoredObjectResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }
}
