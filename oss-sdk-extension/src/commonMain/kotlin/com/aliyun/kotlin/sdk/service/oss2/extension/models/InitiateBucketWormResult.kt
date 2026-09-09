package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the InitiateBucketWorm operation.
 */
public class InitiateBucketWormResult(builder: Builder) : ResultModel(builder) {

    /**
     * <no value>
     */
    public val wormId: String?
        get() = headers["x-oss-worm-id"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): InitiateBucketWormResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): InitiateBucketWormResult {
            return InitiateBucketWormResult(this)
        }
    }
}
