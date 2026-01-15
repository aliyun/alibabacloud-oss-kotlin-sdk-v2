package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetBucketTransferAcceleration operation.
 */
public class GetBucketTransferAccelerationRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketTransferAccelerationRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketTransferAccelerationRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        
        public fun build(): GetBucketTransferAccelerationRequest {
            return GetBucketTransferAccelerationRequest(this)
        }

        public constructor(from: GetBucketTransferAccelerationRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
