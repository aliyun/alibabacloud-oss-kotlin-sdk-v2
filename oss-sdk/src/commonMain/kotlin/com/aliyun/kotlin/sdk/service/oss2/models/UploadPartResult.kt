package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the UploadPart operation.
 */
public class UploadPartResult(builder: Builder) : ResultModel(builder) {

    /**
     * The MD5 hash of the part that you want to upload.
     */
    public val eTag: String?
        get() = headers["ETag"]

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): UploadPartResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): UploadPartResult {
            return UploadPartResult(this)
        }
    }
}
