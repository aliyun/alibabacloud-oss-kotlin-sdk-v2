package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Lua script config for the routing rule.
 */
@Serializable
@SerialName("RoutingRuleLuaConfig")
public data class RoutingRuleLuaConfig(

    /**
     * The name of the Lua script.
     */
    @XmlElement("Script") public var script: String? = null
) {
    public companion object {
        public operator fun invoke(builder: RoutingRuleLuaConfig.() -> Unit): RoutingRuleLuaConfig =
            RoutingRuleLuaConfig().apply(builder)
    }
}
