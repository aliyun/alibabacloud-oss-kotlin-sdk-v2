package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Collection of authorized entities. The usage is similar to that of Bucket Policy's Principal, supporting the input of primary account, sub account, or role.
 * If it is empty or not configured, it indicates that overwriting is not allowed for objects that meet the prefix and suffix conditions.
 */
@Serializable
@SerialName("Principals")
public data class Principals(

    /**
     * Authorized entity. Support input of main account, sub account, or role. If the value is empty, it is an invalid setting.
     */
    @XmlElement("Principal") public var principal: List<String>? = null
) {
    public companion object {
        public operator fun invoke(builder: Principals.() -> Unit): Principals =
            Principals().apply(builder)
    }
}
