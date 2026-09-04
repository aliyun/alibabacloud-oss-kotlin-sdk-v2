package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The configuration list for multiple origins.
 */
@Serializable
@SerialName("MirrorMultiAlternate")
public data class MirrorMultiAlternate(
    /**
     * The distinct number of a specific origin.
     */
    @XmlElement("MirrorMultiAlternateNumber") public var mirrorMultiAlternateNumber: Long? = null,

    /**
     * The URL for a specific origin.
     */
    @XmlElement("MirrorMultiAlternateURL") public var mirrorMultiAlternateURL: String? = null,

    /**
     * The VPC ID for a specific origin.
     */
    @XmlElement("MirrorMultiAlternateVpcId") public var mirrorMultiAlternateVpcId: String? = null,

    /**
     * The region for a specific origin.
     */
    @XmlElement("MirrorMultiAlternateDstRegion") public var mirrorMultiAlternateDstRegion: String? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorMultiAlternate.() -> Unit): MirrorMultiAlternate =
            MirrorMultiAlternate().apply(builder)
    }
}
