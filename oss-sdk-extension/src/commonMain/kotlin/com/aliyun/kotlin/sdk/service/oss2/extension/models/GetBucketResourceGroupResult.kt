package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketResourceGroup operation.
 */
public class GetBucketResourceGroupResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores the ID of the resource group.
     */
    public val bucketResourceGroupConfiguration: BucketResourceGroupConfiguration?
        get() = innerBody as? BucketResourceGroupConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketResourceGroupResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketResourceGroupResult {
            return GetBucketResourceGroupResult(this)
        }
    }
}
