package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that was used to query the information about image styles.
 */
@Serializable
@SerialName("StyleList")
@XmlRoot
public data class StyleList(
    /**
     * The list of styles.
     */
    @XmlElement("Style") public var styles: List<StyleInfo>? = null
) {
    public companion object {
        public operator fun invoke(builder: StyleList.() -> Unit): StyleList =
            StyleList().apply(builder)
    }
}
