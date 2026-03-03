package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the configurations of custom request headers.
 */
@Serializable
@SerialName("HeaderSet")
public data class HeaderSet(
    /**
     * The list of the custom request headers.
     */
    @XmlElement("header") public var headers: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: HeaderSet.() -> Unit): HeaderSet =
            HeaderSet().apply(builder)
    }
}
