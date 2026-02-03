package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketHttpsConfig operation.
 */
public class PutBucketHttpsConfigRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * This name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var httpsConfiguration: HttpsConfiguration? = builder.httpsConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketHttpsConfigRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketHttpsConfigRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * This name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The request body schema.
        */
        public var httpsConfiguration: HttpsConfiguration? = null
    
        
        public fun build(): PutBucketHttpsConfigRequest {
            return PutBucketHttpsConfigRequest(this)
        }

        public constructor(from: PutBucketHttpsConfigRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.httpsConfiguration = from.httpsConfiguration 
        }             
    }

}
