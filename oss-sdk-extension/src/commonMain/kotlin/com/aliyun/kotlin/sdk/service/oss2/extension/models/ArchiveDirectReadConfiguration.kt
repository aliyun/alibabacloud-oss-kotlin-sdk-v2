package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the configurations for real-time access of Archive objects.
 */
@Serializable
@SerialName("ArchiveDirectReadConfiguration")
@XmlRoot
public data class ArchiveDirectReadConfiguration(
    /**
     * Specifies whether to enable real-time access of Archive objects for a bucket. Valid values:
     * - true
     * - false
     */
    @XmlElement("Enabled") public var enabled: Boolean? = null
) {
    public companion object {
        public operator fun invoke(builder: ArchiveDirectReadConfiguration.() -> Unit): ArchiveDirectReadConfiguration =
            ArchiveDirectReadConfiguration().apply(builder)
    }
}
