package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores public access information.
 */
@Serializable
@SerialName("PolicyStatus")
@XmlRoot
public data class PolicyStatus(
    /**
     * Indicates whether the current bucket policy allows public access.true false
     */
    @XmlElement("IsPublic") public var isPublic: Boolean? = null
) {
    public companion object {
        public operator fun invoke(builder: PolicyStatus.() -> Unit): PolicyStatus =
            PolicyStatus().apply(builder)
    }
}
