package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The authentication information for the origin server in mirror-based back-to-origin.
 */
@Serializable
@SerialName("MirrorAuth")
public class MirrorAuth(
    /**
     * The authentication type.
     */
    @XmlElement("AuthType") public var authType: String? = null,

    /**
     * The sign region for signature.
     */
    @XmlElement("Region") public var region: String? = null,

    /**
     * The access key id for signature.
     */
    @XmlElement("AccessKeyId") public var accessKeyId: String? = null,

    /**
     * The access key secret for signature.
     */
    @XmlElement("AccessKeySecret") public var accessKeySecret: String? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorAuth.() -> Unit): MirrorAuth =
            MirrorAuth().apply(builder)
    }
}
