package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores Replication Time Control (RTC) configurations.
 */
@Serializable
@SerialName("ReplicationRule")
@XmlRoot
public data class RtcConfiguration(
    /**
     * The container that stores the status of RTC.
     */
    @XmlElement("RTC") public var rtc: RTC? = null,

    /**
     * The ID of the data replication rule for which you want to configure RTC.
     */
    @XmlElement("ID") public var id: String? = null
) {
    public companion object {
        public operator fun invoke(builder: RtcConfiguration.() -> Unit): RtcConfiguration =
            RtcConfiguration().apply(builder)
    }
}
