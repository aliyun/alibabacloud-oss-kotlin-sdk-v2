package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.CopyObjectResultXml

/**
 * The result for the CopyObject operation.
 */
public class CopyObjectResult(builder: Builder) : ResultModel(builder) {

    /**
     * Version of the object.
     */
    public val versionId: String?
        get() = headers["x-oss-version-id"]

    /**
     * The version ID of the source object.
     */
    public val copySourceVersionId: String?
        get() = headers["x-oss-copy-source-version-id"]

    /**
     * The ETag value of the destination object.
     */
    public val eTag: String?
        get() = (innerBody as? CopyObjectResultXml)?.eTag

    /**
     * The time when the destination object was last modified.
     */
    public val lastModified: String?
        get() = (innerBody as? CopyObjectResultXml)?.lastModified

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CopyObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): CopyObjectResult {
            return CopyObjectResult(this)
        }
    }
}
