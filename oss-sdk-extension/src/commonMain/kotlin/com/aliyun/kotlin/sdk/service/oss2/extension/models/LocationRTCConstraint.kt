package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores regions in which the RTC can be enabled.
 */
@Serializable
@SerialName("LocationRTCConstraint")
public data class LocationRTCConstraint(
    /**
     * The regions where RTC is supported.
     */
    @XmlElement("Location") public var locations: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: LocationRTCConstraint.() -> Unit): LocationRTCConstraint =
            LocationRTCConstraint().apply(builder)
    }
}
