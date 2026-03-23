package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketRtc operation.
 */
public class PutBucketRtcResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRtcResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketRtcResult {
            return PutBucketRtcResult(this)
        }
    }
}
