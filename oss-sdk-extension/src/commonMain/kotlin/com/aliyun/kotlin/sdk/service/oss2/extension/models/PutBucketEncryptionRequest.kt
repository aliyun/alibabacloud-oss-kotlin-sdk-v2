package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketEncryption operation.
 */
public class PutBucketEncryptionRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var serverSideEncryptionRule: ServerSideEncryptionRule? = builder.serverSideEncryptionRule
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketEncryptionRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketEncryptionRequest =
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
        public var serverSideEncryptionRule: ServerSideEncryptionRule? = null
    
        
        public fun build(): PutBucketEncryptionRequest {
            return PutBucketEncryptionRequest(this)
        }

        public constructor(from: PutBucketEncryptionRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.serverSideEncryptionRule = from.serverSideEncryptionRule 
        }             
    }

}
