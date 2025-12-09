package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.AccessControlList
import com.aliyun.kotlin.sdk.service.oss2.models.Bucket
import com.aliyun.kotlin.sdk.service.oss2.models.BucketInfo
import com.aliyun.kotlin.sdk.service.oss2.models.BucketPolicy
import com.aliyun.kotlin.sdk.service.oss2.models.BucketStat
import com.aliyun.kotlin.sdk.service.oss2.models.CommonPrefix
import com.aliyun.kotlin.sdk.service.oss2.models.CreateBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectSummary
import com.aliyun.kotlin.sdk.service.oss2.models.Owner
import com.aliyun.kotlin.sdk.service.oss2.models.ServerSideEncryptionRule
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketResult
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketV2Result
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.urlDecode

internal fun fromXmlBucketStat(data: ByteArray?): BucketStat {
    val root = deserializeXmlBody(data, "BucketStat")
    val result = BucketStat.Builder()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Storage" -> {
                result.storage = it0.value.first().text?.toLong()
            }

            "ObjectCount" -> {
                result.objectCount = it0.value.first().text?.toLong()
            }

            "MultipartUploadCount" -> {
                result.multipartUploadCount = it0.value.first().text?.toLong()
            }

            "MultipartPartCount" -> {
                result.multipartPartCount = it0.value.first().text?.toLong()
            }

            "LiveChannelCount" -> {
                result.liveChannelCount = it0.value.first().text?.toLong()
            }

            "LastModifiedTime" -> {
                result.lastModifiedTime = it0.value.first().text?.toLong()
            }

            "StandardStorage" -> {
                result.standardStorage = it0.value.first().text?.toLong()
            }

            "StandardObjectCount" -> {
                result.standardObjectCount = it0.value.first().text?.toLong()
            }

            "InfrequentAccessStorage" -> {
                result.infrequentAccessStorage = it0.value.first().text?.toLong()
            }

            "InfrequentAccessRealStorage" -> {
                result.infrequentAccessRealStorage = it0.value.first().text?.toLong()
            }

            "InfrequentAccessObjectCount" -> {
                result.infrequentAccessObjectCount = it0.value.first().text?.toLong()
            }

            "ArchiveStorage" -> {
                result.archiveStorage = it0.value.first().text?.toLong()
            }

            "ArchiveRealStorage" -> {
                result.archiveRealStorage = it0.value.first().text?.toLong()
            }

            "ArchiveObjectCount" -> {
                result.archiveObjectCount = it0.value.first().text?.toLong()
            }

            "ColdArchiveStorage" -> {
                result.coldArchiveStorage = it0.value.first().text?.toLong()
            }

            "ColdArchiveRealStorage" -> {
                result.coldArchiveRealStorage = it0.value.first().text?.toLong()
            }

            "ColdArchiveObjectCount" -> {
                result.coldArchiveObjectCount = it0.value.first().text?.toLong()
            }

            "DeleteMarkerCount" -> {
                result.deleteMarkerCount = it0.value.first().text?.toLong()
            }

            "DeepColdArchiveStorage" -> {
                result.deepColdArchiveStorage = it0.value.first().text?.toLong()
            }

            "DeepColdArchiveObjectCount" -> {
                result.deepColdArchiveObjectCount = it0.value.first().text?.toLong()
            }

            "DeepColdArchiveRealStorage" -> {
                result.deepColdArchiveRealStorage = it0.value.first().text?.toLong()
            }
        }
    }
    return result.build()
}

internal fun toXmlCreateBucketConfiguration(value: CreateBucketConfiguration?): ByteStream {
    val root = XmlNode("CreateBucketConfiguration")
    value?.storageClass?.let {
        root.addChild(XmlNode("StorageClass").apply { text = it })
    }
    value?.dataRedundancyType?.let {
        root.addChild(XmlNode("DataRedundancyType").apply { text = it })
    }
    return ByteStream.fromString(root.toXmlString())
}

