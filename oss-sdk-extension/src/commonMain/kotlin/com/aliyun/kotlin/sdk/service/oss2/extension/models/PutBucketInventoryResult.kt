package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutBucketInventory operation.
 */
public class PutBucketInventoryResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketInventoryResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): PutBucketInventoryResult {
            return PutBucketInventoryResult(this)
        }
    }
}
