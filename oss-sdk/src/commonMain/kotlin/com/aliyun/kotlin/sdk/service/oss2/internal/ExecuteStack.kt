package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import kotlin.concurrent.Volatile

/**
 * Execution stack class used to manage and run middleware chain in sequence
 */
internal class ExecuteStack(
    /**
     * Transport layer middleware, acts as the end processor of the chain
     */
    private val transport: TransportExecuteMiddleware
) {
    /**
     * List of middleware creation functions used to build the chain
     */
    private val stack: MutableList<Pair<(ExecuteMiddleware) -> ExecuteMiddleware, String>> = mutableListOf()

    /**
     * Cached resolved middleware chain instance to avoid re-building
     */
    @Volatile
    private var cached: ExecuteMiddleware? = null

    /**
     * Resolves and builds the full middleware chain
     *
     * @return The fully built middleware instance
     */
    fun resolve(): ExecuteMiddleware {
        if (cached == null) {
            synchronized(this) {
                if (cached == null) {
                    var prev: ExecuteMiddleware = transport
                    for (item in stack.reversed()) {
                        prev = item.first(prev)
                    }
                    cached = prev
                }
            }
        }
        return cached!!
    }

    /**
     * Adds a new middleware factory function to the stack
     *
     * @param create A function used to create a new middleware node
     * @param name  Debug name
     */
    fun push(create: (ExecuteMiddleware) -> ExecuteMiddleware, name: String) {
        this.stack.add(Pair(create, name))
        this.cached = null
    }

    /**
     * Synchronously executes the entire middleware chain and returns the response message
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return A processed response message object [ResponseMessage]
     * @throws Exception If an error occurs during execution
     */
    suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage {
        val handler = resolve()
        return handler.execute(request, context)
    }
}
