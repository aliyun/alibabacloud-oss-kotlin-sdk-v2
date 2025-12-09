package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the GetObjectTagging operation.
 */
public class GetObjectTaggingResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the returned tag of the bucket.
     */
    public val tagging: Tagging?
        get() = innerBody as? Tagging

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetObjectTaggingResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetObjectTaggingResult {
            return GetObjectTaggingResult(this)
        }
    }
}
