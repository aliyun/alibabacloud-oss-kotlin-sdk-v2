package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// Non-JVM targets are single-threaded (or provide no dedicated IO pool),
// so the lock is a no-op and IO work runs on the default dispatcher.
internal actual inline fun <T> platformSynchronized(lock: Any, block: () -> T): T = block()

internal actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
