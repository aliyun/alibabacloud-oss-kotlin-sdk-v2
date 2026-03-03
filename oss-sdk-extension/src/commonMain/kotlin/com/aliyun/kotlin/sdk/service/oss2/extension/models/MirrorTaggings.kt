package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The rules for setting tags when saving files during mirror-based back-to-origin.
 */
@Serializable
@SerialName("MirrorTaggings")
public data class MirrorTaggings(
    /**
     * The rule list for setting tags.
     */
    @XmlElement("Tagging") public var taggings: List<Taggings>? = null
) {
    public companion object {
        public operator fun invoke(builder: MirrorTaggings.() -> Unit): MirrorTaggings =
            MirrorTaggings().apply(builder)
    }
}
