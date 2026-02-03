package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores information about exported inventory lists.
 */
@Serializable
@SerialName("Destination")
public data class InventoryDestination(
    /**
     * The container that stores information about the bucket in which exported inventory lists are stored.
     */
    @XmlElement("OSSBucketDestination") public var oSSBucketDestination: InventoryOSSBucketDestination? = null
) {
    public companion object {
        public operator fun invoke(builder: InventoryDestination.() -> Unit): InventoryDestination =
            InventoryDestination().apply(builder)
    }
}
