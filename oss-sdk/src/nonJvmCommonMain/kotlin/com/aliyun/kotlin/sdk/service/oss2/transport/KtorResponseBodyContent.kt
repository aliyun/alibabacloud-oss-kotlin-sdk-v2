package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.Abortable
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.contentLength
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.readByteArray

private const val DEFAULT_CHUNK_SIZE = 65536L

/**
 * Streaming [ByteStream.ChannelStream] backed by a Ktor [HttpResponse]. The response body is read
 * incrementally from the underlying [io.ktor.utils.io.ByteReadChannel] when [chunks] is collected,
 * so the whole payload is never held in memory at once.
 */
internal class KtorResponseBodyContent(
    private val response: HttpResponse,
) : ByteStream.ChannelStream(), Abortable {

    override val contentLength: Long? = response.contentLength()

    override val isOneShot: Boolean = true

    override fun chunks(): Flow<ByteArray> = flow {
        val channel = response.bodyAsChannel()
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_CHUNK_SIZE)
            while (!packet.exhausted()) {
                val bytes = packet.readByteArray()
                if (bytes.isNotEmpty()) {
                    emit(bytes)
                }
            }
        }
    }

    override fun close() {
        response.cancel()
    }

    override fun abort() {
        close()
    }
}
