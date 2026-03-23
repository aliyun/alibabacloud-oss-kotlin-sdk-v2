package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the region in which the destination bucket can be located.
 */
@Serializable
@SerialName("ReplicationLocation")
@XmlRoot
public data class ReplicationLocation(
    /**
     * The regions in which the destination bucket can be located.
     */
    @XmlElement("Location") public var locations: List<String>? = null,

    /**
     * The container that stores regions in which the destination bucket can be located with TransferType specified.
     */
    @XmlElement("LocationTransferTypeConstraint") public var locationTransferTypeConstraint: LocationTransferTypeConstraint? = null,

    /**
     * The container that stores regions in which the RTC can be enabled.
     */
    @XmlElement("LocationRTCConstraint") public var locationRTCConstraint: LocationRTCConstraint? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationLocation.() -> Unit): ReplicationLocation =
            ReplicationLocation().apply(builder)
    }
}
