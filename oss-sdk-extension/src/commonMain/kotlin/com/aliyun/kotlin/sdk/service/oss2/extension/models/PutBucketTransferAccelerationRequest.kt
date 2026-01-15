package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketTransferAcceleration operation.
 */
public class PutBucketTransferAccelerationRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The container of the request body.
     */
    public var transferAccelerationConfiguration: TransferAccelerationConfiguration? = builder.transferAccelerationConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketTransferAccelerationRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketTransferAccelerationRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The container of the request body.
        */
        public var transferAccelerationConfiguration: TransferAccelerationConfiguration? = null
    
        
        public fun build(): PutBucketTransferAccelerationRequest {
            return PutBucketTransferAccelerationRequest(this)
        }

        public constructor(from: PutBucketTransferAccelerationRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.transferAccelerationConfiguration = from.transferAccelerationConfiguration 
        }     
    }

}
