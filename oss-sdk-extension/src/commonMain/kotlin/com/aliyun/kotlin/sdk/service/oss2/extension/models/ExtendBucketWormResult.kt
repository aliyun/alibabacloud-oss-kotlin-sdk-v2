package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the ExtendBucketWorm operation.
 */
public class ExtendBucketWormResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ExtendBucketWormResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ExtendBucketWormResult {
            return ExtendBucketWormResult(this)
        }
    }
}
