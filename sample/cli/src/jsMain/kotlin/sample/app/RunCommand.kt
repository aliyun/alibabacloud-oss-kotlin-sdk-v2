package sample.app

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

private external val process: dynamic

// Node stays alive while the launched coroutine has pending I/O; exit once it settles.
@OptIn(DelicateCoroutinesApi::class)
actual fun runCommand(block: suspend () -> Unit) {
    GlobalScope.promise { block() }.then(
        onFulfilled = { process.exit(0) },
        onRejected = {
            println(it.message ?: it.toString())
            process.exit(1)
        }
    )
}
