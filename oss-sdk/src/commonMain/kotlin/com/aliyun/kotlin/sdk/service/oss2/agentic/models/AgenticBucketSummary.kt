package com.aliyun.kotlin.sdk.service.oss2.agentic.models

/**
 * The summary of an agentic bucket.
 */
public class AgenticBucketSummary(builder: Builder) {
    /**
     * The name of the bucket.
     */
    public val name: String? = builder.name

    /**
     * The storage class of the bucket.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The data redundancy type of the bucket.
     */
    public val dataRedundancyType: String? = builder.dataRedundancyType

    /**
     * The creation time of the bucket.
     */
    public val createTime: String? = builder.createTime

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AgenticBucketSummary =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var name: String? = null

        public var storageClass: String? = null

        public var dataRedundancyType: String? = null

        public var createTime: String? = null

        public fun build(): AgenticBucketSummary = AgenticBucketSummary(this)
    }
}
