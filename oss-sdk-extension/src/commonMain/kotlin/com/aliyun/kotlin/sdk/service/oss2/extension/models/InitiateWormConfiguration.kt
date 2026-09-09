package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the root node.
 */
@Serializable
@SerialName("InitiateWormConfiguration")
@XmlRoot
public data class InitiateWormConfiguration(
    /**
     * The number of days for which objects can be retained.
     */
    @XmlElement("RetentionPeriodInDays") public var retentionPeriodInDays: Int? = null
) {
    public companion object {
        public operator fun invoke(builder: InitiateWormConfiguration.() -> Unit): InitiateWormConfiguration =
            InitiateWormConfiguration().apply(builder)
    }
}
