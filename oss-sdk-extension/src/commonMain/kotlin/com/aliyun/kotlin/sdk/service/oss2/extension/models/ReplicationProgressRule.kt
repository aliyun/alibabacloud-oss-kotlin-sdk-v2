package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Information about the progress of the data replication task.
 */
@Serializable
@SerialName("Rule")
public data class ReplicationProgressRule(
    /**
     * The container that stores the information about the destination bucket.
     */
    @XmlElement("Destination") public var destination: ReplicationDestination? = null,

    /**
     * The status of the data replication task. Valid values:
     * *   starting: OSS creates a data replication task after a data replication rule is configured.
     * *   doing: The replication rule is effective and the replication task is in progress.
     * *   closing: OSS clears a data replication task after the corresponding data replication rule is deleted.
     */
    @XmlElement("Status") public var status: String? = null,

    /**
     * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket.
     * *   enabled (default): replicates historical data to the destination bucket.
     * *   disabled: ignores historical data and replicates only data uploaded to the source bucket after data replication is enabled for the source bucket.
     */
    @XmlElement("HistoricalObjectReplication") public var historicalObjectReplication: String? = null,

    /**
     * The container that stores the progress of the data replication task. This parameter is returned only when the data replication task is in the doing state.
     */
    @XmlElement("Progress") public var progress: Progress? = null,

    /**
     * The ID of the data replication rule.
     */
    @XmlElement("ID") public var id: String? = null,

    /**
     * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
     */
    @XmlElement("PrefixSet") public var prefixSet: ReplicationPrefixSet? = null,

    /**
     * The operations that are synchronized to the destination bucket.
     * *   ALL: PUT, DELETE, and ABORT operations are synchronized to the destination bucket.
     * *   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
     */
    @XmlElement("Action") public var action: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationProgressRule.() -> Unit): ReplicationProgressRule =
            ReplicationProgressRule().apply(builder)
    }
}
