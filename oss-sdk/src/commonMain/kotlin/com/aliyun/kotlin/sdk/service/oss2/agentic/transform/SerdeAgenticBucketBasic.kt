package com.aliyun.kotlin.sdk.service.oss2.agentic.transform

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationOutput
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketInfo
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketSummary
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.BucketSpaceSummary
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusResult
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal.ListAgenticBucketsResultXml
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.internal.ListBucketSpacesResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.Owner
import com.aliyun.kotlin.sdk.service.oss2.models.ServerSideEncryptionRule
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.addContentMd5
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.serializeInput
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

/**
 * The serialization/deserialization for the basic AgenticBucket operations.
 */
internal object SerdeAgenticBucketBasic {

    // region CreateAgenticBucket

    fun fromCreateAgenticBucket(request: CreateAgenticBucketRequest): OperationInput {
        val input = OperationInput {
            opName = "CreateAgenticBucket"
            method = "PUT"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
            }
            bucket = request.bucket
            body = toXmlCreateAgenticBucketConfiguration(request.createAgenticBucketConfiguration)
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    fun toCreateAgenticBucket(output: OperationOutput): CreateAgenticBucketResult =
        CreateAgenticBucketResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }

    // endregion

    // region DeleteAgenticBucket

    fun fromDeleteAgenticBucket(request: DeleteAgenticBucketRequest): OperationInput {
        val input = OperationInput {
            opName = "DeleteAgenticBucket"
            method = "DELETE"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
            }
            bucket = request.bucket
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    fun toDeleteAgenticBucket(output: OperationOutput): DeleteAgenticBucketResult =
        DeleteAgenticBucketResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }

    // endregion

    // region GetAgenticBucket

    fun fromGetAgenticBucket(request: GetAgenticBucketRequest): OperationInput {
        val input = OperationInput {
            opName = "GetAgenticBucket"
            method = "GET"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
            }
            bucket = request.bucket
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    suspend fun toGetAgenticBucket(output: OperationOutput): GetAgenticBucketResult {
        val root = deserializeXmlBody(output.body?.toByteArray(), "AgenticBucketInfo")
        return GetAgenticBucketResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = parseAgenticBucketInfo(root)
        }
    }

    // endregion

    // region ListAgenticBuckets

    fun fromListAgenticBuckets(request: ListAgenticBucketsRequest): OperationInput {
        val input = OperationInput {
            opName = "ListAgenticBuckets"
            method = "GET"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
            }
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    suspend fun toListAgenticBuckets(output: OperationOutput): ListAgenticBucketsResult {
        val root = deserializeXmlBody(output.body?.toByteArray(), "ListAgenticBucketsResult")
        val xml = ListAgenticBucketsResultXml()
        root.children.forEach { (key, value) ->
            when (key) {
                "Region" -> xml.region = value.first().text
                "Owner" -> xml.owner = value.first().text
                "ContinuationToken" -> xml.continuationToken = value.first().text
                "NextContinuationToken" -> xml.nextContinuationToken = value.first().text
                "IsTruncated" -> xml.isTruncated = value.first().text?.toBoolean()
                "AgenticBuckets" -> {
                    val buckets = mutableListOf<AgenticBucketSummary>()
                    value.first().children["AgenticBucket"]?.forEach { node ->
                        buckets.add(parseAgenticBucketSummary(node))
                    }
                    xml.agenticBuckets = buckets
                }
            }
        }
        return ListAgenticBucketsResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = xml
        }
    }

    // endregion

    // region PutAgenticBucketStatus

    fun fromPutAgenticBucketStatus(request: PutAgenticBucketStatusRequest): OperationInput {
        val input = OperationInput {
            opName = "PutAgenticBucketStatus"
            method = "PUT"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
                put("status", "")
            }
            bucket = request.bucket
            body = toXmlAgenticBucketStatus(request.agenticBucketStatus?.status)
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    fun toPutAgenticBucketStatus(output: OperationOutput): PutAgenticBucketStatusResult =
        PutAgenticBucketStatusResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
        }

    // endregion

    // region ListBucketSpaces

    fun fromListBucketSpaces(request: ListBucketSpacesRequest): OperationInput {
        val input = OperationInput {
            opName = "ListBucketSpaces"
            method = "GET"
            headers = MapUtils.headersMap().apply {
                put("Content-Type", "application/xml")
            }
            parameters = MapUtils.parametersMap().apply {
                put("agenticBucket", "")
                put("bucketSpace", "")
            }
            bucket = request.bucket
        }

        serializeInput(request, input) {
            addContentMd5(this)
        }
        return input
    }

