package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the information about the bucket.
 */
public class BucketInfo(builder: Builder) {
    /**
     * The container that stores the bucket information.
     */
    public val bucket: Bucket? = builder.bucket

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): BucketInfo =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The container that stores the bucket information.
         */
        public var bucket: Bucket? = null

        public fun build(): BucketInfo {
            return BucketInfo(this)
        }
    }
}
