package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the configurations of custom URL parameters.
 */
@Serializable
@SerialName("ParamSet")
public data class ParamSet(
    /**
     * The list of the custom URL parameters.
     */
    @XmlElement("parameter") public var parameters: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: ParamSet.() -> Unit): ParamSet =
            ParamSet().apply(builder)
    }
}
