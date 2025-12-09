package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage

/**
 * Middleware for checking and processing response messages after request execution
 */
internal class ResponseCheckerExecuteMiddleware(
    /**
     * Reference to the next middleware handler in the chain
     */
    private val nextHandler: ExecuteMiddleware
) : ExecuteMiddleware {
    /**
     * executes the next middleware and processes the response message
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return Returns the processed response message object [ResponseMessage]
     * @throws Exception If an error occurs during execution
     */
    override suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage {
        val response = nextHandler.execute(request, context)
        context.responseHandlers.forEach { handler -> handler.onResponse(response) }
        return response
    }
}
