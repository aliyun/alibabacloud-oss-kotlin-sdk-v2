package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketInventory operation.
 */
public class PutBucketInventoryRequest(builder: Builder): RequestModel(builder) {
    
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket
    
    /**
     * The name of the inventory.
     */
    public val inventoryId: String?
        get() = parameters["inventoryId"]
    
    /**
     * Request body schema.
     */
    public var inventoryConfiguration: InventoryConfiguration? = builder.inventoryConfiguration
    

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketInventoryRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketInventoryRequest =
            Builder().apply(builder).build()
    }

    public class Builder(): RequestModel.Builder() {
    
        /**
        * The name of the bucket.
        */
        public var bucket: String? = null
    
        /**
        * The name of the inventory.
        */
        public var inventoryId: String?
            set(value) {this.parameters["inventoryId"] = requireNotNull(value)}
            get() = parameters["inventoryId"]
    
        /**
        * Request body schema.
        */
        public var inventoryConfiguration: InventoryConfiguration? = null
    
        
        public fun build(): PutBucketInventoryRequest {
            return PutBucketInventoryRequest(this)
        }

        public constructor(from: PutBucketInventoryRequest): this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters) 
            this.bucket = from.bucket 
            this.inventoryConfiguration = from.inventoryConfiguration 
        }             
    }

}
