package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The rule list for setting tags.
 */
@Serializable
@SerialName("Taggings")
public data class Taggings(
    /**
     * The tag key.
     */
    @XmlElement("Key") public var key: String? = null,

    /**
     * The rule for setting tag value for a specific tag key.
     */
    @XmlElement("Value") public var value: String? = null
) {
    public companion object {
        public operator fun invoke(builder: Taggings.() -> Unit): Taggings =
            Taggings().apply(builder)
    }
}
