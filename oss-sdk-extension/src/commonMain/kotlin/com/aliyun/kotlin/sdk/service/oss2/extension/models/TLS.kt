package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores TLS version configurations.
 */
@Serializable
@SerialName("TLS")
public data class TLS(
    /**
     * Specifies whether to enable TLS version management for the bucket.Valid values:
     * *   true
     * *   false
     */
    @XmlElement("Enable") public var enable: Boolean? = null,

    /**
     * The TLS versions.
     */
    @XmlElement("TLSVersion") public var tlsVersions: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: TLS.() -> Unit): TLS =
            TLS().apply(builder)
    }
}
