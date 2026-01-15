package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the Referer whitelist.  ****The PutBucketReferer operation overwrites the existing Referer whitelist with the Referer whitelist specified in RefererList.
 * If RefererList is not specified in the request, which specifies that no Referer elements are included, the operation clears the existing Referer whitelist.
 */
@Serializable
@SerialName("RefererList")
public data class RefererList(
    /**
     * The addresses in the Referer whitelist.
     */
    @XmlElement("Referer") public var referrers: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: RefererList.() -> Unit): RefererList =
            RefererList().apply(builder)
    }
}
