package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutBucket operation.
 */
public class PutBucketResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketResult {
            return PutBucketResult(this)
        }
    }
}
