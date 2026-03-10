package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Creates an image style. You can include one or multiple Image Processing (IMG) parameters in an image style.
 */
@Serializable
@SerialName("Style")
@XmlRoot
public data class Style(
    /**
     * The content of the image style. You can include one or multiple IMG parameters in an image style.
     * You can include one IMG parameter in an image style. For example, you can use image/resize,p_50 to resize the image by 50%.
     * You can include multiple IMG parameters in an image style. For example, you can use image/resize,p_63/quality,q_90 to resize the image by 63% and then set the relative quality of the image to 90%.
     */
    @XmlElement("Content") public var content: String? = null
) {
    public companion object {
        public operator fun invoke(builder: Style.() -> Unit): Style =
            Style().apply(builder)
    }
}
