package com.aliyun.oss.verify.app

internal expect fun getPlatformEnv(name: String): String?

internal expect suspend fun sendRequest(url: String): String?
