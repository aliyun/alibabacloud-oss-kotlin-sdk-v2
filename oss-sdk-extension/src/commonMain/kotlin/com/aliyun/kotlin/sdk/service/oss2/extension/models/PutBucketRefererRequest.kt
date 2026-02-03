package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketReferer operation.
 */
public class PutBucketRefererRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var refererConfiguration: RefererConfiguration? = builder.refererConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketRefererRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRefererRequest =
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
        public var refererConfiguration: RefererConfiguration? = null
    
        
        public fun build(): PutBucketRefererRequest {
            return PutBucketRefererRequest(this)
        }

        public constructor(from: PutBucketRefererRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.refererConfiguration = from.refererConfiguration 
        }             
    }

}
