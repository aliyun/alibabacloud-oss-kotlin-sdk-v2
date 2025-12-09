package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the PutBucketVersioning operation.
 */
public class PutBucketVersioningResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketVersioningResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutBucketVersioningResult {
            return PutBucketVersioningResult(this)
        }
    }
}
