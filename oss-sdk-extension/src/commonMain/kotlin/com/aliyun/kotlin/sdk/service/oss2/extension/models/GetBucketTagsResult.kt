package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketTags operation.
 */
public class GetBucketTagsResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the returned tags of the bucket. If no tags are configured for the bucket, an XML message body is returned in which the Tagging element is empty.
     */
    public val tagging: Tagging?
        get() = innerBody as? Tagging

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketTagsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketTagsResult {
            return GetBucketTagsResult(this)
        }
    }
}
