package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the Referer blacklist.
 */
@Serializable
@SerialName("RefererBlacklist")
public data class RefererBlacklist(
    /**
     * The addresses in the Referer blacklist.
     */
    @XmlElement("Referer") public var referrers: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: RefererBlacklist.() -> Unit): RefererBlacklist =
            RefererBlacklist().apply(builder)
    }
}
