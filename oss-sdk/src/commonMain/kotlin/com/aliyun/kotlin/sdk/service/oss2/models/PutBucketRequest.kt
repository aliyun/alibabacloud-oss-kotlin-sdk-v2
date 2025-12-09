package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutBucket operation.
 */
public class PutBucketRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     * The name of a bucket must comply with the following naming conventions:
     * *   The name can contain only lowercase letters, digits, and hyphens (-).
     * *   It must start and end with a lowercase letter or a digit.
     * *   The name must be 3 to 63 characters in length.
     */
    public val bucket: String? = builder.bucket

    /**
     * The access control list (ACL) of the bucket to be created.
     * Valid values:
     * *   public-read-write
     * *   public-read
     * *   private (default)
     * For more information, see [Bucket ACL](~~31843~~).
     */
    public val acl: String?
        get() = headers["x-oss-acl"]

    /**
     * The ID of the resource group.
     * *   If you include the header in the request and specify the ID of the resource group, the bucket that you create belongs to the resource group. If the specified resource group ID is rg-default-id, the bucket that you create belongs to the default resource group.
     * *   If you do not include the header in the request, the bucket that you create belongs to the default resource group.You can obtain the ID of a resource group in the Resource Management console or by calling the ListResourceGroups operation.
     * For more information, see [View basic information of a resource group](~~151181~~) and [ListResourceGroups](~~158855~~).  You cannot configure a resource group for an Anywhere Bucket.
     */
    public val resourceGroupId: String?
        get() = headers["x-oss-resource-group-id"]

    /**
     * 指定Bucket标签，如 k1=v1&k2=v2。
     */
    public val bucketTagging: String?
        get() = headers["x-oss-bucket-tagging"]

    /**
     * The container that stores the request body.
     */
    public var createBucketConfiguration: CreateBucketConfiguration? = builder.createBucketConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         * The name of a bucket must comply with the following naming conventions:
         * *   The name can contain only lowercase letters, digits, and hyphens (-).
         * *   It must start and end with a lowercase letter or a digit.
         * *   The name must be 3 to 63 characters in length.
         */
        public var bucket: String? = null

        /**
         * The access control list (ACL) of the bucket to be created. Valid values:
         * *   public-read-write
         * *   public-read
         * *   private (default)For more information, see [Bucket ACL](~~31843~~).
         */
        public var acl: String?
            set(value) {
                value?.let { this.headers["x-oss-acl"] = it }
            }
            get() = headers["x-oss-acl"]

        /**
         * The ID of the resource group.
         * *   If you include the header in the request and specify the ID of the resource group, the bucket that you create belongs to the resource group. If the specified resource group ID is rg-default-id, the bucket that you create belongs to the default resource group.
         * *   If you do not include the header in the request, the bucket that you create belongs to the default resource group.You can obtain the ID of a resource group in the Resource Management console or by calling the ListResourceGroups operation.
         * For more information, see [View basic information of a resource group](~~151181~~) and [ListResourceGroups](~~158855~~).  You cannot configure a resource group for an Anywhere Bucket.
         */
        public var resourceGroupId: String?
            set(value) {
                value?.let { this.headers["x-oss-resource-group-id"] = it }
            }
            get() = headers["x-oss-resource-group-id"]

        /**
         * 指定Bucket标签，如 k1=v1&k2=v2。
         */
        public var bucketTagging: String?
            set(value) {
                value?.let { this.headers["x-oss-bucket-tagging"] = it }
            }
            get() = headers["x-oss-bucket-tagging"]

        /**
         * The container that stores the request body.
         */
        public var createBucketConfiguration: CreateBucketConfiguration? = null

        public fun build(): PutBucketRequest {
            return PutBucketRequest(this)
        }

        public constructor(from: PutBucketRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.createBucketConfiguration = from.createBucketConfiguration
        }
    }
}
