package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.models.CopyPartResult
import com.aliyun.kotlin.sdk.service.oss2.models.Part
import com.aliyun.kotlin.sdk.service.oss2.models.Upload
import com.aliyun.kotlin.sdk.service.oss2.models.internal.CompleteMultipartUploadResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.internal.InitiateMultipartUploadResultXml
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListMultipartUploads
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListPartResult
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.urlDecode

internal fun fromXmlInitiateMultipartUpload(data: ByteArray?): InitiateMultipartUploadResultXml {
    val root = deserializeXmlBody(data, "InitiateMultipartUploadResult")
    val result = InitiateMultipartUploadResultXml()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Bucket" -> {
                result.bucket = it0.value.first().text
            }
            "Key" -> {
                result.key = it0.value.first().text
            }
            "UploadId" -> {
                result.uploadId = it0.value.first().text
            }
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
        }
    }
    if (result.encodingType == "url") {
        result.key = result.key?.urlDecode()
    }

    return result
}

internal fun toXmlCompleteMultipartUpload(value: CompleteMultipartUpload): ByteStream {
    val root = XmlNode("CompleteMultipartUpload")
    value.parts?.forEach { it0 ->
        val part = XmlNode("Part")
        it0.partNumber?.let { part.addChild(XmlNode("PartNumber").apply { text = it.toString() }) }
        it0.eTag?.let { part.addChild(XmlNode("ETag").apply { text = it }) }
        root.addChild(part)
    }

    return ByteStream.fromString(root.toXmlString())
}

internal fun fromXmlCompleteMultipartUpload(data: ByteArray?): CompleteMultipartUploadResultXml {
    val root = deserializeXmlBody(data, "CompleteMultipartUploadResult")
    val result = CompleteMultipartUploadResultXml()
    root.children.forEach { it0 ->
        when (it0.key) {
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
            "Location" -> {
                result.location = it0.value.first().text
            }
            "Bucket" -> {
                result.bucket = it0.value.first().text
            }
            "Key" -> {
                result.key = it0.value.first().text
            }
            "ETag" -> {
                result.eTag = it0.value.first().text
            }
        }
    }
    if (result.encodingType == "url") {
        result.key = result.key?.urlDecode()
    }

    return result
}

internal fun fromXmlCopyPartResult(data: ByteArray?): CopyPartResult {
    val root = deserializeXmlBody(data, "CopyPartResult")
    val result = CopyPartResult.Builder()
    root.children.forEach { it0 ->
        when (it0.key) {
            "LastModified" -> {
                result.lastModified = it0.value.first().text
            }
            "ETag" -> {
                result.eTag = it0.value.first().text
            }
        }
    }

    return result.build()
}

internal fun fromXmlListMultipartUploadsResult(data: ByteArray?): ListMultipartUploads {
    val root = deserializeXmlBody(data, "ListMultipartUploadsResult")
    val result = ListMultipartUploads()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Bucket" -> {
                result.bucket = it0.value.first().text
            }
            "KeyMarker" -> {
                result.keyMarker = it0.value.first().text
            }
            "UploadIdMarker" -> {
                result.uploadIdMarker = it0.value.first().text
            }
            "NextKeyMarker" -> {
                result.nextKeyMarker = it0.value.first().text
            }
            "NextUploadIdMarker" -> {
                result.nextUploadIdMarker = it0.value.first().text
            }
            "Delimiter" -> {
                result.delimiter = it0.value.first().text
            }
            "Prefix" -> {
                result.prefix = it0.value.first().text
            }
            "MaxUploads" -> {
                result.maxUploads = it0.value.first().text?.toLong()
            }
            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text?.toBoolean()
            }
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
            "Upload" -> {
                val uploads = mutableListOf<Upload>()
                it0.value.forEach { it1 ->
                    val upload = Upload.Builder()
                    it1.children.forEach { it2 ->
                        when (it2.key) {
                            "Key" -> {
                                upload.key = it2.value.first().text
                            }
                            "UploadId" -> {
                                upload.uploadId = it2.value.first().text
                            }
                            "Initiated" -> {
                                upload.initiated = it2.value.first().text
                            }
                        }
                    }
                    uploads.add(upload.build())
                }
                result.uploads = uploads
            }
        }
    }

    if (result.encodingType == "url") {
        result.delimiter = result.delimiter?.urlDecode()
        result.keyMarker = result.keyMarker?.urlDecode()
        result.nextKeyMarker = result.nextKeyMarker?.urlDecode()
        result.prefix = result.prefix?.urlDecode()
        result.uploads = result.uploads?.map {
            Upload.Builder().apply {
                key = it.key?.urlDecode()
                uploadId = it.uploadId
                initiated = it.initiated
            }.build()
        }
    }

    return result
}

internal fun fromXmlListPartResult(data: ByteArray?): ListPartResult {
    val root = deserializeXmlBody(data, "ListPartsResult")
    val result = ListPartResult()
    root.children.forEach { it0 ->
        when (it0.key) {
            "Bucket" -> {
                result.bucket = it0.value.first().text
            }
            "Key" -> {
                result.key = it0.value.first().text
            }
            "UploadId" -> {
                result.uploadId = it0.value.first().text
            }
            "NextPartNumberMarker" -> {
                result.nextPartNumberMarker = it0.value.first().text?.toLong()
            }
            "MaxParts" -> {
                result.maxParts = it0.value.first().text?.toLong()
            }
            "IsTruncated" -> {
                result.isTruncated = it0.value.first().text?.toBoolean()
            }
            "EncodingType" -> {
                result.encodingType = it0.value.first().text
            }
            "Part" -> {
                val parts = mutableListOf<Part>()
                it0.value.forEach { it1 ->
                    val upload = Part.Builder()
                    it1.children.forEach { it2 ->
                        when (it2.key) {
                            "PartNumber" -> {
                                upload.partNumber = it2.value.first().text?.toLong()
                            }
                            "LastModified" -> {
                                upload.lastModified = it2.value.first().text
                            }
                            "ETag" -> {
                                upload.eTag = it2.value.first().text
                            }
                            "Size" -> {
                                upload.size = it2.value.first().text?.toLong()
                            }
                        }
                    }
                    parts.add(upload.build())
                }
                result.parts = parts
            }
        }
    }

    if (result.encodingType == "url") {
        result.key = result.key?.urlDecode()
    }

    return result
}
