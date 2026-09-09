package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the AbortBucketWorm operation.
 */
public class AbortBucketWormResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AbortBucketWormResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): AbortBucketWormResult {
            return AbortBucketWormResult(this)
        }
    }
}
