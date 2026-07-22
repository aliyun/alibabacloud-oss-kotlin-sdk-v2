package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.files.Path
import java.io.RandomAccessFile

internal actual fun Path.sourceAt(offset: Long): RawSource =
    RandomAccessFileSource(toString(), offset)

/**
 * Reads a file from [offset] to end-of-file via [RandomAccessFile]. The initial [RandomAccessFile.seek]
 * positions in O(1); subsequent reads advance sequentially from the handle.
 */
private class RandomAccessFileSource(
    pathString: String,
    offset: Long,
) : RawSource {
    private val file = RandomAccessFile(pathString, "r")
    private val scratch = ByteArray(64 * 1024)
    private var closed = false

    init {
        if (offset > 0L) file.seek(offset)
    }

    override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
        require(byteCount >= 0L) { "byteCount < 0: $byteCount" }
        if (byteCount == 0L) return 0L
        var toRead = byteCount
        var total = 0L
        while (toRead > 0L) {
            val want = minOf(toRead, scratch.size.toLong()).toInt()
            val read = file.read(scratch, 0, want)
            if (read <= 0) break
            sink.write(scratch, 0, read)
            total += read
            toRead -= read
            if (read < want) break
        }
        return if (total == 0L) -1L else total
    }

    override fun close() {
        if (!closed) {
            closed = true
            file.close()
        }
    }
}
