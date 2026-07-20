package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The specified field configurations of real-time logs in a bucket.
 */
@Serializable
@SerialName("UserDefinedLogFieldsConfiguration")
@XmlRoot
public data class UserDefinedLogFieldsConfiguration(
    /**
     * The container that stores the configurations of custom request headers.
     */
    @XmlElement("HeaderSet") public var headerSet: HeaderSet? = null,

    /**
     * The container that stores the configurations of custom URL parameters.
     */
    @XmlElement("ParamSet") public var paramSet: ParamSet? = null
) {
    public companion object {
        public operator fun invoke(builder: UserDefinedLogFieldsConfiguration.() -> Unit): UserDefinedLogFieldsConfiguration =
            UserDefinedLogFieldsConfiguration().apply(builder)
    }
}
