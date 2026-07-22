package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSProcessInfo
import platform.objc.objc_sync_enter
import platform.objc.objc_sync_exit

@OptIn(ExperimentalForeignApi::class)
internal actual fun getPlatformEnv(name: String): String? {
    return NSProcessInfo.processInfo.environment[name] as? String
}

internal actual inline fun <T> platformSynchronized(lock: Any, block: () -> T): T {
    objc_sync_enter(lock)
    try {
        return block()
    } finally {
        objc_sync_exit(lock)
    }
}

internal actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