internal fun fromXmlListBucketResult(data: ByteArray?): ListBucketResult {
    val root = deserializeXmlBody(data, "ListBucketResult")
    val result = ListBucketResult()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Name" -> {
                result.name = it0.value.first().text
            }

            "Prefix" -> {
                result.prefix = it0.value.first().text
            }

            "Marker" -> {
                result.marker = it0.value.first().text
            }

            "NextMarker" -> {
                result.nextMarker = it0.value.first().text
            }

            "MaxKeys" -> {
                result.maxKeys = it0.value.first().text?.toInt()
            }

            "Delimiter" -> {
                result.delimiter = it0.value.first().text
            }

            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text.toBoolean()
            }

            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }

            "KeyCount" -> {
                result.keyCount = it0.value.first().text?.toInt()
            }

            "Contents" -> {
                val contents = mutableListOf<ObjectSummary>()
                for (content in it0.value) {
                    val obj = ObjectSummary.Builder()
                    content.children.forEach { it1 ->
                        when (it1.key) {
                            "Key" -> {
                                obj.key = it1.value.first().text
                            }

                            "TransitionTime" -> {
                                obj.transitionTime =
                                    it1.value.first().text
                            }

                            "LastModified" -> {
                                obj.lastModified = it1.value.first().text
                            }

                            "ETag" -> {
                                obj.eTag = it1.value.first().text
                            }

                            "Type" -> {
                                obj.type = it1.value.first().text
                            }

                            "Size" -> {
                                obj.size = it1.value.first().text?.toLong()
                            }

                            "StorageClass" -> {
                                obj.storageClass = it1.value.first().text
                            }

                            "Owner" -> {
                                val owner = Owner.Builder()
                                it1.value.first().children.forEach { it1 ->
                                    when (it1.key) {
                                        "ID" -> owner.id = it1.value.first().text
                                        "DisplayName" -> owner.displayName = it1.value.first().text
                                    }
                                }
                                obj.owner = owner.build()
                            }
                        }
                    }
                    contents.add(obj.build())
                }
                result.contents = contents
            }
            "CommonPrefixes" -> {
                val commonPrefixes = mutableListOf<CommonPrefix>()
                for (commonPrefix in it0.value) {
                    val obj = CommonPrefix.Builder()
                    commonPrefix.children.forEach { it1 ->
                        when (it1.key) {
                            "Prefix" -> {
                                obj.prefix = it1.value.first().text
                            }
                        }
                    }
                    commonPrefixes.add(obj.build())
                }
                result.commonPrefixes = commonPrefixes
            }
        }
    }
    if (result.encodingType == "url") {
        result.prefix = result.prefix?.urlDecode()
        result.delimiter = result.delimiter?.urlDecode()
        result.marker = result.marker?.urlDecode()
        result.nextMarker = result.nextMarker?.urlDecode()
        result.contents = result.contents?.map {
            ObjectSummary.Builder().apply {
                key = it.key?.urlDecode()
                transitionTime = it.transitionTime
                lastModified = it.lastModified
                eTag = it.eTag
                type = it.type
                size = it.size
                storageClass = it.storageClass
                owner = it.owner
            }.build()
        }
        result.commonPrefixes = result.commonPrefixes?.map {
            CommonPrefix.Builder().apply {
                prefix = it.prefix?.urlDecode()
            }.build()
        }
    }
    return result
}

internal fun fromXmlListBucketV2Result(data: ByteArray?): ListBucketV2Result {
    val root = deserializeXmlBody(data, "ListBucketResult")
    val result = ListBucketV2Result()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Name" -> {
                result.name = it0.value.first().text
            }

            "Prefix" -> {
                result.prefix = it0.value.first().text
            }

            "ContinuationToken" -> {
                result.continuationToken = it0.value.first().text
            }

            "NextContinuationToken" -> {
                result.nextContinuationToken = it0.value.first().text
            }

            "MaxKeys" -> {
                result.maxKeys = it0.value.first().text?.toInt()
            }

            "KeyCount" -> {
                result.keyCount = it0.value.first().text?.toInt()
            }

            "Delimiter" -> {
                result.delimiter = it0.value.first().text
            }

            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text.toBoolean()
            }

            "StartAfter" -> {
                result.startAfter = it0.value.first().text
            }

            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }

            "Contents" -> {
                val contents = mutableListOf<ObjectSummary>()
                for (content in it0.value) {
                    val obj = ObjectSummary.Builder()
                    content.children.forEach { it1 ->
                        when (it1.key) {
                            "Key" -> {
                                obj.key = it1.value.first().text
                            }

                            "TransitionTime" -> {
                                obj.transitionTime =
                                    it1.value.first().text
                            }

                            "LastModified" -> {
                                obj.lastModified = it1.value.first().text
                            }

                            "ETag" -> {
                                obj.eTag = it1.value.first().text
                            }

                            "Type" -> {
                                obj.type = it1.value.first().text
                            }

                            "Size" -> {
                                obj.size = it1.value.first().text?.toLong()
                            }

                            "StorageClass" -> {
                                obj.storageClass = it1.value.first().text
                            }

                            "Owner" -> {
                                val owner = Owner.Builder()
                                it1.value.first().children.forEach { it1 ->
                                    when (it1.key) {
                                        "ID" -> owner.id = it1.value.first().text
                                        "DisplayName" -> owner.displayName = it1.value.first().text
                                    }
                                }
                                obj.owner = owner.build()
                            }
                        }
                    }
                    contents.add(obj.build())
                }
                result.contents = contents
            }
            "CommonPrefixes" -> {
                val commonPrefixes = mutableListOf<CommonPrefix>()
                for (commonPrefix in it0.value) {
                    val obj = CommonPrefix.Builder()
                    commonPrefix.children.forEach { it1 ->
                        when (it1.key) {
                            "Prefix" -> {
                                obj.prefix = it1.value.first().text
                            }
                        }
                    }
                    commonPrefixes.add(obj.build())
                }
                result.commonPrefixes = commonPrefixes
            }
        }
    }
    if (result.encodingType == "url") {
        result.prefix = result.prefix?.urlDecode()
        result.delimiter = result.delimiter?.urlDecode()
        result.startAfter = result.startAfter?.urlDecode()
        result.nextContinuationToken = result.nextContinuationToken?.urlDecode()
        result.contents = result.contents?.map {
            ObjectSummary.Builder().apply {
                key = it.key?.urlDecode()
                transitionTime = it.transitionTime
                lastModified = it.lastModified
                eTag = it.eTag
                type = it.type
                size = it.size
                storageClass = it.storageClass
                owner = it.owner
            }.build()
        }
        result.commonPrefixes = result.commonPrefixes?.map {
            CommonPrefix.Builder().apply {
                prefix = it.prefix?.urlDecode()
            }.build()
        }
    }
    return result
}

