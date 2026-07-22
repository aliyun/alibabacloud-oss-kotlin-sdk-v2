package com.aliyun.kotlin.sdk.service.oss2.test

actual fun testEnv(name: String): String? = System.getenv(name)

actual val isJsPlatform: Boolean = false
