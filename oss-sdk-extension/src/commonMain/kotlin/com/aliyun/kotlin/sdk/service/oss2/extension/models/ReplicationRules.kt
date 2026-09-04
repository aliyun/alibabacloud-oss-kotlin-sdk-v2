package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ReplicationRules")
@XmlRoot
public data class ReplicationRules(
    /**
     * The ID of data replication rules that you want to delete. You can call the GetBucketReplication operation to obtain the ID.
     */
    @XmlElement("ID") public var id: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationRules.() -> Unit): ReplicationRules =
            ReplicationRules().apply(builder)
    }
}
