package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Container to store the rules for setting response headers in mirror-based back-to-origin.
 */
@Serializable
@SerialName("MirrorReturnHeaders")
public data class MirrorReturnHeaders(
    /**
     * The rule list for setting response headers in mirror-based back-to-origin.
     */
    @XmlElement("ReturnHeader") public var returnHeaders: List<ReturnHeader>? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorReturnHeaders.() -> Unit): MirrorReturnHeaders =
            MirrorReturnHeaders().apply(builder)
    }
}
