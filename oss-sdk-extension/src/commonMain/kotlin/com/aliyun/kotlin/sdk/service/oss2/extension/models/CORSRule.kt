package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the CORS rules.Up to 10 CORS rules can be configured for a bucket. The XML message body in a request can be up to 16 KB in size.
 */
@Serializable
@SerialName("CORSRule")
public data class CORSRule(
    
    /**
     * The origins from which cross-origin requests are allowed.
     */
    @XmlElement("AllowedOrigin") public var allowedOrigins: List<String>? = null,
    
    /**
     * The methods that you can use in cross-origin requests.
     */
    @XmlElement("AllowedMethod") public var allowedMethods: List<String>? = null,
    
    /**
     * Specifies whether the headers specified by Access-Control-Request-Headers in the OPTIONS preflight request are allowed. Each header specified by Access-Control-Request-Headers must match the value of an AllowedHeader element.
     * You can use only one asterisk (\*) as the wildcard character.
     */
    @XmlElement("AllowedHeader") public var allowedHeaders: List<String>? = null,
    
    /**
     * The response headers for allowed access requests from applications, such as an XMLHttpRequest object in JavaScript.
     * The asterisk (\*) wildcard character is not supported.
     */
    @XmlElement("ExposeHeader") public var exposeHeaders: List<String>? = null,

    /**
     * The period of time within which the browser can cache the response to an OPTIONS preflight request for the specified resource. Unit: seconds.You can specify only one MaxAgeSeconds element in a CORS rule.
     */
    @XmlElement("MaxAgeSeconds") public var maxAgeSeconds: Long? = null,
) {
    public companion object {
        public operator fun invoke(builder: CORSRule.() -> Unit): CORSRule =
            CORSRule().apply(builder)
    }
}
