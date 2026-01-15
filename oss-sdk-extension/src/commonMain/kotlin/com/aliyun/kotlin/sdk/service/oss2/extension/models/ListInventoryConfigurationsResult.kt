package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores inventory configuration list.
 */
@Serializable
@SerialName("ListInventoryConfigurationsResult")
@XmlRoot
public data class ListInventoryConfigurationsResult(
    /**
     * The container that stores inventory configurations.
     */
    @XmlElement("InventoryConfiguration") public var inventoryConfigurations: List<InventoryConfiguration>? = null,

    /**
     * Specifies whether to list all inventory tasks configured for the bucket.Valid values: true and false
     * - The value of false indicates that all inventory tasks configured for the bucket are listed.
     * - The value of true indicates that not all inventory tasks configured for the bucket are listed.
     * To list the next page of inventory configurations, set the continuation-token parameter in the next request to the value of the NextContinuationToken header in the response to the current request.
     */
    @XmlElement("IsTruncated") public var isTruncated: Boolean? = null,

    /**
     * If the value of IsTruncated in the response is true and value of this header is not null, set the continuation-token parameter in the next request to the value of this header.
     */
    @XmlElement("NextContinuationToken") public var nextContinuationToken: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ListInventoryConfigurationsResult.() -> Unit): ListInventoryConfigurationsResult =
            ListInventoryConfigurationsResult().apply(builder)
    }
}
