package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores regions in which the destination bucket can be located with the TransferType information.
 */
@Serializable
@SerialName("LocationTransferType")
public data class LocationTransferType(
    /**
     * The container that stores the transfer type.
     */
    @XmlElement("TransferTypes") public var transferTypes: TransferTypes? = null,

    /**
     * The regions in which the destination bucket can be located.
     */
    @XmlElement("Location") public var location: String? = null
) {
    public companion object {
        public operator fun invoke(builder: LocationTransferType.() -> Unit): LocationTransferType =
            LocationTransferType().apply(builder)
    }
}
