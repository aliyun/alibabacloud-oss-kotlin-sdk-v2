package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the AbortMultipartUpload operation.
 */
public class AbortMultipartUploadResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AbortMultipartUploadResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): AbortMultipartUploadResult {
            return AbortMultipartUploadResult(this)
        }
    }
}
