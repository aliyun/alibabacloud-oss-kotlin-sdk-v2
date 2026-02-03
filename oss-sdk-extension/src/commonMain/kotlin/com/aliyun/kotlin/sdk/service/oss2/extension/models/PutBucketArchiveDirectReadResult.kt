package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketArchiveDirectRead operation.
 */
public class PutBucketArchiveDirectReadResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketArchiveDirectReadResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketArchiveDirectReadResult {
            return PutBucketArchiveDirectReadResult(this)
        }
    }
}
