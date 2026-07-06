package com.aliyun.kotlin.sdk.service.oss2.utils

internal actual class SeekableFileHandle actual constructor(path: String) {
    // only runs under Node; on the browser it is never constructed. Mirrors kotlinx-io.
    private val fs: dynamic = js("eval('require')('fs')")
    private val fd: Int = fs.openSync(path, "r") as Int

    actual fun read(position: Long, destination: ByteArray, destinationOffset: Int, length: Int): Int {
        // A Kotlin/JS ByteArray is an Int8Array at runtime, and fs.readSync accepts any TypedArray,
        // so Node writes the bytes straight into [destination] with no intermediate copy.
        val bytesRead = fs.readSync(fd, destination, destinationOffset, length, position.toDouble()) as Int
        return if (bytesRead <= 0) -1 else bytesRead
    }

    actual fun close() {
        fs.closeSync(fd)
    }
}
