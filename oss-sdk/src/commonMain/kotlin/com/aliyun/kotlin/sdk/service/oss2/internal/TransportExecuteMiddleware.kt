package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpCompletionOption
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestOptions
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage

/**
 * Middleware that handles actual network transport using an HTTP client
 */
internal class TransportExecuteMiddleware(
    /**
     * The HTTP client used for making network requests
     */
    val httpClient: HttpTransport,
    private val logger: LogAgent? = null
) : ExecuteMiddleware {

    /**
     * Sends the request over the network and returns the response
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return Returns the response message received from the server [ResponseMessage]
     * @throws Exception If an error occurs during request execution
     */
    override suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage {
        val opt = RequestOptions(
            uploadObservers = context.requestBodyObserver,
            httpCompletionOption = when (context.responseHeadersRead) {
                true -> HttpCompletionOption.ResponseHeadersRead
                null, false -> null
            },
        )
        logger?.info {
            buildString {
                append("request:-------------------------\n")
                append("${request.method} ${request.url}\n")
                append("${request.headers.map { it.toString() }.joinToString("\n")}\n")
                append("request end:---------------------")
            }
        }
        val response = this.httpClient.execute(request, opt)
        logger?.info {
            buildString {
                append("response:------------------------\n")
                append("${response.statusCode} ${response.request?.url}\n")
                append("${response.headers.map { "${it.key}:${it.value}" }.joinToString("\n")}\n")
                append("response end:--------------------")
            }
        }
        return response
    }
}
