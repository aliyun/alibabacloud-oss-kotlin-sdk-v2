package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketCors operation.
 */
public class PutBucketOverwriteConfigResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketOverwriteConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketOverwriteConfigResult {
            return PutBucketOverwriteConfigResult(this)
        }
    }
}
