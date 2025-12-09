package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.signer.SigningContext
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver
import kotlin.time.Duration

/**
 * Execution context class used to store runtime configuration and state information during request execution
 */
internal class ExecuteContext {
    /**
     * Maximum number of retry attempts allowed for the current request
     */
    var retryMaxAttempts: Int = 0

    /**
     * Timeout duration for a single request attempt
     */
    var requestOnceTimeout: Duration? = null

    /**
     * List of response message handlers used to process [ResponseMessage] objects
     */
    var responseHandlers: MutableList<ResponseHandler> = mutableListOf()

    /**
     * The context used for signing
     */
    var signingContext: SigningContext? = null

    /**
     * List of observer used to track request’s body
     */
    var requestBodyObserver: List<StreamObserver>? = null

    /**
     * The operation should complete as soon as a response is available and headers are read.
     * It is the streaming mode.
     */
    var responseHeadersRead: Boolean? = null

    // var observableInputStream: ObservableInputStream? = null

    // var dataConsumerSupplier: BinaryDataConsumerSupplier? = null
}
