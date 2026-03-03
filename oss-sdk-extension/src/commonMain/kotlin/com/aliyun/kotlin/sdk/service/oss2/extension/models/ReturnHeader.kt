package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The rule list for setting response headers in mirror-based back-to-origin.
 */
@Serializable
@SerialName("ReturnHeader")
public class ReturnHeader(
    /**
     * The response header.
     */
    @XmlElement("Key") public var key: String? = null,

    /**
     * The rule for setting response header value for a specific header.
     */
    @XmlElement("Value") public var value: String? = null
) {
    public companion object {
        public operator fun invoke(builder: ReturnHeader.() -> Unit): ReturnHeader =
            ReturnHeader().apply(builder)
    }
}
