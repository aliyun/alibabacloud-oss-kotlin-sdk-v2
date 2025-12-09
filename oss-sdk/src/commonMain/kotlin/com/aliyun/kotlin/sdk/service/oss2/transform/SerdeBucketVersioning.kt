package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.CommonPrefix
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMarkerEntry
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectVersion
import com.aliyun.kotlin.sdk.service.oss2.models.Owner
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListVersionsResult
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.urlDecode

internal fun toXmlVersioningConfiguration(value: VersioningConfiguration?): ByteStream {
    val root = XmlNode("VersioningConfiguration")
    value?.status?.let { root.addChild(XmlNode("Status").apply { text = it }) }

    return ByteStream.fromString(root.toXmlString())
}

internal fun fromXmlVersioningConfiguration(data: ByteArray?): VersioningConfiguration {
    val root = deserializeXmlBody(data, "VersioningConfiguration")
    val result = VersioningConfiguration.Builder()
    root.children.forEach {
        when (it.key) {
            "Status" -> {
                result.status = it.value.first().text
            }
        }
    }

    return result.build()
}

internal fun fromXmlListVersionsResult(data: ByteArray?): ListVersionsResult {
    val root = deserializeXmlBody(data, "ListVersionsResult")
    val result = ListVersionsResult()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Name" -> {
                result.name = it0.value.first().text
            }
            "MaxKeys" -> {
                result.maxKeys = it0.value.first().text?.toLong()
            }
            "NextVersionIdMarker" -> {
                result.nextVersionIdMarker = it0.value.first().text
            }
            "NextKeyMarker" -> {
                result.nextKeyMarker = it0.value.first().text
            }
            "VersionIdMarker" -> {
                result.versionIdMarker = it0.value.first().text
            }
            "KeyMarker" -> {
                result.keyMarker = it0.value.first().text
            }
            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text?.toBoolean()
            }
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
            "Delimiter" -> {
                result.delimiter = it0.value.first().text
            }
            "Prefix" -> {
                result.prefix = it0.value.first().text
            }
            "Version" -> {
                val versions = mutableListOf<ObjectVersion>()
                for (node in it0.value) {
                    val version = ObjectVersion.Builder()
                    node.children.forEach { it1 ->
                        when (it1.key) {
                            "Key" -> {
                                version.key = it1.value.first().text
                            }
                            "VersionId" -> {
                                version.versionId = it1.value.first().text
                            }
                            "IsLatest" -> {
                                version.isLatest = it1.value.first().text?.toBoolean()
                            }
                            "LastModified" -> {
                                version.lastModified = it1.value.first().text
                            }
                            "ETag" -> {
                                version.eTag = it1.value.first().text
                            }
                            "TransitionTime" -> {
                                version.transitionTime = it1.value.first().text
                            }
                            "Size" -> {
                                version.size = it1.value.first().text?.toLong()
                            }
                            "StorageClass" -> {
                                version.storageClass = it1.value.first().text
                            }
                            "Owner" -> {
                                val owner = Owner.Builder()
                                it1.value.first().children.forEach { it1 ->
                                    when (it1.key) {
                                        "ID" -> owner.id = it1.value.first().text
                                        "DisplayName" -> owner.displayName = it1.value.first().text
                                    }
                                }
                                version.owner = owner.build()
                            }
                        }
                    }
                    versions.add(version.build())
                }
                result.versions = versions
            }
            "DeleteMarker" -> {
                val deleteMarkers = mutableListOf<DeleteMarkerEntry>()
                for (node in it0.value) {
                    val deleteMarker = DeleteMarkerEntry.Builder()
                    node.children.forEach { it1 ->
                        when (it1.key) {
                            "Key" -> {
                                deleteMarker.key = it1.value.first().text
                            }
                            "VersionId" -> {
                                deleteMarker.versionId = it1.value.first().text
                            }
                            "IsLatest" -> {
                                deleteMarker.isLatest = it1.value.first().text?.toBoolean()
                            }
                            "LastModified" -> {
                                deleteMarker.lastModified = it1.value.first().text
                            }
                            "Owner" -> {
                                val owner = Owner.Builder()
                                it1.value.first().children.forEach { it1 ->
                                    when (it1.key) {
                                        "ID" -> owner.id = it1.value.first().text
                                        "DisplayName" -> owner.displayName = it1.value.first().text
                                    }
                                }
                                deleteMarker.owner = owner.build()
                            }
                        }
                    }
                    deleteMarkers.add(deleteMarker.build())
                }
                result.deleteMarkers = deleteMarkers
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
        result.delimiter = result.delimiter?.urlDecode()
        result.keyMarker = result.keyMarker?.urlDecode()
        result.nextKeyMarker = result.nextKeyMarker?.urlDecode()
        result.prefix = result.prefix?.urlDecode()
        result.versions = result.versions?.map {
            ObjectVersion.Builder().apply {
                key = it.key?.urlDecode()
                versionId = it.versionId
                lastModified = it.lastModified
                isLatest = it.isLatest
                eTag = it.eTag
                storageClass = it.storageClass
                transitionTime = it.transitionTime
                restoreInfo = it.restoreInfo
                size = it.size
                owner = it.owner
            }.build()
        }
        result.deleteMarkers = result.deleteMarkers?.map {
            DeleteMarkerEntry.Builder().apply {
                key = it.key?.urlDecode()
                versionId = it.versionId
                lastModified = it.lastModified
                isLatest = it.isLatest
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
