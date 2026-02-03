package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Container for incremental inventory export cycle information.
 */
@Serializable
@SerialName("IncrementInventorySchedule")
public data class IncrementInventorySchedule(
    /**
     * Describes the frequency at which incremental inventory files are exported, in seconds, currently fixed at 10 minutes.
     */
    @XmlElement("Frequency") public var frequency: Long? = null
) {
    public companion object {
        public operator fun invoke(builder: IncrementInventorySchedule.() -> Unit): IncrementInventorySchedule =
            IncrementInventorySchedule().apply(builder)
    }
}
