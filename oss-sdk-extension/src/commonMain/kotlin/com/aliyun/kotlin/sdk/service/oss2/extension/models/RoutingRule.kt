package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container for the redirection rule or mirroring-based back-to-origin rule. You can specify up to 20 rules.
 */
@Serializable
@SerialName("RoutingRule")
public data class RoutingRule(
    /**
     * The sequence number that is used to match and run the redirection rules. OSS matches redirection rules based on this parameter. If a match succeeds, only the rule is run and the subsequent rules are not run.  This parameter must be specified if RoutingRule is specified.
     */
    @XmlElement("RuleNumber") public var ruleNumber: Long? = null,

    /**
     * The matching condition. If all of the specified conditions are met, the rule is run. A rule is considered matched only when the rule meets the conditions that are specified by all nodes in Condition.  This parameter must be specified if RoutingRule is specified.
     */
    @XmlElement("Condition") public var condition: RoutingRuleCondition? = null,

    /**
     * The operation to perform after the rule is matched.  This parameter must be specified if RoutingRule is specified.
     */
    @XmlElement("Redirect") public var redirect: RoutingRuleRedirect? = null,

    /**
     * The Lua script config of this rule.
     */
    @XmlElement("LuaConfig") public var luaConfig: RoutingRuleLuaConfig? = null

) {
    public companion object {
        public operator fun invoke(builder: RoutingRule.() -> Unit): RoutingRule =
            RoutingRule().apply(builder)
    }
}
