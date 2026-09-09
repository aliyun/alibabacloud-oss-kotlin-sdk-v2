package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the CompleteBucketWorm operation.
 */
public class CompleteBucketWormResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CompleteBucketWormResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): CompleteBucketWormResult {
            return CompleteBucketWormResult(this)
        }
    }
}
