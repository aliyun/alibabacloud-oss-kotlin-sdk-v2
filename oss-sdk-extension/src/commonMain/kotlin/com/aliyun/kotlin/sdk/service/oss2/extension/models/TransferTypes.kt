package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the transfer type.
 */
@Serializable
@SerialName("TransferTypes")
public data class TransferTypes(
    /**
     * The data transfer type that is used to transfer data in data replication. Valid values:
     * *   internal (default): the default data transfer link used in OSS.
     * *   oss_acc: the link in which data transmission is accelerated.
     * You can set TransferType to oss_acc only when you create CRR rules.
     */
    @XmlElement("Type") public var types: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: TransferTypes.() -> Unit): TransferTypes =
            TransferTypes().apply(builder)
    }
}
