package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The headers contained in the response that is returned when you use mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
 */
@Serializable
@SerialName("MirrorHeaders")
public class MirrorHeaders(
    /**
     * The headers that are sent to the origin. The specified headers are configured in the data returned by the origin regardless of whether the headers are contained in the request.
     * This parameter takes effect only when the value of RedirectType is Mirror. You can specify up to 10 headers.
     */
    @XmlElement("Set") public var sets: List<MirrorSet>? = null,

    /**
     * Specifies whether to pass through all request headers other than the following headers to the origin. This parameter takes effect only when the value of RedirectType is Mirror.
     * *   Headers such as content-length, authorization2, authorization, range, and date
     * *   Headers that start with oss-, x-oss-, and x-drs-Default value: false.
     * Valid values:
     * *   true
     * *   false
     */
    @XmlElement("PassAll") public var passAll: Boolean? = null,

    /**
     * The headers to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each specified header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-). You can specify up to 10 headers.
     */
    @XmlElement("Pass") public var passes: List<String>? = null,

    /**
     * The headers that are not allowed to pass through to the origin. This parameter takes effect only when the value of RedirectType is Mirror. Each header can be up to 1,024 bytes in length and can contain only letters, digits, and hyphens (-).
     * You can specify up to 10 headers. This parameter is used together with PassAll.
     */
    @XmlElement("Remove") public var removes: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorHeaders.() -> Unit): MirrorHeaders =
            MirrorHeaders().apply(builder)
    }
}
