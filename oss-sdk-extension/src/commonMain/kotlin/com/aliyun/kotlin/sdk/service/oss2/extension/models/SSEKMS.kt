package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the customer master key (CMK) used for SSE-KMS encryption.
 */
@Serializable
@SerialName("SSE-KMS")
public class SSEKMS(
    /**
     * The ID of the key that is managed by Key Management Service (KMS).
     */
    @XmlElement("KeyId") public var keyId: String? = null
) {
    public companion object {
        public operator fun invoke(builder: SSEKMS.() -> Unit): SSEKMS =
            SSEKMS().apply(builder)
    }
}
