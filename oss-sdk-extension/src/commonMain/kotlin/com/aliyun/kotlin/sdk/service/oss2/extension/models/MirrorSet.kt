package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The headers that are sent to the origin. The specified headers are configured in the data returned by the origin regardless of whether the headers are contained in the request.
 * This parameter takes effect only when the value of RedirectType is Mirror. You can specify up to 10 headers.
 */
@Serializable
@SerialName("Set")
public class MirrorSet(
    /**
     * The value of the header. The value can be up to 1,024 bytes in length and cannot contain `\r\n`. This parameter takes effect only when the value of RedirectType is Mirror.
     * This parameter must be specified if Set is specified.
     */
    @XmlElement("Value") public var value: String? = null,

    /**
     * The key of the header. The key can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). This parameter takes effect only when the value of RedirectType is Mirror.
     * This parameter must be specified if Set is specified.
     */
    @XmlElement("Key") public var key: String? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorSet.() -> Unit): MirrorSet =
            MirrorSet().apply(builder)
    }
}
