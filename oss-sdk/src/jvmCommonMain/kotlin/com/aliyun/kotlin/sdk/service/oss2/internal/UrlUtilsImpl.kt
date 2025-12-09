package com.aliyun.kotlin.sdk.service.oss2.internal

import java.net.URI

internal actual fun parseUrl(url: String): Map<String, String>? {
    try {
        val uri: URI = URI(url)
        if (uri.host == null) {
            return null
        }
        return mapOf<String, String>(
            "scheme" to uri.scheme,
            "host" to uri.host,
            "authority" to uri.authority,
        )
    } catch (_: Exception) {
    }
    return null
}
