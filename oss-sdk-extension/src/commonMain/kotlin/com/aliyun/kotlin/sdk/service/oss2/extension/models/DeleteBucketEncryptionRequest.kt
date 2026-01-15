package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the DeleteBucketEncryption operation.
 */
public class DeleteBucketEncryptionRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    

    public inline fun copy(block: Builder.() -> Unit = {}): DeleteBucketEncryptionRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketEncryptionRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        
        public fun build(): DeleteBucketEncryptionRequest {
            return DeleteBucketEncryptionRequest(this)
        }

        public constructor(from: DeleteBucketEncryptionRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
