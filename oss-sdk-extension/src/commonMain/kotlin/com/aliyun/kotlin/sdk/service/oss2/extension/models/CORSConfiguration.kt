package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores CORS configuration.
 */
@Serializable
@SerialName("CORSConfiguration")
@XmlRoot
public class CORSConfiguration(
    
    /**
     * The container that stores CORS rules. Up to 10 rules can be configured for a bucket.
     */
    @XmlElement("CORSRule") public var corsRules: List<CORSRule>? = null,
 
    /**
     * Indicates whether the Vary: Origin header was returned.
     * Default value: false.
     * - true: The Vary: Origin header is returned regardless whether the request is a cross-origin request or whether the cross-origin request succeeds.
     * - false: The Vary: Origin header is not returned.
     */
    @XmlElement("ResponseVary") public var responseVary: Boolean? = null

) {
    public companion object {
        public operator fun invoke(builder: CORSConfiguration.() -> Unit): CORSConfiguration =
            CORSConfiguration().apply(builder)
    }
}
