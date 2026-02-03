package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Contains the frequency that inventory lists are exported
 */
@Serializable
@SerialName("Schedule")
public data class InventorySchedule(
    /**
     * The frequency at which the inventory list is exported. Valid values:
     * - Daily: The inventory list is exported on a daily basis.
     * - Weekly: The inventory list is exported on a weekly basis.
     */
    @XmlElement("Frequency") public var frequency: String? = null
) {
    public companion object {
        public operator fun invoke(builder: InventorySchedule.() -> Unit): InventorySchedule =
            InventorySchedule().apply(builder)
    }
}
