package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketTags operation.
 */
public class PutBucketTagsResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketTagsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketTagsResult {
            return PutBucketTagsResult(this)
        }
    }
}
