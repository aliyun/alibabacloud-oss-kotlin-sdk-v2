package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the bucket information.
 */
public class BucketSummary(builder: Builder) {
    /**
     * The public endpoint of the bucket.
     */
    public val extranetEndpoint: String? = builder.extranetEndpoint

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public val resourceGroupId: String? = builder.resourceGroupId

    /**
     * The description of bucket.
     */
    public val comment: String? = builder.comment

    /**
     * The region in which the bucket is located.
     */
    public val location: String? = builder.location

    /**
     * The name of the bucket.
     */
    public val name: String? = builder.name

    /**
     * The storage class of the bucket.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The region id.
     */
    public val region: String? = builder.region

    /**
     * The time when the bucket is created.
     */
    public val creationDate: String? = builder.creationDate

    /**
     * The internal endpoint of the bucket.
     */
    public val intranetEndpoint: String? = builder.intranetEndpoint

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): BucketSummary =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The public endpoint of the bucket.
         */
        public var extranetEndpoint: String? = null

        /**
         * The internal endpoint of the bucket.
         */
        public var intranetEndpoint: String? = null

        /**
         * The region in which the bucket is located.
         */
        public var location: String? = null

        /**
         * The time when the bucket is created.
         */
        public var creationDate: String? = null

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public var resourceGroupId: String? = null

        /**
         * The storage class of the bucket.
         */
        public var storageClass: String? = null

        /**
         * Bucket description.
         */
        public var comment: String? = null

        /**
         * The name of the bucket.
         */
        public var name: String? = null

        /**
         * The region id.
         */
        public var region: String? = null

        public fun build(): BucketSummary {
            return BucketSummary(this)
        }
    }
}
