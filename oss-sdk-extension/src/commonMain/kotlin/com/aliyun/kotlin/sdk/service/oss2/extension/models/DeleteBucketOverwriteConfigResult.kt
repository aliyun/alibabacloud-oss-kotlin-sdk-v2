package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketCors operation.
 */
public class DeleteBucketOverwriteConfigResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketOverwriteConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteBucketOverwriteConfigResult {
            return DeleteBucketOverwriteConfigResult(this)
        }
    }
}