    suspend fun toListBucketSpaces(output: OperationOutput): ListBucketSpacesResult {
        val root = deserializeXmlBody(output.body?.toByteArray(), "ListBucketSpacesResult")
        val xml = ListBucketSpacesResultXml()
        root.children.forEach { (key, value) ->
            when (key) {
                "Owner" -> xml.owner = parseOwner(value.first())
                "Prefix" -> xml.prefix = value.first().text
                "MaxKeys" -> xml.maxKeys = value.first().text?.toIntOrNull()
                "ContinuationToken" -> xml.continuationToken = value.first().text
                "NextContinuationToken" -> xml.nextContinuationToken = value.first().text
                "StartAfter" -> xml.startAfter = value.first().text
                "IsTruncated" -> xml.isTruncated = value.first().text?.toBoolean()
                "BucketSpaces" -> {
                    val spaces = mutableListOf<BucketSpaceSummary>()
                    value.first().children["BucketSpace"]?.forEach { node ->
                        spaces.add(parseBucketSpaceSummary(node))
                    }
                    xml.bucketSpaces = spaces
                }
            }
        }
        return ListBucketSpacesResult {
            headers = output.headers
            status = output.status
            statusCode = output.statusCode
            innerBody = xml
        }
    }

    // endregion

    // region serializers

    private fun toXmlCreateAgenticBucketConfiguration(value: CreateAgenticBucketConfiguration?): ByteStream? {
        value ?: return null
        val root = XmlNode("CreateAgenticBucketConfiguration")
        value.storageClass?.let { root.addChild(XmlNode("StorageClass").apply { text = it }) }
        value.dataRedundancyType?.let { root.addChild(XmlNode("DataRedundancyType").apply { text = it }) }
        return ByteStream.fromString(root.toXmlString())
    }

    private fun toXmlAgenticBucketStatus(status: String?): ByteStream? {
        status ?: return null
        val root = XmlNode("AgenticBucketStatus")
        root.addChild(XmlNode("Status").apply { text = status })
        return ByteStream.fromString(root.toXmlString())
    }

    // endregion

    // region deserializers

    private fun parseAgenticBucketInfo(root: XmlNode): AgenticBucketInfo {
        val builder = AgenticBucketInfo.Builder()
        root.children.forEach { (key, value) ->
            when (key) {
                "Name" -> builder.name = value.first().text
                "Owner" -> builder.owner = value.first().text
                "Region" -> builder.region = value.first().text
                "StorageClass" -> builder.storageClass = value.first().text
                "DataRedundancyType" -> builder.dataRedundancyType = value.first().text
                "Status" -> builder.status = value.first().text
                "BucketResourceType" -> builder.bucketResourceType = value.first().text
                "CreateTime" -> builder.createTime = value.first().text
                "ACL" -> builder.acl = value.first().text
                "PublicAccessBlock" -> builder.publicAccessBlock = value.first().text
                "ServerSideEncryptionRule" -> builder.serverSideEncryptionRule = parseServerSideEncryptionRule(value.first())
                "Versioning" -> builder.versioning = value.first().text
                "BucketPolicy" -> builder.bucketPolicy = value.first().text
            }
        }
        return builder.build()
    }

    private fun parseServerSideEncryptionRule(node: XmlNode): ServerSideEncryptionRule {
        val base = node.children["ApplyServerSideEncryptionByDefault"]?.firstOrNull() ?: node
        val builder = ServerSideEncryptionRule.Builder()
        base.children.forEach { (key, value) ->
            when (key) {
                "SSEAlgorithm" -> builder.sSEAlgorithm = value.first().text
                "KMSDataEncryption" -> builder.kMSDataEncryption = value.first().text
                "KMSMasterKeyID" -> builder.kMSMasterKeyID = value.first().text
            }
        }
        return builder.build()
    }

    private fun parseAgenticBucketSummary(node: XmlNode): AgenticBucketSummary {
        val builder = AgenticBucketSummary.Builder()
        node.children.forEach { (key, value) ->
            when (key) {
                "Name" -> builder.name = value.first().text
                "StorageClass" -> builder.storageClass = value.first().text
                "DataRedundancyType" -> builder.dataRedundancyType = value.first().text
                "CreateTime" -> builder.createTime = value.first().text
            }
        }
        return builder.build()
    }

    private fun parseBucketSpaceSummary(node: XmlNode): BucketSpaceSummary {
        val builder = BucketSpaceSummary.Builder()
        node.children.forEach { (key, value) ->
            when (key) {
                "Name" -> builder.name = value.first().text
                "Location" -> builder.location = value.first().text
                "CreationDate" -> builder.creationDate = value.first().text
                "StorageClass" -> builder.storageClass = value.first().text
            }
        }
        return builder.build()
    }

    private fun parseOwner(node: XmlNode): Owner {
        val builder = Owner.Builder()
        node.children.forEach { (key, value) ->
            when (key) {
                "ID" -> builder.id = value.first().text
                "DisplayName" -> builder.displayName = value.first().text
            }
        }
        return builder.build()
    }

    // endregion
}
