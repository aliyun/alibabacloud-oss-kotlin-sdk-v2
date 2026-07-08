package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun readEnv(name: String): String? =
    js("(typeof process !== 'undefined' && process.env && process.env[name] != null) ? String(process.env[name]) : null")

internal actual fun getPlatformEnv(name: String): String? = readEnv(name)

// Non-JVM targets are single-threaded (or provide no dedicated IO pool),
// so the lock is a no-op and IO work runs on the default dispatcher.
internal actual inline fun <T> platformSynchronized(lock: Any, block: () -> T): T = block()

internal actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
