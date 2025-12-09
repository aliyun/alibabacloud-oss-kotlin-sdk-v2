package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the DeleteObject operation.
 */
public class DeleteObjectResult(builder: Builder) : ResultModel(builder) {

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    /**
     * Specifies whether the object retrieved was (true) or was not (false) a Delete Marker.
     */
    public val deleteMarker: Boolean?
        get() = headers["x-oss-delete-marker"]?.toBoolean()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteObjectResult {
            return DeleteObjectResult(this)
        }
    }
}
