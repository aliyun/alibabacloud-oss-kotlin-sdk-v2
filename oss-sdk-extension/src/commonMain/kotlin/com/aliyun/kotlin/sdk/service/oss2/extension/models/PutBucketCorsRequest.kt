package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketCors operation.
 */
public class PutBucketCorsRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var corsConfiguration: CORSConfiguration? = builder.corsConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketCorsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketCorsRequest =
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
        public var corsConfiguration: CORSConfiguration? = null
    
        
        public fun build(): PutBucketCorsRequest {
            return PutBucketCorsRequest(this)
        }

        public constructor(from: PutBucketCorsRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.corsConfiguration = from.corsConfiguration
        }     
    }

}
