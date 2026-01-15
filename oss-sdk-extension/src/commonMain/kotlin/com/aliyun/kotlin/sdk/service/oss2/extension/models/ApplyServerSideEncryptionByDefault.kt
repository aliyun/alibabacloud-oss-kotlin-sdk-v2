package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the default server-side encryption method.
 */
@Serializable
@SerialName("ApplyServerSideEncryptionByDefault")
public data class ApplyServerSideEncryptionByDefault(
    /**
     * The default server-side encryption method. Valid values: KMS, AES256, and SM4. You are charged when you call API operations to encrypt or decrypt data by using CMKs managed by KMS.
     * For more information, see [Billing of KMS](~~52608~~). If the default server-side encryption method is configured for the destination bucket and ReplicaCMKID is configured in the CRR rule:
     * *   If objects in the source bucket are not encrypted, they are encrypted by using the default encryption method of the destination bucket after they are replicated.
     * *   If objects in the source bucket are encrypted by using SSE-KMS or SSE-OSS, they are encrypted by using the same method after they are replicated.For more information, see [Use data replication with server-side encryption](~~177216~~).
     */
    @XmlElement("SSEAlgorithm") public var sSEAlgorithm: String? = null,

    /**
     * The CMK ID that is specified when SSEAlgorithm is set to KMS and a specified CMK is used for encryption. In other cases, leave this parameter empty.
     */
    @XmlElement("KMSMasterKeyID") public var kMSMasterKeyID: String? = null,

    /**
     * The algorithm that is used to encrypt objects. If this parameter is not specified, objects are encrypted by using AES256. This parameter is valid only when SSEAlgorithm is set to KMS.
     * Valid value: SM4.
     */
    @XmlElement("KMSDataEncryption") public var kMSDataEncryption: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ApplyServerSideEncryptionByDefault.() -> Unit): ApplyServerSideEncryptionByDefault =
            ApplyServerSideEncryptionByDefault().apply(builder)
    }
}
