package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The object metadata returned.
 */
public class ObjectSummary(builder: Builder) {
    /**
     * The types of the returned objects.
     * *   Normal: Objects created by using simple upload.
     * *   Multipart: Objects created by using multipart upload.
     * *   Appendable: Objects created by using append upload. You can append content to objects only of the Appendable type.
     */
    public val type: String? = builder.type

    /**
     * The storage class of the object.
     */
    public val storageClass: String? = builder.storageClass

    /**
     * The restoration status of the object.
     */
    public val restoreInfo: String? = builder.restoreInfo

    /**
     * The time when the object was last modified.
     */
    public val lastModified: String? = builder.lastModified

    /**
     * The ETag that is generated when an object is created. ETags are used to identify the content of objects.
     * *   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.
     * *   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.
     * *   The ETag of an object can be used to check whether the object content is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
     */
    public val eTag: String? = builder.eTag

    /**
     * The sizes of the returned objects. Unit: byte.
     */
    public val size: Long? = builder.size

    /**
     * The container for the information about the bucket owner.
     */
    public val owner: Owner? = builder.owner

    /**
     * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public val transitionTime: String? = builder.transitionTime

    /**
     * The key of the object.
     */
    public val key: String? = builder.key

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ObjectSummary =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The types of the returned objects.
         * *   Normal: Objects created by using simple upload.
         * *   Multipart: Objects created by using multipart upload.
         * *   Appendable: Objects created by using append upload. You can append content to objects only of the Appendable type.
         */
        public var type: String? = null

        /**
         * The storage class of the object.
         */
        public var storageClass: String? = null

        /**
         * The restoration status of the object.
         */
        public var restoreInfo: String? = null

        /**
         * The time when the object was last modified.
         */
        public var lastModified: String? = null

        /**
         * The ETag that is generated when an object is created. ETags are used to identify the content of objects.
         * *   For an object that is created by calling the PutObject operation, the ETag value of the object is the MD5 hash of its content.
         * *   For an object that is created by using another method, the ETag value is not the MD5 hash of the object content but a unique value calculated based on a specific rule.
         * *   The ETag of an object can be used to check whether the object content is modified. We recommend that you use the MD5 hash of an object rather than the ETag of it to verify data integrity.
         */
        public var eTag: String? = null

        /**
         * The sizes of the returned objects. Unit: byte.
         */
        public var size: Long? = null

        /**
         * The container for the information about the bucket owner.
         */
        public var owner: Owner? = null

        /**
         * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
         */
        public var transitionTime: String? = null

        /**
         * The key of the object.
         */
        public var key: String? = null

        public fun build(): ObjectSummary {
            return ObjectSummary(this)
        }
    }
}
