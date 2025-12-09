package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.Base64Utils
import kotlin.math.min

public interface ResponseHandler {
    public suspend fun onResponse(response: ResponseMessage)
}

internal class ChecksumUploadResponseHandler(
    private var crc64: Crc64
) : ResponseHandler {
    override suspend fun onResponse(response: ResponseMessage) {
        val headers = response.headers
        val statusCode = response.statusCode

        if (statusCode / 100 != 2) {
            return
        }
        headers["x-oss-hash-crc64ecma"]?.let {
            val serverCrc = it
            val clientCrc = crc64.digestValue.toULong().toString()
            if (serverCrc != clientCrc) {
                throw InconsistentException(clientCrc, serverCrc, headers)
            }
        }
    }
}

internal class ServiceErrorResponseHandler : ResponseHandler {
    override suspend fun onResponse(response: ResponseMessage) {
        val headers = response.headers
        val errorFields = mutableMapOf<String, String>()
        val statusCode = response.statusCode

        if (statusCode / 100 == 2) {
            return
        }

        var data = response.body?.toByteArray() ?: byteArrayOf()

        if (data.isEmpty()) {
            // try to get error from x-oss-err header
            headers["x-oss-err"]?.let { value ->
                data = Base64Utils.decodeString(value)
            }
        }

        try {
            if (data.isNotEmpty()) {
                val root = XmlNode.parse(data)
                if (root.name.local == "Error") {
                    root.children.forEach { (k, v) ->
                        errorFields.put(k, v.first().text ?: "")
                    }
                } else {
                    errorFields.put("Message", toErrorMessage("Not found tag <Error>", data))
                }
            } else {
                errorFields.put("Message", toErrorMessage("Empty body", data))
            }
        } catch (_: Exception) {
            // Fail to parse xml
            errorFields.put("Message", toErrorMessage("Failed to parse xml from response body", data))
        }

        val requestTarget = when (response.request) {
            null -> ""
            else -> "${response.request.method} ${response.request.url}"
        }

        throw ServiceException(
            statusCode = statusCode,
            headers = headers,
            errorFields = errorFields,
            snapshot = data,
            requestTarget = requestTarget,
            timestamp = headers["Date"] ?: ""
        )
    }

    private fun toErrorMessage(prefix: String, data: ByteArray): String {
        val rawString = data.decodeToString()
        if (rawString.isEmpty()) {
            return prefix
        }
        return prefix + ", part response body " + rawString.substring(0, min(255, rawString.length))
    }
}
