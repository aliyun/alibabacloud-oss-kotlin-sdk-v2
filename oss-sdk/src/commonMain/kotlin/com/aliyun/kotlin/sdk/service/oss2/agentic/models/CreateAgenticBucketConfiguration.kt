package com.aliyun.kotlin.sdk.service.oss2.agentic.models

/**
 * The configuration for creating an agentic bucket.
 */
public class CreateAgenticBucketConfiguration(builder: Builder) {
    /**
     * The storage class of the bucket.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The data redundancy type of the bucket.
     */
    public val dataRedundancyType: String? = builder.dataRedundancyType

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CreateAgenticBucketConfiguration =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var storageClass: String? = null

        public var dataRedundancyType: String? = null

        public fun build(): CreateAgenticBucketConfiguration = CreateAgenticBucketConfiguration(this)
    }
}
