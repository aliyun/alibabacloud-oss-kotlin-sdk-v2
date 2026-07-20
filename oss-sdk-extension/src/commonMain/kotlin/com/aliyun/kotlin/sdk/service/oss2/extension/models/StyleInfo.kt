package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores style information.
 */
@Serializable
@SerialName("Style")
@XmlRoot
public data class StyleInfo(
    /**
     * The style name.
     */
    @XmlElement("Name") public var name: String? = null,

    /**
     * The content of the style.
     */
    @XmlElement("Content") public var content: String? = null,

    /**
     * The time when the style was created.
     */
    @XmlElement("CreateTime") public var createTime: String? = null,

    /**
     * The time when the style was last modified.
     */
    @XmlElement("LastModifyTime") public var lastModifyTime: String? = null,

    /**
     * The category of this style. Valid values: image, document, video.
     */
    @XmlElement("Category") public var category: String? = null
) {
    public companion object {
        public operator fun invoke(builder: StyleInfo.() -> Unit): StyleInfo =
            StyleInfo().apply(builder)
    }
}
