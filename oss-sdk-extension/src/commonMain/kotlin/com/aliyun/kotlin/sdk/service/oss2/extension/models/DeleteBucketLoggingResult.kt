package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketLogging operation.
 */
public class DeleteBucketLoggingResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketLoggingResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteBucketLoggingResult {
            return DeleteBucketLoggingResult(this)
        }
    }
}
