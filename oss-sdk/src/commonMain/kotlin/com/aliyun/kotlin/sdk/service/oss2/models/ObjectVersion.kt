package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the versions of objects, excluding delete markers.
 */
public class ObjectVersion(builder: Builder) {
    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The version ID of the object.
     */
    public val versionId: String? = builder.versionId

    /**
     * The container for the information about the bucket owner.
     */
    public val owner: Owner? = builder.owner

    /**
     * The storage class of the object.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The restoration status of the object version.
     */
    public val restoreInfo: String? = builder.restoreInfo

    /**
     * The time when a version of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public val transitionTime: String? = builder.transitionTime

    /**
     * Indicates whether the version of the object is the current version.
     * Valid values:
     * *   true: The version is the current version.
     * *   false: The version is a previous version.
     */
    public val isLatest: Boolean? = builder.isLatest

    /**
     * The time when the object was last modified.
     */
    public val lastModified: String? = builder.lastModified

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.
     * *   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.
     * *   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.
     * The ETag value of an object can be used only to check whether the content of the object is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
     */
    public val eTag: String? = builder.eTag

    /**
     * The size of the object.
     */
    public val size: Long? = builder.size

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ObjectVersion =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The name of the object.
         */
        public var key: String? = null

        /**
         * The version ID of the object.
         */
        public var versionId: String? = null

        /**
         * The container for the information about the bucket owner.
         */
        public var owner: Owner? = null

        /**
         * The storage class of the object.
         */
        public var storageClass: String? = null

        /**
         * The restoration status of the object version.
         */
        public var restoreInfo: String? = null

        /**
         * The time when a version of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
         */
        public var transitionTime: String? = null

        /**
         * Indicates whether the version of the object is the current version.
         * Valid values:
         * *   true: The version is the current version.
         * *   false: The version is a previous version.
         */
        public var isLatest: Boolean? = null

        /**
         * The time when the object was last modified.
         */
        public var lastModified: String? = null

        /**
         * The ETag that is generated when an object is created. ETags are used to identify the content of objects.
         * *   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.
         * *   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.
         * The ETag value of an object can be used only to check whether the content of the object is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
         */
        public var eTag: String? = null

        /**
         * The size of the object.
         */
        public var size: Long? = null

        public fun build(): ObjectVersion {
            return ObjectVersion(this)
        }
    }
}
