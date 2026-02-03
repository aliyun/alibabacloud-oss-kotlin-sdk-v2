package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration container for incremental inventory.
 */
@Serializable
@SerialName("IncrementalInventory")
public data class IncrementalInventory(
    /**
     * Specifies whether incremental inventory is enabled.
     */
    @XmlElement("IsEnabled") public var isEnabled: Boolean? = null,

    /**
     * Container for incremental inventory export cycle.
     */
    @XmlElement("Schedule") public var schedule: IncrementInventorySchedule? = null,

    /**
     * Configuration container for incremental inventory file attributes.
     */
    @XmlElement("OptionalFields") public var optionalFields: OptionalFields? = null
) {
    public companion object {
        public operator fun invoke(builder: IncrementalInventory.() -> Unit): IncrementalInventory =
            IncrementalInventory().apply(builder)
    }
}
