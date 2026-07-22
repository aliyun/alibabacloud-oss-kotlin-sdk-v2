package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.files.Path

internal actual fun Path.sourceAt(offset: Long): RawSource =
    SeekableFileSource(toString(), offset)

private const val READ_CHUNK: Int = 256 * 1024

/**
 * Reads a file from [offset] to end-of-file via positioned reads on a [SeekableFileHandle]. A single
 * scratch buffer is reused across reads, so no per-read allocation occurs. Positioning is O(1) per
 * read, so parallel part readers of the same file do not re-read the skipped prefix.
 */
private class SeekableFileSource(
    pathString: String,
    offset: Long,
) : RawSource {
    private val handle = SeekableFileHandle(pathString)
    private val scratch = ByteArray(READ_CHUNK)
    private var position: Long = offset
    private var closed = false

    override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
        require(byteCount >= 0L) { "byteCount < 0: $byteCount" }
        if (byteCount == 0L) return 0L
        var toRead = byteCount
        var total = 0L
        while (toRead > 0L) {
            val want = minOf(toRead, scratch.size.toLong()).toInt()
            val read = handle.read(position, scratch, 0, want)
            if (read <= 0) break
            sink.write(scratch, 0, read)
            position += read
            total += read
            toRead -= read
            if (read < want) break
        }
        return if (total == 0L) -1L else total
    }

    override fun close() {
        if (!closed) {
            closed = true
            handle.close()
        }
    }
}

/**
 * A file opened for positioned, read-only access. The name is runtime-neutral: actuals may back it
 * with any platform file API (Node `fs` on js/wasm today, posix `pread` on native in the future).
 */
internal expect class SeekableFileHandle(path: String) {
    /**
     * Reads up to [length] bytes starting at file [position] into [destination] at
     * [destinationOffset]. Returns the number of bytes read, or -1 at end of file. The caller owns
     * and reuses [destination]; implementations MUST NOT allocate a destination array per call.
     */
    fun read(position: Long, destination: ByteArray, destinationOffset: Int, length: Int): Int

    fun close()
}
