package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.SerializationException
import com.aliyun.kotlin.sdk.service.oss2.hash.md5
import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.FileContent
import com.aliyun.kotlin.sdk.service.oss2.utils.Base64Utils
import com.aliyun.kotlin.sdk.service.oss2.utils.MimeUtils

internal object SerdeUtils {

    fun serializeInput(
        request: RequestModel,
        input: OperationInput,
        block: OperationInput.() -> Unit = {}
    ) {
        // headers
        input.headers.putAll(request.headers)

        // parameters
        input.parameters.putAll(request.parameters).apply {
        }

        // custom serializer
        input.apply {
            block()
        }
    }

    fun serializeXmlBody(input: OperationInput, value: Any?): ByteStream? {
        // TODO
        value?.let {
        }
        return null
    }

    fun addContentType(input: OperationInput) {
        if (input.headers.containsKey("Content-Type")) {
            return
        }

        val value = input.key?.let {
            MimeUtils.getMimetype(it, null)
        } ?: when (val body = input.body) {
            is FileContent -> MimeUtils.getMimetype(body.path.name, MimeUtils.DEFAULT_MIMETYPE)
            else -> MimeUtils.DEFAULT_MIMETYPE
        } ?: MimeUtils.DEFAULT_MIMETYPE

        input.headers.put("Content-Type", value)
    }

    fun addContentMd5(input: OperationInput) {
        if (input.headers.containsKey("Content-MD5")) {
            return
        }

        var md5 = "1B2M2Y8AsgTpgAmY7PhCfg=="
        input.body?.let {
            md5 = when (it) {
                is ByteStream.Buffer -> Base64Utils.encodeToString(it.bytes().md5())
                else -> throw SerializationException("Only support ByteStream.Buffer")
            }
        }

        input.headers.put("Content-MD5", md5)
    }

    fun deserializeXmlBody(data: ByteArray?, rootName: String): XmlNode {
        if (data == null) {
            throw DeserializationException("body is null")
        }
        val root = XmlNode.parse(data)
        if (root.name.local != rootName) {
            throw DeserializationException("Not found tag <$rootName>")
        }
        return root
    }
}
