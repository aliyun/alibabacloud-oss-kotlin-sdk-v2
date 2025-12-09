package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetObjectMeta operation.
 */
public class GetObjectMetaResult(builder: Builder) : ResultModel(builder) {

    /**
     * The version ID of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    /**
     * The entity tag (ETag).
     */
    public val eTag: String?
        get() = headers["ETag"]

    /**
     * Size of the body in bytes.
     */
    public val contentLength: Long?
        get() = headers["Content-Length"]?.toLong()

    /**
     * The time when the object was last accessed.
     */
    public val lastAccessTime: String?
        get() = headers["x-oss-last-access-time"]

    /**
     * The time when the returned objects were last modified.
     */
    public val lastModified: String?
        get() = headers["Last-Modified"]

    /**
     * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public val transitionTime: String?
        get() = headers["x-oss-transition-time"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectMetaResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetObjectMetaResult {
            return GetObjectMetaResult(this)
        }
    }
}