internal fun fromXmlBucketInfo(data: ByteArray?): BucketInfo {
    val root = deserializeXmlBody(data, "BucketInfo")
    val result = Bucket.Builder()
    root.children.forEach { it2 ->
        when (it2.key) {
            "Bucket" -> {
                it2.value.first().children.forEach { it0 ->
                    when (it0.key) {
                        "AccessMonitor" -> {
                            result.accessMonitor = it0.value.first().text
                        }

                        "CreationDate" -> {
                            result.creationDate = it0.value.first().text
                        }

                        "ExtranetEndpoint" -> {
                            result.extranetEndpoint = it0.value.first().text
                        }

                        "IntranetEndpoint" -> {
                            result.intranetEndpoint = it0.value.first().text
                        }

                        "Location" -> {
                            result.location = it0.value.first().text
                        }

                        "StorageClass" -> {
                            result.storageClass = it0.value.first().text
                        }

                        "TransferAcceleration" -> {
                            result.transferAcceleration = it0.value.first().text
                        }

                        "CrossRegionReplication" -> {
                            result.crossRegionReplication = it0.value.first().text
                        }

                        "Name" -> {
                            result.name = it0.value.first().text
                        }

                        "ResourceGroupId" -> {
                            result.resourceGroupId = it0.value.first().text
                        }

                        "Owner" -> {
                            val owner = Owner.Builder()
                            it0.value.first().children.forEach { it1 ->
                                when (it1.key) {
                                    "ID" -> owner.id = it1.value.first().text
                                    "DisplayName" -> owner.displayName = it1.value.first().text
                                }
                            }
                            result.owner = owner.build()
                        }

                        "AccessControlList" -> {
                            val accessControlList = AccessControlList.Builder()
                            it0.value.first().children.forEach { it1 ->
                                when (it1.key) {
                                    "Grant" -> accessControlList.grant = it1.value.first().text
                                }
                            }
                            result.accessControlList = accessControlList.build()
                        }

                        "Comment" -> {
                            result.comment = it0.value.first().text
                        }

                        "DataRedundancyType" -> {
                            result.dataRedundancyType = it0.value.first().text
                        }

                        "Versioning" -> {
                            result.versioning = it0.value.first().text
                        }

                        "BucketPolicy" -> {
                            val bucketPolicy = BucketPolicy.Builder()
                            it0.value.first().children.forEach { it1 ->
                                when (it1.key) {
                                    "LogBucket" -> bucketPolicy.logBucket = it1.value.first().text
                                    "LogPrefix" -> bucketPolicy.logPrefix = it1.value.first().text
                                }
                            }
                            result.bucketPolicy = bucketPolicy.build()
                        }

                        "BlockPublicAccess" -> {
                            result.blockPublicAccess = it0.value.first().text.toBoolean()
                        }

                        "ServerSideEncryptionRule" -> {
                            val serverSideEncryptionRule = ServerSideEncryptionRule.Builder()
                            it0.value.first().children.forEach { it1 ->
                                when (it1.key) {
                                    "SSEAlgorithm" ->
                                        serverSideEncryptionRule.sSEAlgorithm =
                                            it1.value.first().text

                                    "KMSDataEncryption" ->
                                        serverSideEncryptionRule.kMSDataEncryption =
                                            it1.value.first().text

                                    "KMSMasterKeyID" ->
                                        serverSideEncryptionRule.kMSMasterKeyID =
                                            it1.value.first().text
                                }
                            }
                            result.serverSideEncryptionRule = serverSideEncryptionRule.build()
                        }
                    }
                }
            }
        }
    }

    return BucketInfo.Builder().apply {
        bucket = result.build()
    }.build()
}

internal fun fromXmlBucketLocation(data: ByteArray?): String {
    val root = deserializeXmlBody(data, "LocationConstraint")
    return root.text ?: ""
}
