package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketWebsite operation.
 */
public class DeleteBucketWebsiteResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketWebsiteResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): DeleteBucketWebsiteResult {
            return DeleteBucketWebsiteResult(this)
        }
    }
}
