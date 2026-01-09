package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the encryption method of exported inventory lists.
 */
@Serializable
@SerialName("Encryption")
public data class InventoryEncryption(
    /**
     * The container that stores information about the SSE-OSS encryption method.
     */
    @XmlElement("SSE-OSS") public var sseOss: String? = null,

    /**
     * The container that stores the customer master key (CMK) used for SSE-KMS encryption.
     */
    @XmlElement("SSE-KMS") public var sseKms: SSEKMS? = null
) {
    public companion object {
        public operator fun invoke(builder: InventoryEncryption.() -> Unit): InventoryEncryption =
            InventoryEncryption().apply(builder)
    }
}
