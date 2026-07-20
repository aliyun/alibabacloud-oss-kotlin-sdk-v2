package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Indicates the container used to store access logging configuration of a bucket.
 */
@Serializable
@SerialName("BucketLoggingStatus")
@XmlRoot
public data class BucketLoggingStatus(
    /**
     * Indicates the container used to store access logging information. This element is returned if it is enabled and is not returned if it is disabled.
     */
    @XmlElement("LoggingEnabled") public var loggingEnabled: LoggingEnabled? = null
) {
    public companion object {
        public operator fun invoke(builder: BucketLoggingStatus.() -> Unit): BucketLoggingStatus =
            BucketLoggingStatus().apply(builder)
    }
}
