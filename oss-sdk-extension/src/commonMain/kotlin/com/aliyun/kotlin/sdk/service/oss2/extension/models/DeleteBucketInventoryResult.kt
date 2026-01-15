package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteBucketInventory operation.
 */
public class DeleteBucketInventoryResult(builder: Builder): ResultModel(builder) { 

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketInventoryResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): DeleteBucketInventoryResult {
            return DeleteBucketInventoryResult(this)
        }
    }
}
