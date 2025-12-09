package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the AbortMultipartUpload operation.
 */
public class AbortMultipartUploadRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object that you want to upload.
     */
    public val key: String? = builder.key

    /**
     * The ID of the multipart upload task.
     */
    public val uploadId: String?
        get() = parameters["uploadId"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): AbortMultipartUploadRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AbortMultipartUploadRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object that you want to upload.
         */
        public var key: String? = null

        /**
         * The ID of the multipart upload task.
         */
        public var uploadId: String?
            set(value) {
                value?.let { this.parameters["uploadId"] = it }
            }
            get() = parameters["uploadId"]

        public fun build(): AbortMultipartUploadRequest {
            return AbortMultipartUploadRequest(this)
        }

        public constructor(from: AbortMultipartUploadRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
