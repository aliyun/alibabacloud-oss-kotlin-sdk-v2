package com.aliyun.kotlin.sdk.service.oss2.agentic.models

/**
 * The summary of a bucket space.
 */
public class BucketSpaceSummary(builder: Builder) {
    /**
     * The name of the bucket space.
     */
    public val name: String? = builder.name

    /**
     * The location of the bucket space.
     */
    public val location: String? = builder.location

    /**
     * The creation time of the bucket space.
     */
    public val creationDate: String? = builder.creationDate

    /**
     * The storage class of the bucket space.
     */
    public val storageClass: String? = builder.storageClass

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): BucketSpaceSummary =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var name: String? = null

        public var location: String? = null

        public var creationDate: String? = null

        public var storageClass: String? = null

        public fun build(): BucketSpaceSummary = BucketSpaceSummary(this)
    }
}
