package com.aliyun.kotlin.sdk.service.oss2.internal

import java.net.URI

internal actual fun parseUrl(url: String): Map<String, String>? {
    try {
        val uri: URI = URI(url)
        if (uri.host == null) {
            return null
        }
        // authority is host[:port], excluding userinfo (OSS request-path building
        // ignores userinfo). uri.authority would include userinfo when present.
        val authority = if (uri.port == -1) uri.host else "${uri.host}:${uri.port}"
        return mapOf<String, String>(
            "scheme" to uri.scheme,
            "host" to uri.host,
            "authority" to authority,
        )
    } catch (_: Exception) {
    }
    return null
}
