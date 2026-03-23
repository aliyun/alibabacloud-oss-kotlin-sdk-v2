package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The data replication rule configuration.
 */
@Serializable
@SerialName("ReplicationRule")
public data class ReplicationRule(
    /**
     * The container that stores the status of the RTC feature.
     */
    @XmlElement("RTC") public var rtc: RTC? = null,

    /**
     * The container that stores prefixes. You can specify up to 10 prefixes in each data replication rule.
     */
    @XmlElement("PrefixSet") public var prefixSet: ReplicationPrefixSet? = null,

    /**
     * The container that stores the information about the destination bucket.
     */
    @XmlElement("Destination") public var destination: ReplicationDestination? = null,

    /**
     * Specifies whether to replicate historical data that exists before data replication is enabled from the source bucket to the destination bucket. Valid values:*   enabled (default): replicates historical data to the destination bucket.*   disabled: does not replicate historical data to the destination bucket. Only data uploaded to the source bucket after data replication is enabled for the source bucket is replicated.
     */
    @XmlElement("HistoricalObjectReplication") public var historicalObjectReplication: String? = null,

    /**
     * The role that you want to authorize OSS to use to replicate data. If you want to use SSE-KMS to encrypt the objects that are replicated to the destination bucket, you must specify this parameter.
     */
    @XmlElement("SyncRole") public var syncRole: String? = null,

    /**
     * The container that specifies other conditions used to filter the source objects that you want to replicate. Filter conditions can be specified only for source objects encrypted by using SSE-KMS.
     */
    @XmlElement("SourceSelectionCriteria") public var sourceSelectionCriteria: ReplicationSourceSelectionCriteria? = null,

    /**
     * The encryption configuration for the objects replicated to the destination bucket. If the Status parameter is set to Enabled, you must specify this parameter.
     */
    @XmlElement("EncryptionConfiguration") public var encryptionConfiguration: ReplicationEncryptionConfiguration? = null,

    /**
     * The ID of the rule.
     */
    @XmlElement("ID") public var id: String? = null,

    /**
     * The operations that can be synchronized to the destination bucket. If you configure Action in a data replication rule, OSS synchronizes new data and historical data based on the specified value of Action. You can set Action to one or more of the following operation types. Valid values:*   ALL (default): PUT, DELETE, and ABORT operations are synchronized to the destination bucket.*   PUT: Write operations are synchronized to the destination bucket, including PutObject, PostObject, AppendObject, CopyObject, PutObjectACL, InitiateMultipartUpload, UploadPart, UploadPartCopy, and CompleteMultipartUpload.
     */
    @XmlElement("Action") public var action: String? = null,

    /**
     * The status of the data replication task. Valid values:*   starting: OSS creates a data replication task after a data replication rule is configured.*   doing: The replication rule is effective and the replication task is in progress.*   closing: OSS clears a data replication task after the corresponding data replication rule is deleted.
     */
    @XmlElement("Status") public var status: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationRule.() -> Unit): ReplicationRule =
            ReplicationRule().apply(builder)
    }
}
