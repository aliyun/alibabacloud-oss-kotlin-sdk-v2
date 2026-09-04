package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
 */
@Serializable
@SerialName("PrefixSet")
public data class ReplicationPrefixSet(
    /**
     * The prefix that is used to specify the object that you want to replicate. Only objects whose names contain the specified prefix are replicated to the destination bucket.
     * *   The value of the Prefix parameter can be up to 1,023 characters in length.
     * *   If you specify the Prefix parameter in a data replication rule, OSS synchronizes new data and historical data based on the value of the Prefix parameter.
     */
    @XmlElement("Prefix") public var prefixes: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationPrefixSet.() -> Unit): ReplicationPrefixSet =
            ReplicationPrefixSet().apply(builder)
    }
}
