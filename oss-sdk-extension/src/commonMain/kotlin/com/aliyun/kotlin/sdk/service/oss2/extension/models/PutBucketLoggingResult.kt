package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketLogging operation.
 */
public class PutBucketLoggingResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketLoggingResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketLoggingResult {
            return PutBucketLoggingResult(this)
        }
    }
}
