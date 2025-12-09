package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutObjectAcl operation.
 */
public class PutObjectAclRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The access control list (ACL) of the object.
     */
    public val objectAcl: String?
        get() = headers["x-oss-object-acl"]

    /**
     * The version id of the object.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    public inline fun copy(block: Builder.() -> Unit = {}): PutObjectAclRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutObjectAclRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The name of the object.
         */
        public var key: String? = null

        /**
         * The access control list (ACL) of the object.
         */
        public var objectAcl: String?
            set(value) {
                value?.let { this.headers["x-oss-object-acl"] = it }
            }
            get() = headers["x-oss-object-acl"]

        /**
         * The version id of the object.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        public fun build(): PutObjectAclRequest {
            return PutObjectAclRequest(this)
        }

        public constructor(from: PutObjectAclRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
