package com.aliyun.oss.verify.app

import java.net.URI

internal actual fun getPlatformEnv(name: String): String? {
    return System.getenv(name)
}

internal actual suspend fun sendRequest(url: String): String? {
    val url = URI.create(url).toURL()
    val connection = url.openConnection()
    val data = connection.getInputStream().readBytes()
    return data.decodeToString()
}
