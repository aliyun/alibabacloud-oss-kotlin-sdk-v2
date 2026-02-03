package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the transfer acceleration configurations.
 */
@Serializable
@SerialName("TransferAccelerationConfiguration")
@XmlRoot
public data class TransferAccelerationConfiguration(
    /**
     * Whether the transfer acceleration is enabled for this bucket.
     */
    @XmlElement("Enabled") public var enabled: Boolean? = null
) {
    public companion object {
        public operator fun invoke(builder: TransferAccelerationConfiguration.() -> Unit): TransferAccelerationConfiguration =
            TransferAccelerationConfiguration().apply(builder)
    }
}
