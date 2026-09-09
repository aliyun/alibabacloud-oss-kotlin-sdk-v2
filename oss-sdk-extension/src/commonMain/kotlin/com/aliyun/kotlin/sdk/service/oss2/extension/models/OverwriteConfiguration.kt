package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Do not overwrite the written root node.
 */
@Serializable
@SerialName("OverwriteConfiguration")
@XmlRoot
public data class OverwriteConfiguration(

    /**
     * Do not overwrite writing items. A single bucket can support configuring up to 100 rules.
     */
    @XmlElement("Rule") public var rules: List<OverwriteRule>? = null,

) {
    public companion object {
        public operator fun invoke(builder: OverwriteConfiguration.() -> Unit): OverwriteConfiguration =
            OverwriteConfiguration().apply(builder)
    }
}
