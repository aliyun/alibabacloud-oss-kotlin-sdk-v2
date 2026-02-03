package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketResourceGroup operation.
 */
public class PutBucketResourceGroupRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The bucket for which you want to modify the ID of the resource group.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The request body schema.
     */
    public var bucketResourceGroupConfiguration: BucketResourceGroupConfiguration? = builder.bucketResourceGroupConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketResourceGroupRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketResourceGroupRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The bucket for which you want to modify the ID of the resource group.
        */
        public var bucket: String? = null
    
        /**
        * The request body schema.
        */
        public var bucketResourceGroupConfiguration: BucketResourceGroupConfiguration? = null
    
        
        public fun build(): PutBucketResourceGroupRequest {
            return PutBucketResourceGroupRequest(this)
        }

        public constructor(from: PutBucketResourceGroupRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.bucketResourceGroupConfiguration = from.bucketResourceGroupConfiguration 
        }             
    }

}
