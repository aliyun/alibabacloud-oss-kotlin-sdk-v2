package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutObjectTagging operation.
 */
public class PutObjectTaggingRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The version id of the target object.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    /**
     * The request body schema.
     */
    public var tagging: Tagging? = builder.tagging

    public inline fun copy(block: Builder.() -> Unit = {}): PutObjectTaggingRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutObjectTaggingRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The name of the object.
         */
        public var key: String? = null

        /**
         * The version id of the target object.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        /**
         * The request body schema.
         */
        public var tagging: Tagging? = null

        public fun build(): PutObjectTaggingRequest {
            return PutObjectTaggingRequest(this)
        }

        public constructor(from: PutObjectTaggingRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.tagging = from.tagging
        }
    }
}
