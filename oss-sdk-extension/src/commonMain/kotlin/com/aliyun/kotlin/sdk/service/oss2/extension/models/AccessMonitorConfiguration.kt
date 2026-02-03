package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores access monitor configuration.
 */
@Serializable
@SerialName("AccessMonitorConfiguration")
@XmlRoot
public data class AccessMonitorConfiguration(
    /**
     * The access tracking status of the bucket. Valid values:
     * - Enabled: Access tracking is enabled.
     * - Disabled: Access tracking is disabled.
     */
    @XmlElement("Status") public var status: String? = null
) {
    public companion object {
        public operator fun invoke(builder: AccessMonitorConfiguration.() -> Unit): AccessMonitorConfiguration =
            AccessMonitorConfiguration().apply(builder)
    }
}
