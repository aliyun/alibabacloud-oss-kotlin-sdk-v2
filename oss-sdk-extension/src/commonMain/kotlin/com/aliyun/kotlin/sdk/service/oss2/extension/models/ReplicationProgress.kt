package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that is used to store the progress of data replication tasks.
 */
@Serializable
@SerialName("ReplicationProgress")
@XmlRoot
public data class ReplicationProgress(
    /**
     * The container that stores the progress of the data replication task corresponding to each data replication rule.
     */
    @XmlElement("Rule") public var rules: List<ReplicationProgressRule>? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationProgress.() -> Unit): ReplicationProgress =
            ReplicationProgress().apply(builder)
    }
}
