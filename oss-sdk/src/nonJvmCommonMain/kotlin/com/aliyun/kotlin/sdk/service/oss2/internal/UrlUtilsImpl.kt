package com.aliyun.kotlin.sdk.service.oss2.internal

internal actual fun parseUrl(url: String): Map<String, String>? {
    val schemeSep = url.indexOf("://")
    if (schemeSep <= 0) return null
    val scheme = url.substring(0, schemeSep)

    val rest = url.substring(schemeSep + 3)
    if (rest.isEmpty()) return null

    val authorityEnd = rest.indexOfFirst { it == '/' || it == '?' || it == '#' }
    val authority = if (authorityEnd == -1) rest else rest.substring(0, authorityEnd)
    if (authority.isEmpty()) return null

    val hostPort = authority.substringAfterLast('@')
    val host = if (hostPort.startsWith("[")) {
        hostPort.substringAfter('[').substringBefore(']')
    } else {
        hostPort.substringBefore(':')
    }
    if (host.isEmpty()) return null

    return mapOf(
        "scheme" to scheme,
        "host" to host,
        "authority" to authority,
    )
}
