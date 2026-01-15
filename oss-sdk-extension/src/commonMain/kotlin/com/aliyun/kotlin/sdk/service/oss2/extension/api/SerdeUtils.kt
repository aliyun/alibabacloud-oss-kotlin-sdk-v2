package com.aliyun.kotlin.sdk.service.oss2.extension.api

import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.SerializationException
import com.aliyun.kotlin.sdk.service.oss2.extension.models.RequestModel
import com.aliyun.kotlin.sdk.service.oss2.hash.md5
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlSerializer
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.Base64Utils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

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

    inline fun <reified T> deserializeXmlBody(data: ByteArray?): T {
        return data?.let {
            XmlSerializer.Default.decodeFromString<T>(it.decodeToString())
        } ?: throw DeserializationException("body is null")
    }

    inline fun <reified T> serializeXmlBody(value: T): ByteArray {
        val xml = XmlSerializer.Default.encodeToString(value)
        return xml.toByteArray()
    }
}

internal fun ByteArray.toByteStream(): ByteStream = ByteStream.fromBytes(this)
