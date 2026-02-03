package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetBucketInventory operation.
 */
public class GetBucketInventoryRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The name of the inventory to be queried.
     */
    public val inventoryId: String?
        get() = parameters["inventoryId"]
    

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketInventoryRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketInventoryRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The name of the inventory to be queried.
        */
        public var inventoryId: String?
            set(value) {this.parameters["inventoryId"] = requireNotNull(value)}
            get() = parameters["inventoryId"]
    
        
        public fun build(): GetBucketInventoryRequest {
            return GetBucketInventoryRequest(this)
        }

        public constructor(from: GetBucketInventoryRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
