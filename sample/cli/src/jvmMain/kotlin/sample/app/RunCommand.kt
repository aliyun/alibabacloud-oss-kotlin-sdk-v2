package sample.app

import kotlinx.coroutines.runBlocking

actual fun runCommand(block: suspend () -> Unit): Unit = runBlocking { block() }
