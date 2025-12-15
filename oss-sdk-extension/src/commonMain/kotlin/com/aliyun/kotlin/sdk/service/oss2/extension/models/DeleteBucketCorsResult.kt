package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketCors operation.
 */
public class DeleteBucketCorsResult(builder: Builder): ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketCorsResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): DeleteBucketCorsResult {
            return DeleteBucketCorsResult(this)
        }
    }
}
