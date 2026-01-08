package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketPolicy operation.
 */
public class PutBucketPolicyResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketPolicyResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketPolicyResult {
            return PutBucketPolicyResult(this)
        }
    }
}
