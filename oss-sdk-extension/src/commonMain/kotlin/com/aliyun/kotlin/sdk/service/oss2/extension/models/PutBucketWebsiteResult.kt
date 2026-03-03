package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketWebsite operation.
 */
public class PutBucketWebsiteResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketWebsiteResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketWebsiteResult {
            return PutBucketWebsiteResult(this)
        }
    }
}
