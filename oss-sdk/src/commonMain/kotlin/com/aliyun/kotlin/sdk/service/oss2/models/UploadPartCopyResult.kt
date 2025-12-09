package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the UploadPartCopy operation.
 */
public class UploadPartCopyResult(builder: Builder) : ResultModel(builder) {

    /**
     * The version ID of the source object.
     */
    public val copySourceVersionId: String?
        get() = headers["x-oss-copy-source-version-id"]

    /**
     * The container that stores the copy result.
     */
    public val copyPartResult: CopyPartResult?
        get() = innerBody as? CopyPartResult

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): UploadPartCopyResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): UploadPartCopyResult {
            return UploadPartCopyResult(this)
        }
    }
}
