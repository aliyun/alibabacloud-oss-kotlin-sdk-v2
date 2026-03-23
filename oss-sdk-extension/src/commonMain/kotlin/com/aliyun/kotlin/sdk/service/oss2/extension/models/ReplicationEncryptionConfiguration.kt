package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Encryption configuration for the replication rule.
 */
@Serializable
@SerialName("EncryptionConfiguration")
public class ReplicationEncryptionConfiguration(
    /**
     * The KMS key ID used for replication.
     */
    @XmlElement("ReplicaKmsKeyID") public var replicaKmsKeyID: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReplicationEncryptionConfiguration.() -> Unit): ReplicationEncryptionConfiguration =
            ReplicationEncryptionConfiguration().apply(builder)
    }
}
