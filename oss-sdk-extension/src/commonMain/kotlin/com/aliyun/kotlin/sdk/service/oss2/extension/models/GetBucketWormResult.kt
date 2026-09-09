package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketWorm operation.
 */
public class GetBucketWormResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the information about retention policies of the bucket.
     */
    public val wormConfiguration: WormConfiguration?
        get() = innerBody as? WormConfiguration

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketWormResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketWormResult {
            return GetBucketWormResult(this)
        }
    }
}
