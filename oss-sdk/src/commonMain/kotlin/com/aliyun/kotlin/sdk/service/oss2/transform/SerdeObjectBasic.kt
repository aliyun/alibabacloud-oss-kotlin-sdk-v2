package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.models.internal.CopyObjectResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.internal.DeleteResultXml
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.XmlUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.urlDecode

internal fun fromXmlCopyObject(data: ByteArray?): CopyObjectResultXml {
    val root = deserializeXmlBody(data, "CopyObjectResult")
    val copyObjectResultXml = CopyObjectResultXml()
    root.children.forEach { it0 ->
        when (it0.key) {
            "ETag" -> {
                copyObjectResultXml.eTag = it0.value.first().text
            }
            "LastModified" -> {
                copyObjectResultXml.lastModified = it0.value.first().text
            }
        }
    }
    return copyObjectResultXml
}

internal fun toXmlRestoreRequest(value: RestoreRequest): ByteStream {
    val root = XmlNode("RestoreRequest")
    value.days?.let { root.addChild(XmlNode("Days").apply { text = it.toString() }) }
    value.jobParameters?.tier?.let {
        root.addChild(
            XmlNode("JobParameters").apply {
                addChild(XmlNode("Tier").apply { text = it })
            }
        )
    }
    return ByteStream.fromString(root.toXmlString())
}

internal fun toXmlDeleteMultipleObjects(value: Delete): ByteStream {
    val root = XmlNode("Delete")
    value.quiet?.let { root.addChild(XmlNode("Quiet").apply { text = it.toString() }) }
    value.objects?.forEach { it0 ->
        val deleteObject = XmlNode("Object")
        it0.key?.let { deleteObject.addChild(XmlNode("Key").apply { text = XmlUtils.escapeText(it) }) }
        it0.versionId?.let { deleteObject.addChild(XmlNode("VersionId").apply { text = it }) }
        root.addChild(deleteObject)
    }
    return ByteStream.fromString(root.toXmlString())
}

internal fun fromXmlDeleteMultipleObjects(data: ByteArray?): DeleteResultXml {
    val root = data?.let {
        if (it.isNotEmpty()) {
            deserializeXmlBody(it, "DeleteResult")
        } else {
            null
        }
    }
    val result = DeleteResultXml()
    root?.children?.forEach { it0 ->
        when (it0.key) {
            "Deleted" -> {
                val deleteObjects: MutableList<DeletedInfo> = mutableListOf()
                it0.value.forEach { it1 ->
                    val delete = DeletedInfo.Builder()
                    it1.children.forEach { it2 ->
                        when (it2.key) {
                            "Key" -> {
                                delete.key = it2.value.first().text
                            }
                            "VersionId" -> {
                                delete.versionId = it2.value.first().text
                            }
                            "DeleteMarker" -> {
                                delete.deleteMarker = it2.value.first().text?.toBoolean()
                            }
                            "DeleteMarkerVersionId" -> {
                                delete.deleteMarkerVersionId = it2.value.first().text
                            }
                        }
                    }
                    deleteObjects.add(delete.build())
                }
                result.deletedObjects = deleteObjects
            }
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
        }
    }

    if (result.encodingType == "url") {
        result.deletedObjects = result.deletedObjects?.map {
            DeletedInfo {
                key = it.key?.urlDecode()
                versionId = it.versionId
                deleteMarker = it.deleteMarker
                deleteMarkerVersionId = it.deleteMarkerVersionId
            }
        }
    }

    return result
}
