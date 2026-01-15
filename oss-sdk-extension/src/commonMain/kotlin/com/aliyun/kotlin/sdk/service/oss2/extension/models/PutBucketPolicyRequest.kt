package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The request for the PutBucketPolicy operation.
 */
public class PutBucketPolicyRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request parameters.
     */
    public var body: ByteStream? = builder.body
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketPolicyRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketPolicyRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The request parameters.
        */
        public var body: ByteStream? = null
    
        
        public fun build(): PutBucketPolicyRequest {
            return PutBucketPolicyRequest(this)
        }

        public constructor(from: PutBucketPolicyRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.body = from.body 
        }             
    }

}
