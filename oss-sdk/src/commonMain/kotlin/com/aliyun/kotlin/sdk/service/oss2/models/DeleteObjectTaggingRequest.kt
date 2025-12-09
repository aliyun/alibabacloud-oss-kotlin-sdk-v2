package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the DeleteObjectTagging operation.
 */
public class DeleteObjectTaggingRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The version ID of the object that you want to delete.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): DeleteObjectTaggingRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteObjectTaggingRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object.
         */
        public var key: String? = null

        /**
         * The version ID of the object that you want to delete.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        public fun build(): DeleteObjectTaggingRequest {
            return DeleteObjectTaggingRequest(this)
        }

        public constructor(from: DeleteObjectTaggingRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
