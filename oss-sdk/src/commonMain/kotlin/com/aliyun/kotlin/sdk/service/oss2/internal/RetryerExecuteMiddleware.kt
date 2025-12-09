@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.retry.NopRetryer
import com.aliyun.kotlin.sdk.service.oss2.retry.RetryHandler
import com.aliyun.kotlin.sdk.service.oss2.retry.Retryer
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlin.time.ExperimentalTime

/**
 * Middleware that adds retry capability to request execution
 */
internal class RetryerExecuteMiddleware(
    /**
     * Reference to the next middleware handler in the chain
     */
    private val nextHandler: ExecuteMiddleware,

    /**
     * The retry strategy used for handling failures
     */
    private val retryer: Retryer = NopRetryer(),
    private val logger: LogAgent? = null,
    private val retryHandler: RetryHandler? = null
) : ExecuteMiddleware {

    /**
     * executes the request with retry logic on failure
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return Returns the successfully processed response message
     * @throws RuntimeException If maximum attempts are reached or the error is not retryable
     */
    override suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage {
        val attempts = context.retryMaxAttempts
        var error: Throwable? = null
        val signTime = context.signingContext?.signTimeInEpoch
        val expirationTime = context.signingContext?.expirationInEpoch
        var retries = 0
        while (true) {
            try {
                val response = nextHandler.execute(request, context)
                context.requestBodyObserver?.forEach { observer ->
                    observer.finished()
                }
                return response
            } catch (e: Throwable) {
                error = e
            }

            if (retries + 1 >= attempts) {
                break
            }

            if (request.body != null && request.body!!.isOneShot) {
                logger?.info { "Should not retry. Body is one shot." }
                break
            }

            if (!retryer.isErrorRetryable(error)) {
                logger?.info { "Should not retry. Cannot retry exception." }
                break
            }

            // delay
            val delayMs = retryer.retryDelay(retries + 1, error).inWholeMilliseconds
            logger?.info { "Should retry. Current retries: $retries, delay: $delayMs, exception: $error." }
            try {
                delay(delayMs)
            } catch (e: CancellationException) {
                // Ignore
                error = e
                break
            }

            // reset to init state
            context.requestBodyObserver?.forEach { observer ->
                observer.reset()
            }

            // reset signing time
            context.signingContext?.signTimeInEpoch = signTime
            context.signingContext?.expirationInEpoch = expirationTime
            retries++

            retryHandler?.retrying(request, context, error)
        }

        context.requestBodyObserver?.forEach { observer ->
            observer.error(error)
        }
        throw error
    }
}
