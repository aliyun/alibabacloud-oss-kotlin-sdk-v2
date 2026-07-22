package sample.app

import kotlinx.coroutines.runBlocking

// Node stays alive while the launched coroutine has pending I/O; exit once it settles.
actual fun runCommand(block: suspend () -> Unit): Unit = runBlocking { block() }
