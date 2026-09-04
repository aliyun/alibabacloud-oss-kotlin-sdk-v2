package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The container that stores the progress of the data replication task. This parameter is returned only when the data replication task is in the doing state.
 */
@Serializable
@SerialName("Progress")
public data class Progress(
    /**
     * The percentage of the replicated historical data. This parameter is valid only when HistoricalObjectReplication is set to enabled.
     */
    @XmlElement("HistoricalObject") public var historicalObject: String? = null,

    /**
     * The time used to determine whether data is replicated to the destination bucket.
     * Data that is written to the source bucket before the time is replicated to the destination bucket. The value of this parameter is in the GMT format.
     * Example: Thu, 24 Sep 2015 15:39:18 GMT.
     */
    @XmlElement("NewObject") public var newObject: String? = null
) {
    public companion object {
        public operator fun invoke(builder: Progress.() -> Unit): Progress =
            Progress().apply(builder)
    }
}
