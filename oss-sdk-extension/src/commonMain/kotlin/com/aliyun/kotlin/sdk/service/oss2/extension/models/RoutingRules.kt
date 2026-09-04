package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the redirection rules.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
 */
@Serializable
@SerialName("RoutingRules")
public data class RoutingRules(
    /**
     * The specified redirection rule or mirroring-based back-to-origin rule. You can specify up to 20 rules.
     */
    @XmlElement("RoutingRule") public var routingRules: List<RoutingRule>? = null
) {
    public companion object {
        public operator fun invoke(builder: RoutingRules.() -> Unit): RoutingRules =
            RoutingRules().apply(builder)
    }
}
