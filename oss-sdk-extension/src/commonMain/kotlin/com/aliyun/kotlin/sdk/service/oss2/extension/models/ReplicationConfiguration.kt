package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores data replication configurations.
 */
@Serializable
@SerialName("ReplicationConfiguration")
@XmlRoot
public data class ReplicationConfiguration(
    /**
     * The container that stores the data replication rules.
     */
    @XmlElement("Rule") public var rules: List<ReplicationRule>? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationConfiguration.() -> Unit): ReplicationConfiguration =
            ReplicationConfiguration().apply(builder)
    }
}
