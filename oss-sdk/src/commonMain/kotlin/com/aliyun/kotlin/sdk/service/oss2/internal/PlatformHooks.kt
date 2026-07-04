package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Reads a platform environment variable, returning null when it is not set.
 */
internal expect fun getPlatformEnv(name: String): String?

/**
 * Runs [block] holding [lock] on platforms that support monitor locks.
 * On single-threaded targets this simply invokes [block].
 */
internal expect inline fun <T> platformSynchronized(lock: Any, block: () -> T): T

/**
 * Dispatcher used for blocking IO. Falls back to the default dispatcher on
 * platforms without a dedicated IO pool.
 */
internal expect val ioDispatcher: CoroutineDispatcher
