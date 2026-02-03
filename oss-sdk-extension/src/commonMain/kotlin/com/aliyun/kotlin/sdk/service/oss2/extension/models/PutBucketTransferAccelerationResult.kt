package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketTransferAcceleration operation.
 */
public class PutBucketTransferAccelerationResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketTransferAccelerationResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketTransferAccelerationResult {
            return PutBucketTransferAccelerationResult(this)
        }
    }
}
