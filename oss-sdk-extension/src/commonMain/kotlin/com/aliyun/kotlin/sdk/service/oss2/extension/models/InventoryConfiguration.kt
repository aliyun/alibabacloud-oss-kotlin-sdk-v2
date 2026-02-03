package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the configurations of the inventory.
 */
@Serializable
@SerialName("InventoryConfiguration")
@XmlRoot
public data class InventoryConfiguration(
    /**
     * Specifies whether to include the version information about the objects in inventory lists. Valid values:
     * *   All: The information about all versions of the objects is exported.
     * *   Current: Only the information about the current versions of the objects is exported.
     */
    @XmlElement("IncludedObjectVersions") public var includedObjectVersions: String? = null,

    /**
     * The container that stores the configuration fields in inventory lists.
     */
    @XmlElement("OptionalFields") public var optionalFields: OptionalFields? = null,

    /**
     * The name of the inventory. The name must be unique in the bucket.
     */
    @XmlElement("Id") public var id: String? = null,

    /**
     * Specifies whether to enable the bucket inventory feature. Valid values:
     * *   true
     * *   false
     */
    @XmlElement("IsEnabled") public var isEnabled: Boolean? = null,

    /**
     * The container that stores the exported inventory lists.
     */
    @XmlElement("Destination") public var destination: InventoryDestination? = null,

    /**
     * The container that stores information about the frequency at which inventory lists are exported.
     */
    @XmlElement("Schedule") public var schedule: InventorySchedule? = null,

    /**
     * The container that stores the prefix used to filter objects. Only objects whose names contain the specified prefix are included in the inventory.
     */
    @XmlElement("Filter") public var filter: InventoryFilter? = null

) {
    public companion object {
        public operator fun invoke(builder: InventoryConfiguration.() -> Unit): InventoryConfiguration =
            InventoryConfiguration().apply(builder)
    }
}
