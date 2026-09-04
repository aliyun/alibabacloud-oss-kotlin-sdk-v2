package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The rule will only be matched when the request contains the specified Header with the specified value. Up to 10 of these can be specified in the container.
 */
@Serializable
@SerialName("IncludeHeader")
public data class IncludeHeader(
    /**
     * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
     */
    @XmlElement("Key") public var key: String? = null,

    /**
     * The rule will only be matched when the request contains this Header and its value is equal to the specified value.
     */
    @XmlElement("Equals") public var equals: String? = null,

    /**
     * The rule will only be matched when the request contains this Header and its value starts with the specified value.
     */
    @XmlElement("StartsWith") public var startsWith: String? = null,

    /**
     * The rule will only be matched when the request contains this Header and its value ends with the specified value.
     */
    @XmlElement("EndsWith") public var endsWith: String? = null
) {
    public companion object {
        public operator fun invoke(builder: IncludeHeader.() -> Unit): IncludeHeader =
            IncludeHeader().apply(builder)
    }
}
