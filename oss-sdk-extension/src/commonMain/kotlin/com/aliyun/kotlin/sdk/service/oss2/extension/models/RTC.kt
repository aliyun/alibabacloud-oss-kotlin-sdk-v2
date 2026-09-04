package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores information about the Replication Time Control (RTC) status.
 */
@Serializable
@SerialName("RTC")
public data class RTC(
    /**
     * Specifies whether to enable RTC.Valid values:
     * *   disabled
     * *   enabled
     */
    @XmlElement("Status") public var status: String? = null
) {
    public companion object {
        public operator fun invoke(builder: RTC.() -> Unit): RTC =
            RTC().apply(builder)
    }
}
