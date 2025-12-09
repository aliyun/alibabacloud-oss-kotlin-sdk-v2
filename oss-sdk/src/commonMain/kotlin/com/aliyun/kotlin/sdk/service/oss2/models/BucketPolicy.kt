package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The log configurations of the bucket.
 */
public class BucketPolicy(builder: Builder) {
    /**
     * The name of the bucket used to store access logs.
     */
    public val logBucket: String? = builder.logBucket

    /**
     * The directory used to store access logs.
     */
    public val logPrefix: String? = builder.logPrefix

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): BucketPolicy =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The name of the bucket used to store access logs.
         */
        public var logBucket: String? = null

        /**
         * The directory used to store access logs.
         */
        public var logPrefix: String? = null

        public fun build(): BucketPolicy {
            return BucketPolicy(this)
        }
    }
}
