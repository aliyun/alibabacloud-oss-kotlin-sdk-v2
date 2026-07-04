package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual fun getPlatformEnv(name: String): String? = System.getenv(name)

internal actual inline fun <T> platformSynchronized(lock: Any, block: () -> T): T =
    synchronized(lock, block)

internal actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
