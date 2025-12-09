package com.aliyun.kotlin.sdk.service.oss2.operations

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey.Companion.SUB_RESOURCE
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.internal.ClientImpl
import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlBucketInfo
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlBucketLocation
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlBucketStat
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlListBucketResult
import com.aliyun.kotlin.sdk.service.oss2.transform.fromXmlListBucketV2Result
import com.aliyun.kotlin.sdk.service.oss2.transform.toXmlCreateBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

internal object BucketBasic {

    internal suspend fun getBucketStat(impl: ClientImpl, request: GetBucketStatRequest, options: OperationOptions?): GetBucketStatResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "GetBucketStat"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("stat", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("stat")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetBucketStatResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlBucketStat(body)
        }
    }

    internal suspend fun putBucket(impl: ClientImpl, request: PutBucketRequest, options: OperationOptions?): PutBucketResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "PutBucket"
            method = "PUT"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
            // body
            body = toXmlCreateBucketConfiguration(request.createBucketConfiguration)
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return PutBucketResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun deleteBucket(impl: ClientImpl, request: DeleteBucketRequest, options: OperationOptions?): DeleteBucketResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "DeleteBucket"
            method = "DELETE"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            bucket = request.bucket
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)

        return DeleteBucketResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }
    }

    internal suspend fun listObjects(impl: ClientImpl, request: ListObjectsRequest, options: OperationOptions?): ListObjectsResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "ListObjects"
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
        }

        // opMetadata

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return ListObjectsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlListBucketResult(body)
        }
    }

    internal suspend fun listObjectsV2(impl: ClientImpl, request: ListObjectsV2Request, options: OperationOptions?): ListObjectsV2Result {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "ListObjectsV2"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("list-type", "2")
                put("encoding-type", "url")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("list-type")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return ListObjectsV2Result {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlListBucketV2Result(body)
        }
    }

    internal suspend fun getBucketInfo(impl: ClientImpl, request: GetBucketInfoRequest, options: OperationOptions?): GetBucketInfoResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "GetBucketInfo"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("bucketInfo", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("bucketInfo")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetBucketInfoResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlBucketInfo(body)
        }
    }

    internal suspend fun getBucketLocation(impl: ClientImpl, request: GetBucketLocationRequest, options: OperationOptions?): GetBucketLocationResult {
        requireNotNull(request.bucket) { "request.bucket is required" }

        val input = OperationInput {
            opName = "GetBucketLocation"
            method = "GET"
            // default headers
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            // parameters
            parameters = MapUtils.parametersMap().apply {
                put("location", "")
            }
            bucket = request.bucket
        }

        // opMetadata
        input.opMetadata[SUB_RESOURCE] = listOf("location")

        serializeInput(request, input) {
            addContentMd5(this)
        }

        val output = impl.execute(input, options)
        val body = output.body?.toByteArray()

        return GetBucketLocationResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = fromXmlBucketLocation(body)
        }
    }
}
