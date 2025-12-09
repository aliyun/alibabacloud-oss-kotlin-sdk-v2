package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage

/**
 * Interface for defining synchronous and asynchronous request execution logic
 */
internal interface ExecuteMiddleware {
    /**
     * Synchronously executes a request and returns the response message
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return The processed response message object [ResponseMessage]
     * @throws Exception If an error occurs during execution
     */
    suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage
}
