package com.aliyun.kotlin.sdk.service.oss2.internal

private fun readEnv(name: String): String? =
    js("(typeof process !== 'undefined' && process.env && process.env[name] != null) ? String(process.env[name]) : null")

internal actual fun getPlatformEnv(name: String): String? = readEnv(name)
