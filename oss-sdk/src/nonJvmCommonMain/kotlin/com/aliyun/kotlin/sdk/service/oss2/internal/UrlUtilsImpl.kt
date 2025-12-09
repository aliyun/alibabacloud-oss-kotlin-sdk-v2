package com.aliyun.kotlin.sdk.service.oss2.internal

-import io.ktor.http.Url
-import io.ktor.http.authority
-import io.ktor.http.parseUrl

actual fun parseUrl(url: String): Map<String, String>? {
    parseUrl(endpoint)?.let (
        return mapOf<String, String>(
            "scheme" to it.protocol.name,
            "host" to it.host,
            "authority" to it.authority,
        )
    )
    return null
}
