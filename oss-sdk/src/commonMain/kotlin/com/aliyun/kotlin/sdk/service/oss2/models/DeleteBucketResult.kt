package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the DeleteBucket operation.
 */
public class DeleteBucketResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteBucketResult {
            return DeleteBucketResult(this)
        }
    }
}
