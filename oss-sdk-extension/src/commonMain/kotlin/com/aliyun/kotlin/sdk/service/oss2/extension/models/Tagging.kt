package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the returned tag of the bucket.
 */
@Serializable
@SerialName("Tagging")
@XmlRoot
public data class Tagging(
    /**
     * The tag set of the target object.
     */
    @XmlElement("TagSet") public var tagSet: TagSet? = null
) {
    public companion object {
        public operator fun invoke(builder: Tagging.() -> Unit): Tagging =
            Tagging().apply(builder)
    }
}
