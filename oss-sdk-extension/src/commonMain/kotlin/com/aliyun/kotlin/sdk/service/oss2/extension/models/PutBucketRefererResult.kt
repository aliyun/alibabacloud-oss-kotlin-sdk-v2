package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketReferer operation.
 */
public class PutBucketRefererResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRefererResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketRefererResult {
            return PutBucketRefererResult(this)
        }
    }
}
