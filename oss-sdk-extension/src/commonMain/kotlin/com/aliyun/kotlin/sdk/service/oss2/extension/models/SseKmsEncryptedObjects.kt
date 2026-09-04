package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that is used to filter the source objects that are encrypted by using SSE-KMS. This parameter must be specified if the SourceSelectionCriteria parameter is specified in the data replication rule.
 */
@Serializable
@SerialName("SseKmsEncryptedObjects")
public class SseKmsEncryptedObjects(
    /**
     * Specifies whether to replicate objects that are encrypted by using SSE-KMS. Valid values:
     * *   Enabled
     * *   Disabled
     */
    @XmlElement("Status") public var status: String? = null
) {
    public companion object {
        public operator fun invoke(builder: SseKmsEncryptedObjects.() -> Unit): SseKmsEncryptedObjects =
            SseKmsEncryptedObjects().apply(builder)
    }
}
