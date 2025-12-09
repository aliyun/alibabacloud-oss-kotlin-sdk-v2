package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutBucketAcl operation.
 */
public class PutBucketAclRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The ACL that you want to configure or modify for the bucket.
     * The x-oss-acl header is included in PutBucketAcl requests to configure or modify the ACL of the bucket. If this header is not included, the ACL configurations do not take effect.
     * Valid values:
     * *   public-read-write: All users can read and write objects in the bucket. Exercise caution when you set the value to public-read-write.
     * *   public-read: Only the owner and authorized users of the bucket can read and write objects in the bucket. Other users can only read objects in the bucket. Exercise caution when you set the value to public-read.
     * *   private: Only the owner and authorized users of this bucket can read and write objects in the bucket. Other users cannot access objects in the bucket.
     */
    public val acl: String?
        get() = headers["x-oss-acl"]

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketAclRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketAclRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The ACL that you want to configure or modify for the bucket.
         * The x-oss-acl header is included in PutBucketAcl requests to configure or modify the ACL of the bucket. If this header is not included, the ACL configurations do not take effect.
         * Valid values:
         * *   public-read-write: All users can read and write objects in the bucket. Exercise caution when you set the value to public-read-write.
         * *   public-read: Only the owner and authorized users of the bucket can read and write objects in the bucket. Other users can only read objects in the bucket. Exercise caution when you set the value to public-read.
         * *   private: Only the owner and authorized users of this bucket can read and write objects in the bucket. Other users cannot access objects in the bucket.
         */
        public var acl: String?
            set(value) {
                value?.let { this.headers["x-oss-acl"] = it }
            }
            get() = headers["x-oss-acl"]

        public fun build(): PutBucketAclRequest {
            return PutBucketAclRequest(this)
        }

        public constructor(from: PutBucketAclRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
