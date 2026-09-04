package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The matching condition. If all of the specified conditions are met, the rule is run. A rule is considered matched only when the rule meets the conditions that are specified by all nodes in Condition.
 * This parameter must be specified if RoutingRule is specified.
 */
@Serializable
@SerialName("RoutingRuleCondition")
public data class RoutingRuleCondition(
    /**
     * The rule will only be matched when the request contains the specified Header with the specified value. Up to 10 of these can be specified in the container.
     */
    @XmlElement("IncludeHeader") public var includeHeaders: List<IncludeHeader>? = null,

    /**
     * The prefix of object names. Only objects whose names contain the specified prefix match the rule.
     */
    @XmlElement("KeyPrefixEquals") public var keyPrefixEquals: String? = null,

    /**
     * Only Objects that match this suffix can match this rule.
     */
    @XmlElement("KeySuffixEquals") public var keySuffixEquals: String? = null,

    /**
     * The HTTP status code. The rule is matched only when the specified object is accessed and the specified HTTP status code is returned. If the redirection rule is the mirroring-based back-to-origin rule, the value of this parameter is 404.
     */
    @XmlElement("HttpErrorCodeReturnedEquals") public var httpErrorCodeReturnedEquals: Long? = null

) {
    public companion object {
        public operator fun invoke(builder: RoutingRuleCondition.() -> Unit): RoutingRuleCondition =
            RoutingRuleCondition().apply(builder)
    }
}
