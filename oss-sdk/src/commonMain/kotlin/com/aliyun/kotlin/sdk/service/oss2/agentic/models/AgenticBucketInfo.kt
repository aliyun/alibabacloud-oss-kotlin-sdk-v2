package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.ServerSideEncryptionRule

/**
 * The information of an agentic bucket.
 */
public class AgenticBucketInfo(builder: Builder) {
    /**
     * The name of the bucket.
     */
    public val name: String? = builder.name

    /**
     * The owner of the bucket.
     */
    public val owner: String? = builder.owner

    /**
     * The region in which the bucket is located.
     */
    public val region: String? = builder.region

    /**
     * The storage class of the bucket.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The data redundancy type of the bucket.
     */
    public val dataRedundancyType: String? = builder.dataRedundancyType

    /**
     * The status of the bucket.
     */
    public val status: String? = builder.status

    /**
     * The resource type of the bucket.
     */
    public val bucketResourceType: String? = builder.bucketResourceType

    /**
     * The creation time of the bucket.
     */
    public val createTime: String? = builder.createTime

    /**
     * The access control list (ACL) of the bucket.
     */
    public val acl: String? = builder.acl

    /**
     * The public access block configuration of the bucket.
     */
    public val publicAccessBlock: String? = builder.publicAccessBlock

    /**
     * The server-side encryption rule of the bucket.
     */
    public val serverSideEncryptionRule: ServerSideEncryptionRule? = builder.serverSideEncryptionRule

    /**
     * The versioning state of the bucket.
     */
    public val versioning: String? = builder.versioning

    /**
     * The policy of the bucket.
     */
    public val bucketPolicy: String? = builder.bucketPolicy

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AgenticBucketInfo =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var name: String? = null

        public var owner: String? = null

        public var region: String? = null

        public var storageClass: String? = null

        public var dataRedundancyType: String? = null

        public var status: String? = null

        public var bucketResourceType: String? = null

        public var createTime: String? = null

        public var acl: String? = null

        public var publicAccessBlock: String? = null

        public var serverSideEncryptionRule: ServerSideEncryptionRule? = null

        public var versioning: String? = null

        public var bucketPolicy: String? = null

        public fun build(): AgenticBucketInfo = AgenticBucketInfo(this)
    }
}
