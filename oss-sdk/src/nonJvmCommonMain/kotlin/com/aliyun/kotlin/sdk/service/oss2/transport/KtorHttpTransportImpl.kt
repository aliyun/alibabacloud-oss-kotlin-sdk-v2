package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.exceptions.NonRetryableTimeoutException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.ProxyBuilder
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
import io.ktor.http.Url
import io.ktor.http.content.OutgoingContent
import kotlinx.io.IOException
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.DurationUnit

internal class KtorHttpTransportImpl(config: HttpTransportConfig): HttpTransport, AutoCloseable {
    private val httpClient: io.ktor.client.HttpClient

    init {

        val configuration: HttpClientConfig<*>.() -> Unit = {

            // proxy
            this.engine {
                config.proxy?.let { url ->
                    proxy = ProxyBuilder.http(Url(url))
                }
            }

            //timeout
            val socketTimeout = config.readWriteTimeout ?: Defaults.READWRITE_TIMEOUT
            val connectTimeout = config.connectTimeout ?: Defaults.CONNECT_TIMEOUT
            this.install(HttpTimeout) {
                this.socketTimeoutMillis = socketTimeout.toLong(DurationUnit.MILLISECONDS)
                this.connectTimeoutMillis = connectTimeout.toLong(DurationUnit.MILLISECONDS)
            }

            this.expectSuccess = false

            this.followRedirects = config.enabledRedirect ?: false

            config.httpClientConfig(this)
        }

        if (config.engine != null) {
            this.httpClient = io.ktor.client.HttpClient(config.engine, configuration)
        } else {
            this.httpClient = io.ktor.client.HttpClient(configuration)
        }
    }

    override suspend fun execute(
        request: RequestMessage,
        options: RequestOptions
    ): ResponseMessage {
        // For non-streaming requests, the response body is automatically loaded and cached in memory
        try {
            val response: HttpResponse = this.httpClient.request(request.url) {
                this.method = HttpMethod.parse(request.method)
                this.headers.apply {
                    request.headers.forEach { (k, v) -> this.set(k,v) }
                }
                setBody(toBody(request.body))
            }

            return ResponseMessage(
                status = response.status.description,
                statusCode = response.status.value,
                headers = fromHeaders(response.headers),
                body = response.bodyAsBytes(),
                request = request
            )
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    override val name: String
        get() = "ktor-client"

    override fun close() {
        httpClient.close()
    }

    /**
     * Handles various exceptions that can occur during an API request and converts them into appropriate
     */
    private fun handleException(e: Throwable) = when (e) {
        is CancellationException -> e // propagate coroutine cancellation
        is ClientRequestException -> RequestException("ktor request", e)
        is ServerResponseException -> ResponseException("ktor response", e)
        is SocketTimeoutException, is ConnectTimeoutException -> RequestException("ktor timeout", e)
        is HttpRequestTimeoutException -> NonRetryableTimeoutException("ktor timeout", e)
        is IOException -> RequestException("ktor io", e)
        else -> RequestException("ktor others", e)
    }

    /**
     * convert sdk body to Ktor body
     */
    private fun toBody(content: ByteStream?): Any? {
        return when(content) {
            is ByteStream.Buffer -> content.bytes()
            null -> object: OutgoingContent.NoContent() {}
            else -> null
        }
    }

    /**
     * convert Ktor Headers to sdk Headers
     */
    private fun fromHeaders(headers: io.ktor.http.Headers): MutableMap<String, String> {
        val result = MapUtils.headersMap()
        headers.entries().forEach { (key, value) -> result.put(key, value.joinToString(",")) }
        return result
    }
}
