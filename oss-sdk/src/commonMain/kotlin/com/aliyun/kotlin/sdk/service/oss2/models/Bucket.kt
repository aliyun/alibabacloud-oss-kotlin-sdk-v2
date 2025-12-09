package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the bucket information.
 */
public class Bucket(builder: Builder) {
    /**
     * Whether the bucket has been configured to block public access.
     */
    public val blockPublicAccess: Boolean? = builder.blockPublicAccess

    /**
     * Indicates whether cross-region replication (CRR) is enabled for the bucket.
     * Valid values:
     * *   Enabled
     * *   Disabled
     */
    public val crossRegionReplication: String? = builder.crossRegionReplication

    /**
     * The internal endpoint of the bucket.
     */
    public val intranetEndpoint: String? = builder.intranetEndpoint

    /**
     * The region in which the bucket is located.
     */
    public val location: String? = builder.location

    /**
     * The versioning status of the bucket.
     */
    public val versioning: String? = builder.versioning

    /**
     * The server-side encryption configurations of the bucket.
     */
    public val serverSideEncryptionRule: ServerSideEncryptionRule? = builder.serverSideEncryptionRule

    /**
     * Indicates whether access tracking is enabled for the bucket.
     * Valid values:
     * *   Enabled
     * *   Disabled
     */
    public val accessMonitor: String? = builder.accessMonitor

    /**
     * The time when the bucket is created.
     */
    public val creationDate: String? = builder.creationDate

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public val resourceGroupId: String? = builder.resourceGroupId

    /**
     * Indicates whether transfer acceleration is enabled for the bucket.
     * Valid values:
     * *   Enabled
     * *   Disabled
     */
    public val transferAcceleration: String? = builder.transferAcceleration

    /**
     * The ACL of the bucket.
     */
    public val accessControlList: AccessControlList? = builder.accessControlList

    /**
     * The storage class of the bucket.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The owner of the bucket.
     */
    public val owner: Owner? = builder.owner

    /**
     * The log configurations of the bucket.
     */
    public val bucketPolicy: BucketPolicy? = builder.bucketPolicy

    /**
     * Bucket description.
     */
    public val comment: String? = builder.comment

    /**
     * The redundancy type of the bucket.
     */
    public val dataRedundancyType: String? = builder.dataRedundancyType

    /**
     * The public endpoint of the bucket.
     */
    public val extranetEndpoint: String? = builder.extranetEndpoint

    /**
     * The name of the bucket.
     */
    public val name: String? = builder.name

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Bucket =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * Whether the bucket has been configured to block public access.
         */
        public var blockPublicAccess: Boolean? = null

        /**
         * Indicates whether cross-region replication (CRR) is enabled for the bucket.
         * Valid values:
         * *   Enabled
         * *   Disabled
         */
        public var crossRegionReplication: String? = null

        /**
         * The internal endpoint of the bucket.
         */
        public var intranetEndpoint: String? = null

        /**
         * The region in which the bucket is located.
         */
        public var location: String? = null

        /**
         * The versioning status of the bucket.
         */
        public var versioning: String? = null

        /**
         * The server-side encryption configurations of the bucket.
         */
        public var serverSideEncryptionRule: ServerSideEncryptionRule? = null

        /**
         * Indicates whether access tracking is enabled for the bucket.
         * Valid values:
         * *   Enabled
         * *   Disabled
         */
        public var accessMonitor: String? = null

        /**
         * The time when the bucket is created.
         */
        public var creationDate: String? = null

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public var resourceGroupId: String? = null

        /**
         * Indicates whether transfer acceleration is enabled for the bucket.
         * Valid values:
         * *   Enabled
         * *   Disabled
         */
        public var transferAcceleration: String? = null

        /**
         * The ACL of the bucket.
         */
        public var accessControlList: AccessControlList? = null

        /**
         * The storage class of the bucket.
         */
        public var storageClass: String? = null

        /**
         * The owner of the bucket.
         */
        public var owner: Owner? = null

        /**
         * The log configurations of the bucket.
         */
        public var bucketPolicy: BucketPolicy? = null

        /**
         * Bucket description.
         */
        public var comment: String? = null

        /**
         * The redundancy type of the bucket.
         */
        public var dataRedundancyType: String? = null

        /**
         * The public endpoint of the bucket.
         */
        public var extranetEndpoint: String? = null

        /**
         * The name of the bucket.
         */
        public var name: String? = null

        public fun build(): Bucket {
            return Bucket(this)
        }
    }
}
