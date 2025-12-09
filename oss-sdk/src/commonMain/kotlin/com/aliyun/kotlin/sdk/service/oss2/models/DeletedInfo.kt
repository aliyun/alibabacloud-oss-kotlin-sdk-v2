package com.aliyun.kotlin.sdk.service.oss2.models

public class DeletedInfo(builder: Builder) {

    /**
     * The name of the object that you deleted.
     */
    public val key: String? = builder.key

    /**
     * The version ID of the object that you deleted.
     */
    public val versionId: String? = builder.versionId

    /**
     * Indicates whether the deleted version is a delete marker.
     */
    public val deleteMarker: Boolean? = builder.deleteMarker

    /**
     * The version ID of the delete marker.
     */
    public val deleteMarkerVersionId: String? = builder.deleteMarkerVersionId

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeletedInfo =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The name of the object that you deleted.
         */
        public var key: String? = null

        /**
         * The version ID of the object that you deleted.
         */
        public var versionId: String? = null

        /**
         * Indicates whether the deleted version is a delete marker.
         */
        public var deleteMarker: Boolean? = null

        /**
         * The version ID of the delete marker.
         */
        public var deleteMarkerVersionId: String? = null

        public fun build(): DeletedInfo {
            return DeletedInfo(this)
        }
    }
}
