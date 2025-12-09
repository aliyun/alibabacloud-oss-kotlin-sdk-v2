package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.UnsafeIoApi
import kotlinx.io.unsafe.UnsafeBufferOperations
import okhttp3.RequestBody
import okio.BufferedSink
import kotlin.math.min

/**
 * Returns a [okio.Source] backed by this [kotlinx.io.RawSource].
 *
 * Closing one of these sources will also close another one.
 */
internal fun RawSource.asOkioSource(srcObserver: List<StreamObserver>? = null): okio.Source = object : okio.Source {
    private val buffer = Buffer()
    private val observer = srcObserver

    override fun close() {
        this@asOkioSource.close()
        observer?.forEach { it.closed() }
    }

    override fun read(sink: okio.Buffer, byteCount: Long): Long {
        val readBytes = this@asOkioSource.readAtMostTo(buffer, byteCount)
        if (readBytes == -1L) return -1L

        var remainingBytes = readBytes
        while (remainingBytes > 0) {
            @OptIn(UnsafeIoApi::class)
            UnsafeBufferOperations.readFromHead(buffer) { data, from, to ->
                val len = to - from
                remainingBytes -= len
                sink.write(data, from, len)
                observer?.forEach { it.data(data, from, len) }
                len
            }
        }
        return readBytes
    }

    override fun timeout(): okio.Timeout = okio.Timeout.NONE
}

/**
 * Returns a [kotlinx.io.RawSource] backed by this [okio.Source].
 *
 * Closing one of these sources will also close another one.
 */
internal fun okio.Source.asRawSource(): RawSource = object : RawSource {
    private val buffer = okio.Buffer()

    override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
        val readBytes = this@asRawSource.read(buffer, byteCount)
        if (readBytes == -1L) return -1L

        var remaining = readBytes
        while (remaining > 0) {
            @OptIn(UnsafeIoApi::class)
            UnsafeBufferOperations.writeToTail(sink, 1) { data, from, to ->
                val toRead = min((to - from).toLong(), remaining).toInt()
                val read = buffer.read(data, from, toRead)
                check(read != -1) { "Buffer was exhausted before reading $toRead bytes from it." }
                remaining -= read
                read
            }
        }

        return readBytes
    }

    override fun close() {
        this@asRawSource.close()
    }
}

/** Returns a new request body that transmits the content of this. */
internal fun okio.Source.asRequestBody(contentLength: Long? = -1L): RequestBody =
    object : RequestBody() {

        override fun contentType() = null

        override fun contentLength() = contentLength ?: -1

        override fun writeTo(sink: BufferedSink) {
            this@asRequestBody.use { source -> sink.writeAll(source) }
        }
    }

private fun checkOffsetAndCount(
    arrayLength: Long,
    offset: Long,
    count: Long,
) {
    if (offset or count < 0L || offset > arrayLength || arrayLength - offset < count) {
        throw ArrayIndexOutOfBoundsException("length=$arrayLength, offset=$offset, count=$offset")
    }
}

internal fun ByteArray.toRequestBody(
    observer: List<StreamObserver>? = null,
    offset: Int = 0,
    byteCount: Int = size,
): RequestBody {
    checkOffsetAndCount(size.toLong(), offset.toLong(), byteCount.toLong())
    return object : RequestBody() {
        override fun contentType() = null

        override fun contentLength() = byteCount.toLong()

        override fun writeTo(sink: BufferedSink) {
            when (observer) {
                null -> sink.write(this@toRequestBody, offset, byteCount)
                else -> {
                    val step = 64 * 1024
                    var remaining = byteCount
                    var off = offset
                    while (remaining > 0) {
                        val toCopy = minOf(remaining, step)
                        sink.write(this@toRequestBody, off, toCopy)
                        observer.forEach {
                            it.data(this@toRequestBody, off, toCopy)
                        }
                        remaining -= toCopy
                        off += toCopy
                    }
                }
            }
        }
    }
}

internal fun okhttp3.Headers.asSdkHeaders(): MutableMap<String, String> {
    val result = MapUtils.headersMap()
    this.forEach { (key, value) -> result.put(key, value) }
    return result
}

/** Returns a new request body that transmits the content of this. */
internal fun ByteStream?.asRequestBody(options: RequestOptions): RequestBody {
    return when (this) {
        is ByteStream.Buffer -> bytes().toRequestBody(options.uploadObservers)
        is ByteStream.SourceStream -> readFrom()
            .asOkioSource(options.uploadObservers)
            .asRequestBody(contentLength)
        null -> RequestBody.EMPTY
    }
}
