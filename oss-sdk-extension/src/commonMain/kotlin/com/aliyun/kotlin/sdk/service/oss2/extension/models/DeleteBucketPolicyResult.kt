package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketPolicy operation.
 */
public class DeleteBucketPolicyResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketPolicyResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): DeleteBucketPolicyResult {
            return DeleteBucketPolicyResult(this)
        }
    }
}
