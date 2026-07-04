package sample.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// Kotlin/JS has no Dispatchers.IO; the single-threaded event loop uses Default.
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
