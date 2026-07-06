package com.aliyun.kotlin.sdk.service.oss2.utils

import org.khronos.webgl.Int8Array
import org.khronos.webgl.toByteArray
import kotlin.js.ExperimentalWasmJsInterop

// only runs under Node; on the browser it is never constructed. Mirrors kotlinx-io.
@OptIn(ExperimentalWasmJsInterop::class)
private fun nodeOpen(path: String): Int = js("eval('require')('fs').openSync(path, 'r')")

@OptIn(ExperimentalWasmJsInterop::class)
private fun nodeRead(fd: Int, position: Double, length: Int): Int8Array? =
    js(
        "(function(){var fs=eval('require')('fs');var b=new Int8Array(length);" +
            "var n=fs.readSync(fd,b,0,length,position);return n<=0?null:(n<length?b.subarray(0,n):b);})()",
    )

@OptIn(ExperimentalWasmJsInterop::class)
private fun nodeClose(fd: Int) {
    js("eval('require')('fs').closeSync(fd)")
}

internal actual class SeekableFileHandle actual constructor(path: String) {
    private val fd: Int = nodeOpen(path)

    actual fun read(position: Long, destination: ByteArray, destinationOffset: Int, length: Int): Int {
        // A wasmJs ByteArray lives in wasm memory and can't be handed to Node directly. Read into a
        // JS Int8Array, then copy the bytes across the boundary via the kotlinx-browser typed-array
        // bridge. This handle only runs under Node.
        val arr = nodeRead(fd, position.toDouble(), length) ?: return -1
        val bytes = arr.toByteArray()
        bytes.copyInto(destination, destinationOffset)
        return bytes.size
    }

    actual fun close() {
        nodeClose(fd)
    }
}
