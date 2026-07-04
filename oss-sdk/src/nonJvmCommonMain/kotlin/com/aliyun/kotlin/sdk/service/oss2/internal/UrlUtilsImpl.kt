package com.aliyun.kotlin.sdk.service.oss2.internal

import io.ktor.http.DEFAULT_PORT
import io.ktor.http.Url

internal actual fun parseUrl(url: String): Map<String, String>? {
    // Require an explicit scheme+authority. Ktor's URLBuilder defaults a missing
    // host to "localhost", so without these guards input like "https://#!@" would
    // not map to null the way java.net.URI (jvm) does.
    val schemeSep = url.indexOf("://")
    if (schemeSep < 0) return null
    val rawAuthority = url.substring(schemeSep + 3).takeWhile { it != '/' && it != '?' && it != '#' }
    if (rawAuthority.isEmpty()) return null

    val parsed =
        try {
            Url(url)
        } catch (_: Exception) {
            return null
        }
    if (parsed.host.isEmpty()) return null

    // authority is host[:port], excluding userinfo (OSS request-path building ignores
    // userinfo). specifiedPort is DEFAULT_PORT when no port is explicitly present
    // (Url.port would fill in the scheme default).
    val authority =
        if (parsed.specifiedPort == DEFAULT_PORT) {
            parsed.host
        } else {
            "${parsed.host}:${parsed.specifiedPort}"
        }

    return mapOf(
        "scheme" to parsed.protocol.name,
        "host" to parsed.host,
        "authority" to authority,
    )
}
