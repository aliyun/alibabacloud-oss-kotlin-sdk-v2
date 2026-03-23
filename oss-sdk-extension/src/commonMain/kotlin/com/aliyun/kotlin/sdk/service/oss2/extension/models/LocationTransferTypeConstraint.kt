package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores regions in which the destination bucket can be located with TransferType specified.
 */
@Serializable
@SerialName("LocationTransferTypeConstraint")
public data class LocationTransferTypeConstraint(
    /**
     * The container that stores regions in which the destination bucket can be located with the TransferType information.
     */
    @XmlElement("LocationTransferType") public var locationTransferTypes: List<LocationTransferType>? = null
) {
    public companion object {
        public operator fun invoke(builder: LocationTransferTypeConstraint.() -> Unit): LocationTransferTypeConstraint =
            LocationTransferTypeConstraint().apply(builder)
    }
}
