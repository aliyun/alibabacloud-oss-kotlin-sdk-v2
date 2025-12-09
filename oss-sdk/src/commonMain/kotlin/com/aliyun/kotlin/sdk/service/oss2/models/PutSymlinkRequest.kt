package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutSymlink operation.
 */
public class PutSymlinkRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The target object to which the symbolic link points.
     * The naming conventions for target objects are the same as those for objects.
     * - Similar to ObjectName, TargetObjectName must be URL-encoded.
     * - The target object to which a symbolic link points cannot be a symbolic link.
     */
    public val symlinkTarget: String?
        get() = headers["x-oss-symlink-target"]

    /**
     * The access control list (ACL) of the object. Default value: default.
     * Valid values:
     * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
     * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
     * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
     * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
     * For more information about the ACL, see **[ACL](~~100676~~)**.
     */
    public val objectAcl: String?
        get() = headers["x-oss-object-acl"]

    /**
     * The storage class of the bucket. Default value: Standard.
     * Valid values:
     * - Standard
     * - IA
     * - Archive
     * - ColdArchive
     */
    public val storageClass: String?
        get() = headers["x-oss-storage-class"]

    /**
     * Specifies whether the PutSymlink operation overwrites the object that has the same name as that of the symbolic link you want to create.
     * - If the value of **x-oss-forbid-overwrite** is not specified or set to **false**, existing objects can be overwritten by objects that have the same names.
     * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.  The **x-oss-forbid-overwrite** request header is invalid when versioning is enabled or suspended for the destination bucket. In this case, the object with the same name can be overwritten.
     */
    public val forbidOverwrite: Boolean?
        get() = headers["x-oss-forbid-overwrite"].toBoolean()

    public inline fun copy(block: Builder.() -> Unit = {}): PutSymlinkRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutSymlinkRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object.
         */
        public var key: String? = null

        /**
         * The target object to which the symbolic link points.
         * The naming conventions for target objects are the same as those for objects.
         * - Similar to ObjectName, TargetObjectName must be URL-encoded.
         * - The target object to which a symbolic link points cannot be a symbolic link.
         */
        public var symlinkTarget: String?
            set(value) {
                value?.let { this.headers["x-oss-symlink-target"] = it }
            }
            get() = headers["x-oss-symlink-target"]

        /**
         * The access control list (ACL) of the object. Default value: default.
         * Valid values:
         * - default: The ACL of the object is the same as that of the bucket in which the object is stored.
         * - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object.
         * - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value.
         * - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value.
         * For more information about the ACL, see **[ACL](~~100676~~)**.
         */
        public var objectAcl: String?
            set(value) {
                value?.let { this.headers["x-oss-object-acl"] = it }
            }
            get() = headers["x-oss-object-acl"]

        /**
         * The storage class of the bucket. Default value: Standard.
         * Valid values:
         * - Standard
         * - IA
         * - Archive
         * - ColdArchive
         */
        public var storageClass: String?
            set(value) {
                value?.let { this.headers["x-oss-storage-class"] = it }
            }
            get() = headers["x-oss-storage-class"]

        /**
         * Specifies whether the PutSymlink operation overwrites the object that has the same name as that of the symbolic link you want to create.
         * - If the value of **x-oss-forbid-overwrite** is not specified or set to **false**, existing objects can be overwritten by objects that have the same names.
         * - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support.  The **x-oss-forbid-overwrite** request header is invalid when versioning is enabled or suspended for the destination bucket. In this case, the object with the same name can be overwritten.
         */
        public var forbidOverwrite: Boolean?
            set(value) {
                value?.let { this.headers["x-oss-forbid-overwrite"] = it.toString() }
            }
            get() = headers["x-oss-forbid-overwrite"].toBoolean()

        public fun build(): PutSymlinkRequest {
            return PutSymlinkRequest(this)
        }

        public constructor(from: PutSymlinkRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
