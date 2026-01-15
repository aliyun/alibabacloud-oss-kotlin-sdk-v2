package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the ListBucketInventory operation.
 */
public class ListBucketInventoryResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores inventory configuration list.
     */
    private val delegate: ListInventoryConfigurationsResult?
        get() = innerBody as? ListInventoryConfigurationsResult

    /**
     * The container that stores inventory configurations.
     */
    public val inventoryConfigurations: List<InventoryConfiguration>? = delegate?.inventoryConfigurations

    /**
     * Specifies whether to list all inventory tasks configured for the bucket.Valid values: true and false
     * - The value of false indicates that all inventory tasks configured for the bucket are listed.
     * - The value of true indicates that not all inventory tasks configured for the bucket are listed.
     * To list the next page of inventory configurations, set the continuation-token parameter in the next request to the value of the NextContinuationToken header in the response to the current request.
     */
    public val isTruncated: Boolean? = delegate?.isTruncated

    /**
     * If the value of IsTruncated in the response is true and value of this header is not null, set the continuation-token parameter in the next request to the value of this header.
     */
    public val nextContinuationToken: String? = delegate?.nextContinuationToken

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketInventoryResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): ListBucketInventoryResult {
            return ListBucketInventoryResult(this)
        }
    }
}
