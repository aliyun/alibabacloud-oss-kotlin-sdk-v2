package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container for tags.
 */
@Serializable
@SerialName("TagSet")
public data class TagSet(
    /**
     * The tags.
     */
    @XmlElement("Tag") public var tags: List<Tag>? = null
) {
    public companion object {
        public operator fun invoke(builder: TagSet.() -> Unit): TagSet =
            TagSet().apply(builder)
    }
}
