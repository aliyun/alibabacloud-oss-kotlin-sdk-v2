package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketCors operation.
 */
public class PutBucketCorsResult(builder: Builder): ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketCorsResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketCorsResult {
            return PutBucketCorsResult(this)
        }
    }
}
