package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the DeleteBucketInventory operation.
 */
public class DeleteBucketInventoryRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The name of the inventory that you want to delete.
     */
    public val inventoryId: String?
        get() = parameters["inventoryId"]
    

    public inline fun copy(block: Builder.() -> Unit = {}): DeleteBucketInventoryRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketInventoryRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The name of the inventory that you want to delete.
        */
        public var inventoryId: String?
            set(value) {this.parameters["inventoryId"] = requireNotNull(value)}
            get() = parameters["inventoryId"]
    
        
        public fun build(): DeleteBucketInventoryRequest {
            return DeleteBucketInventoryRequest(this)
        }

        public constructor(from: DeleteBucketInventoryRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
        }             
    }

}
