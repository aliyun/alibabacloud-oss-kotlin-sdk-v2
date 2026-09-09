package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlRoot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The container that stores the information about retention policies of the bucket.
 */
@Serializable
@SerialName("WormConfiguration")
@XmlRoot
public data class WormConfiguration(
    /**
     * The time at which the retention policy will be expired.
     */
    @XmlElement("ExpirationDate") public var expirationDate: String? = null,

    /**
     * The ID of the retention policy.Note If the specified retention policy ID that is used to query the retention policy configurations of the bucket does not exist, OSS returns the 404 error code.
     */
    @XmlElement("WormId") public var wormId: String? = null,

    /**
     * The status of the retention policy. Valid values:- InProgress: indicates that the retention policy is in the InProgress state. By default, a retention policy is in the InProgress state after it is created. The policy remains in this state for 24 hours.- Locked: indicates that the retention policy is in the Locked state.
     */
    @XmlElement("State") public var state: String? = null,

    /**
     * The number of days for which objects can be retained.
     */
    @XmlElement("RetentionPeriodInDays") public var retentionPeriodInDays: Int? = null,

    /**
     * The time at which the retention policy was created.
     */
    @XmlElement("CreationDate") public var creationDate: String? = null
) {
    public companion object {
        public operator fun invoke(builder: WormConfiguration.() -> Unit): WormConfiguration =
            WormConfiguration().apply(builder)
    }
}
