package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores delete markers.
 */
public class DeleteMarkerEntry(builder: Builder) {
    /**
     * The time when the object was last modified.
     */
    public val lastModified: String? = builder.lastModified

    /**
     * The container that stores the information about the bucket owner.
     */
    public val owner: Owner? = builder.owner

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The version ID of the object.
     */
    public val versionId: String? = builder.versionId

    /**
     * Indicates whether the version is the current version.
     * Valid values:
     * *   true: The version is the current version.
     * *   false: The version is a previous version.
     */
    public val isLatest: Boolean? = builder.isLatest

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteMarkerEntry =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The time when the object was last modified.
         */
        public var lastModified: String? = null

        /**
         * The container that stores the information about the bucket owner.
         */
        public var owner: Owner? = null

        /**
         * The name of the object.
         */
        public var key: String? = null

        /**
         * The version ID of the object.
         */
        public var versionId: String? = null

        /**
         * Indicates whether the version is the current version.
         * Valid values:
         * *   true: The version is the current version.
         * *   false: The version is a previous version.
         */
        public var isLatest: Boolean? = null

        public fun build(): DeleteMarkerEntry {
            return DeleteMarkerEntry(this)
        }
    }
}
