package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the information about the destination bucket.
 */
@Serializable
@SerialName("Destination")
public data class ReplicationDestination(
    /**
     * The destination bucket to which data is replicated.
     */
    @XmlElement("Bucket") public var bucket: String? = null,

    /**
     * The region in which the destination bucket is located.
     */
    @XmlElement("Location") public var location: String? = null,

    /**
     * The link that is used to transfer data during data replication. Valid values:
     * *   internal (default): the default data transfer link used in OSS.
     * *   oss_acc: the transfer acceleration link.
     * You can set TransferType to oss_acc only when you create CRR rules.
     */
    @XmlElement("TransferType") public var transferType: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationDestination.() -> Unit): ReplicationDestination =
            ReplicationDestination().apply(builder)
    }
}
