package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The root node for website configuration.
 */
@Serializable
@SerialName("WebsiteConfiguration")
@XmlRoot
public data class WebsiteConfiguration(
    /**
     * The container that stores the default homepage.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
     */
    @XmlElement("IndexDocument") public var indexDocument: IndexDocument? = null,

    /**
     * The container that stores the default 404 page.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
     */
    @XmlElement("ErrorDocument") public var errorDocument: ErrorDocument? = null,

    /**
     * The container that stores the redirection rules.  You must specify at least one of the following containers: IndexDocument, ErrorDocument, and RoutingRules.
     */
    @XmlElement("RoutingRules") public var routingRules: RoutingRules? = null

) {
    public companion object {
        public operator fun invoke(builder: WebsiteConfiguration.() -> Unit): WebsiteConfiguration =
            WebsiteConfiguration().apply(builder)
    }
}
