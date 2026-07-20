package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketLogging operation.
 */
public class GetBucketLoggingResult(builder: Builder) : ResultModel(builder) {

    /**
     * Indicates the container used to store access logging configuration of a bucket.
     */
    public val bucketLoggingStatus: BucketLoggingStatus?
        get() = innerBody as? BucketLoggingStatus

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketLoggingResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketLoggingResult {
            return GetBucketLoggingResult(this)
        }
    }
}
