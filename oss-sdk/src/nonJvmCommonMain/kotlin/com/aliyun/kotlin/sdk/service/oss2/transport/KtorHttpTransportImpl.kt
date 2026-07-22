package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.exceptions.NonRetryableTimeoutException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.HttpMethod
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.DurationUnit

internal class KtorHttpTransportImpl(config: HttpTransportConfig) : HttpTransport, AutoCloseable {
    private val httpClient: HttpClient

    init {
        val configuration: HttpClientConfig<*>.() -> Unit = {
            val socketTimeout = config.readWriteTimeout ?: Defaults.READWRITE_TIMEOUT
            val connectTimeout = config.connectTimeout ?: Defaults.CONNECT_TIMEOUT
            install(HttpTimeout) {
                socketTimeoutMillis = socketTimeout.toLong(DurationUnit.MILLISECONDS)
                connectTimeoutMillis = connectTimeout.toLong(DurationUnit.MILLISECONDS)
            }

            expectSuccess = false
            followRedirects = config.enabledRedirect ?: false
        }

        // The engine is selected from the artifact on the classpath (Js for the JS target).
        httpClient = HttpClient(configuration)
    }

    override suspend fun execute(
        request: RequestMessage,
        options: RequestOptions
    ): ResponseMessage {
        try {
            val requestBody = request.body?.toByteArray()
            if (requestBody != null) {
                notifyUploadObservers(requestBody, options.uploadObservers)
            }
            val response: HttpResponse = httpClient.request(request.url) {
                method = HttpMethod.parse(request.method)
                request.headers.forEach { (k, v) -> headers.set(k, v) }
                if (requestBody != null) {
                    setBody(requestBody)
                }
            }

            return ResponseMessage(
                status = response.status.description,
                statusCode = response.status.value,
                headers = fromHeaders(response.headers),
                body = handleResponseBody(response.status.value, response, options),
                request = request
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw handleException(e)
        }
    }

    /**
     * Buffers the whole response into memory for error/short responses or when the caller wants the
     * content read eagerly; otherwise returns a streaming [KtorResponseBodyContent]. Mirrors the
     * [HttpCompletionOption] handling of the OkHttp transport.
     */
    private suspend fun handleResponseBody(
        statusCode: Int,
        response: HttpResponse,
        options: RequestOptions
    ): ByteStream? {
        return if (statusCode == 203 ||
            statusCode >= 300 ||
            options.httpCompletionOption == null ||
            options.httpCompletionOption == HttpCompletionOption.ResponseContentRead
        ) {
            ByteStream.fromBytes(response.bodyAsBytes())
        } else {
            KtorResponseBodyContent(response)
        }
    }

    private fun notifyUploadObservers(
        body: ByteArray,
        observers: List<com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver>?
    ) {
        if (observers.isNullOrEmpty()) return
        val step = 16 * 1024
        var offset = 0
        while (offset < body.size) {
            val toCopy = minOf(step, body.size - offset)
            observers.forEach { it.data(body, offset, toCopy) }
            offset += toCopy
        }
    }

    override val name: String
        get() = "ktor-client"

    override fun close() {
        httpClient.close()
    }

    private fun handleException(e: Throwable) = when (e) {
        is CancellationException -> e // propagate coroutine cancellation
        is ClientRequestException -> RequestException("ktor request", e)
        is ServerResponseException -> ResponseException("ktor response", e)
        is SocketTimeoutException, is ConnectTimeoutException -> RequestException("ktor timeout", e)
        is HttpRequestTimeoutException -> NonRetryableTimeoutException("ktor timeout", e)
        is IOException -> RequestException("ktor io", e)
        else -> RequestException("ktor others", e)
    }

    private fun fromHeaders(headers: io.ktor.http.Headers): MutableMap<String, String> {
        val result = MapUtils.headersMap()
        headers.entries().forEach { (key, value) -> result.put(key, value.joinToString(",")) }
        return result
    }
}
