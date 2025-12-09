package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetBucketInfo operation.
 */
public class GetBucketInfoResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the information about the bucket.
     */
    public val bucketInfo: BucketInfo?
        get() = innerBody as? BucketInfo

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketInfoResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketInfoResult {
            return GetBucketInfoResult(this)
        }
    }
}
