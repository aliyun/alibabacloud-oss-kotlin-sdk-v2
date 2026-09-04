package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that specifies other conditions used to filter the source objects that you want to replicate. Filter conditions can be specified only for source objects encrypted by using SSE-KMS.
 */
@Serializable
@SerialName("SourceSelectionCriteria")
public data class ReplicationSourceSelectionCriteria(
    /**
     * The container that is used to filter the source objects that are encrypted by using SSE-KMS. This parameter must be specified if the SourceSelectionCriteria parameter is specified in the data replication rule.
     */
    @XmlElement("SseKmsEncryptedObjects") public var sseKmsEncryptedObjects: SseKmsEncryptedObjects? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationSourceSelectionCriteria.() -> Unit): ReplicationSourceSelectionCriteria =
            ReplicationSourceSelectionCriteria().apply(builder)
    }
}
