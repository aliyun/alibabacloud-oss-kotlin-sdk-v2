package com.aliyun.oss.verify.app

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

internal actual fun getPlatformEnv(name: String): String? {
    val env: dynamic = js("(typeof process !== 'undefined' && process.env) ? process.env : null")
    if (env == null) return null
    val value = env[name]
    return value as? String
}

private val client = HttpClient()
internal actual suspend fun sendRequest(url: String): String? {
    return try {
        val response = client.get(url)
        if (response.status.value in 200..299) {
            response.bodyAsText()
        } else {
            null
        }
    } catch (e: Throwable) {
        null
    }
}
