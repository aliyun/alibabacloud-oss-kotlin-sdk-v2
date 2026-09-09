package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Do not overwrite writing items. A single bucket can support configuring up to 100 rules.
 */
@Serializable
@SerialName("Rule")
public data class OverwriteRule(
    /**
     * The unique identifier of the rule. If not filled in, a UUID will be randomly generated to fill in;
     * If filled in, it must be a unique value, meaning that the same ID cannot be set in different Rules.
     */
    @XmlElement("ID") public var id: String? = null,

    /**
     * Operation type. Currently, only forbid (prohibit overwrite writing) is supported.
     */
    @XmlElement("Action") public var action: String? = null,

    /**
     * The prefix of the Object name is used to filter the objects that need to be processed.
     * The maximum length is 1023 characters. There can be at most one prefix in a single rule.
     * The prefix and suffix do not support regular expression.
     */
    @XmlElement("Prefix") public var prefix: String? = null,

    /**
     * The suffix of the Object name is used to filter the objects that need to be processed. The maximum length is 1023 characters.
     * There can be at most one Suffix in a single Rule. The prefix and suffix do not support regular expression.
     */
    @XmlElement("Suffix") public var suffix: String? = null,

    /**
     * Collection of authorized entities. The usage is similar to that of Bucket Policy's Principal, supporting the input of primary account, sub account, or role.
     * If it is empty or not configured, it indicates that overwriting is not allowed for objects that meet the prefix and suffix conditions.
     */
    @XmlElement("Principals") public var principals: Principals? = null
) {

    public companion object {
        public operator fun invoke(builder: OverwriteRule.() -> Unit): OverwriteRule =
            OverwriteRule().apply(builder)
    }
}
