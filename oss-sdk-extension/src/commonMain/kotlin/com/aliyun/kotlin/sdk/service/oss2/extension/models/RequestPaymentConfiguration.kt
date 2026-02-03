package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Indicates the container for the payer.
 */
@Serializable
@SerialName("RequestPaymentConfiguration")
@XmlRoot
public data class RequestPaymentConfiguration(
    /**
     * Indicates who pays the download and request fees.
     */
    @XmlElement("Payer") public var payer: String? = null
) {
    public companion object {
        public operator fun invoke(builder: RequestPaymentConfiguration.() -> Unit): RequestPaymentConfiguration =
            RequestPaymentConfiguration().apply(builder)
    }
}
