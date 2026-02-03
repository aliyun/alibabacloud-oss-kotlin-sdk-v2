package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketInventory operation.
 */
public class GetBucketInventoryResult(builder: Builder): ResultModel(builder) { 

    /**
     * The inventory task configured for a bucket.
     */
    public val inventoryConfiguration: InventoryConfiguration?
        get() = innerBody as? InventoryConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketInventoryResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketInventoryResult {
            return GetBucketInventoryResult(this)
        }
    }
}
