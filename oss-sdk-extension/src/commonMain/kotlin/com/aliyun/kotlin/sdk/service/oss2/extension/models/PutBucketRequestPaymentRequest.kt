package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketRequestPayment operation.
 */
public class PutBucketRequestPaymentRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var requestPaymentConfiguration: RequestPaymentConfiguration? = builder.requestPaymentConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketRequestPaymentRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRequestPaymentRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The request body schema.
        */
        public var requestPaymentConfiguration: RequestPaymentConfiguration? = null
    
        
        public fun build(): PutBucketRequestPaymentRequest {
            return PutBucketRequestPaymentRequest(this)
        }

        public constructor(from: PutBucketRequestPaymentRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.requestPaymentConfiguration = from.requestPaymentConfiguration 
        }             
    }

}
