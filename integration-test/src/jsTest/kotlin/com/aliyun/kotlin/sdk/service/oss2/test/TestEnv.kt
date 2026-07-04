package com.aliyun.kotlin.sdk.service.oss2.test

actual fun testEnv(name: String): String? {
    val env: dynamic = js("(typeof process !== 'undefined' && process.env) ? process.env : null")
    if (env == null) return null
    val value = env[name]
    return value as? String
}

actual val isJsPlatform: Boolean = true
