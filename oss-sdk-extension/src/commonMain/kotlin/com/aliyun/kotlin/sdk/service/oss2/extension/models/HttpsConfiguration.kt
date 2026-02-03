package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores Transport Layer Security (TLS) version configurations.
 */
@Serializable
@SerialName("HttpsConfiguration")
@XmlRoot
public data class HttpsConfiguration(
    /**
     * The container that stores TLS version configurations.
     */
    @XmlElement("TLS") public var tls: TLS? = null
) {
    public companion object {
        public operator fun invoke(builder: HttpsConfiguration.() -> Unit): HttpsConfiguration =
            HttpsConfiguration().apply(builder)
    }
}
