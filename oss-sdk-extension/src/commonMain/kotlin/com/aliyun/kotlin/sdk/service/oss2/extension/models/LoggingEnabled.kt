package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the information about access log collection.
 */
@Serializable
@SerialName("LoggingEnabled")
public data class LoggingEnabled(
    /**
     * The bucket that stores access logs.
     */
    @XmlElement("TargetBucket") public var targetBucket: String? = null,

    /**
     * The prefix of the log objects. This parameter can be left empty.
     */
    @XmlElement("TargetPrefix") public var targetPrefix: String? = null,

    /**
     * The logging role.
     */
    @XmlElement("LoggingRole") public var loggingRole: String? = null
) {
    public companion object {
        public operator fun invoke(builder: LoggingEnabled.() -> Unit): LoggingEnabled =
            LoggingEnabled().apply(builder)
    }
}
