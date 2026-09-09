package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketTags operation.
 */
public class PutBucketTagsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The request body schema.
     */
    public var tagging: Tagging? = builder.tagging

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketTagsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketTagsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The request body schema.
         */
        public var tagging: Tagging? = null

        public fun build(): PutBucketTagsRequest {
            return PutBucketTagsRequest(this)
        }

        public constructor(from: PutBucketTagsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.tagging = from.tagging
        }
    }
}
