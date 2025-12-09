package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.io.IOException
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpMethod
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.toJavaDuration

internal class OkHttpTransportImpl(
    config: HttpTransportConfig,
    okBuilder: okhttp3.OkHttpClient.Builder?
) : HttpTransport, AutoCloseable {
    private val httpClient: okhttp3.OkHttpClient

    init {

        // set defaults
        var builder = okBuilder ?: okhttp3.OkHttpClient.Builder()
            .followRedirects(false)
            .connectTimeout(Defaults.CONNECT_TIMEOUT.toJavaDuration())
            .writeTimeout(Defaults.READWRITE_TIMEOUT.toJavaDuration())
            .readTimeout(Defaults.READWRITE_TIMEOUT.toJavaDuration())

        config.proxy?.let { url ->
        }

        config.connectTimeout?.let {
            builder.connectTimeout(it.toJavaDuration())
        }

        config.readWriteTimeout?.let {
            builder.writeTimeout(it.toJavaDuration())
            builder.readTimeout(it.toJavaDuration())
        }

        config.enabledRedirect?.let {
            builder.followRedirects(it)
        }

        httpClient = builder.build()
    }

    override suspend fun execute(
        request: RequestMessage,
        options: RequestOptions
    ): ResponseMessage {
        // For non-streaming requests, the response body is automatically loaded and cached in memory
        try {
            return suspendCancellableCoroutine { continuation ->
                val call = httpClient.newCall(
                    okhttp3.Request.Builder().apply {
                        url(request.url)

                        request.headers.forEach { (k, v) -> header(k, v) }

                        val httpBody = if (HttpMethod.permitsRequestBody(request.method)) {
                            request.body.asRequestBody(options)
                        } else {
                            null
                        }

                        method(request.method, httpBody)
                    }.build()
                )
                continuation.invokeOnCancellation {
                    call.cancel()
                }

                val response = call.execute()

                continuation.resume(
                    ResponseMessage(
                        status = response.message,
                        statusCode = response.code,
                        headers = response.headers.asSdkHeaders(),
                        body = handleResponseBody(response.code, response.body, options),
                        request = request
                    )
                ) { cause, _, _ -> response.close() }
            }
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    override val name: String
        get() = "okhttp3-client"

    override fun close() {
    }

    /**
     * Handles various exceptions that can occur during an API request and converts them into appropriate
     */
    private fun handleException(e: Throwable) = when (e) {
        is CancellationException -> e // propagate coroutine cancellation
        is UnknownHostException -> RequestException("okhttp3 host unreachable", e)
        is ConnectException -> RequestException("okhttp3 request", e)
        is SSLException -> RequestException("okhttp3 ssl", e)
        is IOException -> RequestException("okhttp3 io", e)
        else -> ResponseException("okhttp3 others", e)
    }

    /**
     * convert okhttp3 response to sdk StreamBytes
     */
    private fun handleResponseBody(
        statusCode: Int,
        body: ResponseBody,
        options: RequestOptions
    ): ByteStream? {
        var sdkBody: ByteStream?

        if (statusCode == 203 ||
            statusCode >= 300 ||
            options.httpCompletionOption == null ||
            options.httpCompletionOption == HttpCompletionOption.ResponseContentRead
        ) {
            try {
                sdkBody = ByteStream.fromBytes(body.bytes())
            } finally {
                body.close()
            }
        } else {
            // stream mode
            sdkBody = OkHttpResponseBodyContent(body)
        }
        return sdkBody
    }
}
