package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketAccessMonitor operation.
 */
public class GetBucketAccessMonitorResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores access monitor configuration.
     */
    public val accessMonitorConfiguration: AccessMonitorConfiguration?
        get() = innerBody as? AccessMonitorConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketAccessMonitorResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketAccessMonitorResult {
            return GetBucketAccessMonitorResult(this)
        }
    }
}
