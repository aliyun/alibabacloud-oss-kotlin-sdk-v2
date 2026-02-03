package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores server-side encryption rules.
 */
@Serializable
@SerialName("ServerSideEncryptionRule")
@XmlRoot
public data class ServerSideEncryptionRule(
    /**
     * The container that stores the default server-side encryption method.
     */
    @XmlElement("ApplyServerSideEncryptionByDefault") public var applyServerSideEncryptionByDefault: ApplyServerSideEncryptionByDefault? = null
) {
    public companion object {
        public operator fun invoke(builder: ServerSideEncryptionRule.() -> Unit): ServerSideEncryptionRule =
            ServerSideEncryptionRule().apply(builder)
    }
}
