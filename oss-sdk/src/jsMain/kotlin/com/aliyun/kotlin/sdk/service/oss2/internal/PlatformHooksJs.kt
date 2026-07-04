package com.aliyun.kotlin.sdk.service.oss2.internal

internal actual fun getPlatformEnv(name: String): String? {
    val env: dynamic = js("(typeof process !== 'undefined' && process.env) ? process.env : null")
    if (env == null) return null
    val value = env[name]
    return value as? String
}
