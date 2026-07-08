package com.aliyun.kotlin.sdk.service.oss2.test

import platform.Foundation.NSProcessInfo

actual fun testEnv(name: String): String? {
    return NSProcessInfo.processInfo.environment[name] as? String
}

actual val isJsPlatform: Boolean = true
