package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketEncryption operation.
 */
public class PutBucketEncryptionResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketEncryptionResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketEncryptionResult {
            return PutBucketEncryptionResult(this)
        }
    }
}
