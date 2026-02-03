package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketAccessMonitor operation.
 */
public class PutBucketAccessMonitorResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketAccessMonitorResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketAccessMonitorResult {
            return PutBucketAccessMonitorResult(this)
        }
    }
}
