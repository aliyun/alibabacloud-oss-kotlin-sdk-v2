package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container used to store the tag that you want to configure.
 */
@Serializable
@SerialName("Tag")
public data class Tag(
    /**
     * The key of a tag.
     * *   A tag key can be up to 64 bytes in length.
     * *   A tag key cannot start with `http://`, `https://`, or `Aliyun`.
     * *   A tag key must be UTF-8 encoded.
     * *   A tag key cannot be left empty.
     */
    @XmlElement("Key") public var key: String? = null,

    /**
     * The value of the tag that you want to add or modify.
     * *   A tag value can be up to 128 bytes in length.
     * *   A tag value must be UTF-8 encoded.
     * *   The tag value can be left empty.
     */
    @XmlElement("Value") public var value: String? = null
) {
    public companion object {
        public operator fun invoke(builder: Tag.() -> Unit): Tag =
            Tag().apply(builder)
    }
}
