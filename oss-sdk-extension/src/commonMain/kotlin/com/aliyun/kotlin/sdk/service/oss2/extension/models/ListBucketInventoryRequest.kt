package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the ListBucketInventory operation.
 */
public class ListBucketInventoryRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * Specify the start position of the list operation. You can obtain this token from the NextContinuationToken field of last ListBucketInventory's result.
     */
    public val continuationToken: String?
        get() = parameters["continuation-token"]
    

    public inline fun copy(block: Builder.() -> Unit = {}): ListBucketInventoryRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketInventoryRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * Specify the start position of the list operation. You can obtain this token from the NextContinuationToken field of last ListBucketInventory's result.
        */
        public var continuationToken: String?
            set(value) {this.parameters["continuation-token"] = requireNotNull(value)}
            get() = parameters["continuation-token"]
    
        
        public fun build(): ListBucketInventoryRequest {
            return ListBucketInventoryRequest(this)
        }

        public constructor(from: ListBucketInventoryRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
