package com.aliyun.kotlin.sdk.service.oss2.types

import com.aliyun.kotlin.sdk.service.oss2.utils.sourceAt
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/**
 * A [ByteStream] backed by a file, optionally limited to the byte range `[offset, offset + length)`.
 *
 * Each [readFrom] opens its own file handle. A non-zero [offset] positions with a random-access
 * seek, so multiple ranges of the same file can be read concurrently without sharing a read position
 * or re-reading the skipped prefix; a zero offset simply reads (and length-bounds) from the start.
 * The content is streamed rather than buffered whole in memory, making the type reusable for
 * hand-rolled parallel multipart-from-file uploads.
 *
 * @param path the file to read.
 * @param offset the byte offset to start reading from. Defaults to the start of the file.
 * @param length the maximum number of bytes to read. `null` reads to the end of the file.
 */
public class FileContent(
    public val path: Path,
    public val offset: Long,
    public val length: Long? = null,
) : ByteStream.SourceStream() {

    public constructor(path: Path) : this(path, 0L, null)

    init {
        require(offset >= 0L) { "offset must be >= 0, but was $offset" }
        require(length == null || length >= 0L) { "length must be >= 0 or null, but was $length" }
    }

    // Resolved lazily so merely constructing a FileContent does no filesystem IO.
    override val contentLength: Long? by lazy {
        length ?: SystemFileSystem.metadataOrNull(path)?.size?.let { (it - offset).coerceAtLeast(0L) }
    }

    override val isOneShot: Boolean = false

    override fun readFrom(): RawSource {
        val source = if (offset > 0L) path.sourceAt(offset) else SystemFileSystem.source(path)
        return if (length == null) source else LimitedRawSource(source, length)
    }
}

/** Yields at most [remaining] bytes from [upstream], then reports end-of-stream. */
private class LimitedRawSource(
    private val upstream: RawSource,
    private var remaining: Long,
) : RawSource {
    override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
        if (remaining <= 0L) return -1L
        val read = upstream.readAtMostTo(sink, minOf(byteCount, remaining))
        if (read > 0L) remaining -= read
        return read
    }

    override fun close() = upstream.close()
}
