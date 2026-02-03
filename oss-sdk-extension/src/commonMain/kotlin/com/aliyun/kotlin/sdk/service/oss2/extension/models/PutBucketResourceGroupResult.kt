package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketResourceGroup operation.
 */
public class PutBucketResourceGroupResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketResourceGroupResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketResourceGroupResult {
            return PutBucketResourceGroupResult(this)
        }
    }
}
