package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.CredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsFetchException
import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.signer.Signer
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage

/**
 * Middleware for signing requests before they are sent over the network
 */
internal class SignerExecuteMiddleware(
    /**
     * Reference to the next middleware handler in the chain
     */
    private val nextHandler: ExecuteMiddleware,

    /**
     * The credentials provider used to retrieve signing credentials
     */
    private val provider: CredentialsProvider,

    /**
     * The signer implementation used to sign requests
     */
    private val singer: Signer,

    private val logger: LogAgent? = null
) : ExecuteMiddleware {

    /**
     * executes the request after signing it
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return Returns the processed response message object [ResponseMessage]
     * @throws Exception If an error occurs during execution
     */
    override suspend fun execute(request: RequestMessage, context: ExecuteContext): ResponseMessage {
        return nextHandler.execute(signRequest(request, context), context)
    }

    /**
     * Signs the request using the configured signer and credentials provider
     *
     * @param request The request message object [RequestMessage]
     * @param context The execution context object [ExecuteContext]
     * @return Returns the original or modified request message after signing
     */
    private suspend fun signRequest(request: RequestMessage, context: ExecuteContext): RequestMessage {
        if (this.provider is AnonymousCredentialsProvider) {
            logger?.debug { "Using anonymous credentials, skip signing" }
            return request
        }

        val cred: Credentials
        try {
            cred = this.provider.getCredentials()
        } catch (e: Exception) {
            logger?.error { "Failed to fetch credentials: ${e.message}" }
            throw CredentialsFetchException(e)
        }

        if (!cred.hasKeys()) {
            logger?.error { "Credentials are null or empty" }
            throw CredentialsException("Credentials is null or empty.")
        }

        logger?.debug {
            "Signing request: method=${request.method}, url=${request.url}, signer=${singer::class.simpleName}"
        }

        context.signingContext?.let {
            it.credentials = cred
            it.request = request
            this.singer.sign(it)
            logger?.debug {
                "stringToSign: ${context.signingContext?.stringToSign}"
            }

            it.request?.headers?.get("Authorization")?.let { auth ->
                logger?.debug { "Authorization header set: ${auth.take(30)}..." }
            }

            return requireNotNull(it.request)
        }

        return request
    }
}
