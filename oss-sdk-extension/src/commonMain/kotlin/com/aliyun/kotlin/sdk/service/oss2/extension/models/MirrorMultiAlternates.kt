package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container to store the configuration for multiple origins in mirror-based back-to-origin.
 */
@Serializable
@SerialName("MirrorMultiAlternates")
public data class MirrorMultiAlternates(

    /**
     * The configuration list for multiple origins.
     */
    @XmlElement("MirrorMultiAlternate") public var mirrorMultiAlternates: List<MirrorMultiAlternate>? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorMultiAlternates.() -> Unit): MirrorMultiAlternates =
            MirrorMultiAlternates().apply(builder)
    }
}
