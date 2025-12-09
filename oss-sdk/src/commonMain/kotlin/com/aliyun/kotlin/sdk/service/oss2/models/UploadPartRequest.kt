package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The request for the UploadPart operation.
 */
public class UploadPartRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The progress listener for monitoring data transfer during OSS operations.
     */
    public var progressListener: ProgressListener? = builder.progressListener

    /**
     * The number that identifies a part.
     * Valid values: 1 to 10000.The size of a part ranges from 100 KB to 5 GB.
     * In multipart upload, each part except the last part must be larger than or equal to 100 KB in size. When you call the UploadPart operation, the size of each part is not verified because not all parts have been uploaded and OSS does not know which part is the last part. The size of each part is verified only when you call CompleteMultipartUpload.
     */
    public val partNumber: Long?
        get() = parameters["partNumber"]?.toLong()

    /**
     * The ID that identifies the object to which the part that you want to upload belongs.
     */
    public val uploadId: String?
        get() = parameters["uploadId"]

    /**
     * The request body.
     */
    public var body: ByteStream? = builder.body

    public inline fun copy(block: Builder.() -> Unit = {}): UploadPartRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): UploadPartRequest =
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
         * The progress listener for monitoring data transfer during OSS operations.
         */
        public var progressListener: ProgressListener? = null

        /**
         * The number that identifies a part.
         * Valid values: 1 to 10000.The size of a part ranges from 100 KB to 5 GB.
         * In multipart upload, each part except the last part must be larger than or equal to 100 KB in size. When you call the UploadPart operation, the size of each part is not verified because not all parts have been uploaded and OSS does not know which part is the last part. The size of each part is verified only when you call CompleteMultipartUpload.
         */
        public var partNumber: Long?
            set(value) {
                value?.let { this.parameters["partNumber"] = it.toString() }
            }
            get() = parameters["partNumber"]?.toLong()

        /**
         * The ID that identifies the object to which the part that you want to upload belongs.
         */
        public var uploadId: String?
            set(value) {
                value?.let { this.parameters["uploadId"] = it }
            }
            get() = parameters["uploadId"]

        /**
         * The request body.
         */
        public var body: ByteStream? = null

        public fun build(): UploadPartRequest {
            return UploadPartRequest(this)
        }

        public constructor(from: UploadPartRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.body = from.body
        }
    }
}
