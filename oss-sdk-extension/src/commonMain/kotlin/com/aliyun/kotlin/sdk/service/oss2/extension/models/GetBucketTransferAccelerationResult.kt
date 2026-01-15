package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketTransferAcceleration operation.
 */
public class GetBucketTransferAccelerationResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores the transfer acceleration configurations.
     */
    public val transferAccelerationConfiguration: TransferAccelerationConfiguration?
        get() = innerBody as? TransferAccelerationConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketTransferAccelerationResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketTransferAccelerationResult {
            return GetBucketTransferAccelerationResult(this)
        }
    }
}
