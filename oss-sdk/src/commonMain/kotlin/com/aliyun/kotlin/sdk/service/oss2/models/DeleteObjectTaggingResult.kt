package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the DeleteObjectTagging operation.
 */
public class DeleteObjectTaggingResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteObjectTaggingResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteObjectTaggingResult {
            return DeleteObjectTaggingResult(this)
        }
    }
}
