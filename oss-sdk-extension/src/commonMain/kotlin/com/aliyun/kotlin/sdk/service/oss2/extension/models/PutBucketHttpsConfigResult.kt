package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketHttpsConfig operation.
 */
public class PutBucketHttpsConfigResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketHttpsConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketHttpsConfigResult {
            return PutBucketHttpsConfigResult(this)
        }
    }
}
