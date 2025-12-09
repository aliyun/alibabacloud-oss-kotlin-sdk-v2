package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetBucketStat operation.
 */
public class GetBucketStatResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores all information returned for the GetBucketStat request.
     */
    public val bucketStat: BucketStat?
        get() = innerBody as? BucketStat

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketStatResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketStatResult {
            return GetBucketStatResult(this)
        }
    }
}
