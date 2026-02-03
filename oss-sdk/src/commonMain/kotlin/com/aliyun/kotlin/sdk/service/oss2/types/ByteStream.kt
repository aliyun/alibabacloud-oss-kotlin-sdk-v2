package com.aliyun.kotlin.sdk.service.oss2.types

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.readByteArray

/**
 * Represents an abstract read-only stream of bytes
 */
public sealed class ByteStream {
    /**
     * The content length if known
     */
    public open val contentLength: Long? = null

    /**
     * Flag indicating if the body can only be consumed once. If false the underlying stream
     * must be capable of being replayed.
     */
    public open val isOneShot: Boolean = true

    /**
     * Variant of a [ByteStream] with payload represented as an in-memory byte buffer.
     */
    public abstract class Buffer : ByteStream() {
        // implementations MUST be idempotent and replayable or else they should be modeled differently
        // this is meant for simple in-memory representations only
        override val isOneShot: Boolean = false

        /**
         * Provides [ByteArray] to be consumed. This *MUST* be idempotent as the data may be
         * read multiple times.
         */
        public abstract fun bytes(): ByteArray
    }

    /**
     * Variant of a [ByteStream] with a streaming payload read from an [kotlinx.io.Source]
     */
    public abstract class SourceStream : ByteStream() {
        /**
         * Provides [kotlinx.io.Source] to read from/consume.
         *
         * Implementations that are replayable ([isOneShot] = `false`) MUST provide a fresh source
         * reset to the original state on each invocation of [readFrom]. Consumers are allowed
         * to close the stream and ask for a new one.
         */
        public abstract fun readFrom(): RawSource
    }

    public companion object {
        /**
         * Create a [ByteStream] from a [String]
         */
        public fun fromString(str: String): ByteStream = StringContent(str)

        /**
         * Create a [ByteStream] from a [ByteArray]
         */
        public fun fromBytes(bytes: ByteArray): ByteStream = ByteArrayContent(bytes)

        /**
         * Create a [ByteStream] from a [Path]
         */
        public fun fromFile(path: Path): ByteStream = FileContent(path)
    }
}

/**
 * Consume the [ByteStream] and pull the entire contents into memory as a [ByteArray].
 * Only do this if you are sure the contents fit in-memory as this will read the entire contents
 * of a streaming variant.
 */
public suspend fun ByteStream.toByteArray(): ByteArray = when (val stream = this) {
    is ByteStream.Buffer -> stream.bytes()
    is ByteStream.SourceStream -> stream.readFrom().buffered().readByteArray()
}

public fun ByteStream.cancel() {
    when (val stream = this) {
        is ByteStream.Buffer -> stream.bytes()
        is ByteStream.SourceStream -> stream.readFrom().close()
    }
}

/**
 * Return a [Flow] that consumes the underlying [ByteStream] when collected.
 *
 * @param bufferSize the size of the buffers to emit from the flow. All buffers emitted
 * will be of this size except for the last one which may be less than the requested buffer size.
 * This parameter has no effect for the [ByteStream.Buffer] variant. The emitted [ByteArray]
 * will be whatever size the in-memory buffer already is in that case.
 */
public fun ByteStream.toFlow(bufferSize: Long = 8192): Flow<ByteArray> = when (this) {
    is ByteStream.Buffer -> flowOf(bytes())
    is ByteStream.SourceStream -> readFrom().toFlow(bufferSize).flowOn(Dispatchers.IO)
}

private fun RawSource.toFlow(bufferSize: Long): Flow<ByteArray> {
    val source = this
    return flow {
        val sink = Buffer()
        while (true) {
            val rc = source.readAtMostTo(sink, bufferSize)
            if (rc == -1L) break
            if (sink.size >= bufferSize) {
                val bytes = sink.readByteArray(bufferSize.toInt())
                emit(bytes)
            }
        }
        if (sink.size > 0L) {
            emit(sink.readByteArray())
        }
    }
}

public fun ByteArray.toByteStream(): ByteStream = ByteStream.fromBytes(this)
public fun String.toByteStream(): ByteStream = ByteStream.fromString(this)
