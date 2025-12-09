package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the UploadPartCopy operation.
 */
public class UploadPartCopyRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The name of the source bucket.
     */
    public val sourceBucket: String? = builder.sourceBucket

    /**
     * The full path of the source object.
     */
    public val sourceKey: String? = builder.sourceKey

    /**
     * The version id of the source object.
     */
    public val sourceVersionId: String?
        get() = parameters["versionId"]

    /**
     * The range of bytes to copy data from the source object. For example, if you specify bytes to 0 to 9, the system transfers byte 0 to byte 9, a total of 10 bytes.
     * Default value: null
     * - If the x-oss-copy-source-range request header is not specified, the entire source object is copied.
     * - If the x-oss-copy-source-range request header is specified, the response contains the length of the entire object and the range of bytes to be copied for this operation.
     * For example, Content-Range: bytes 0~9/44 indicates that the length of the entire object is 44 bytes. The range of bytes to be copied is byte 0 to byte 9.
     * - If the specified range does not conform to the range conventions, OSS copies the entire object and does not include Content-Range in the response.
     */
    public val copySourceRange: String?
        get() = headers["x-oss-copy-source-range"]

    /**
     * The copy operation condition. If the ETag value of the source object is the same as the ETag value provided by the user, OSS copies data. Otherwise, OSS returns 412 Precondition Failed.
     * Default value: null
     */
    public val copySourceIfMatch: String?
        get() = headers["x-oss-copy-source-if-match"]

    /**
     * The object transfer condition. If the input ETag value does not match the ETag value of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.
     * Default value: null
     */
    public val copySourceIfNoneMatch: String?
        get() = headers["x-oss-copy-source-if-none-match"]

    /**
     * The object transfer condition. If the specified time is the same as or later than the actual modified time of the object, OSS transfers the object normally and returns 200 OK. Otherwise, OSS returns 412 Precondition Failed.
     * Default value: null
     */
    public val copySourceIfUnmodifiedSince: String?
        get() = headers["x-oss-copy-source-if-unmodified-since"]

    /**
     * The object transfer condition. If the specified time is earlier than the actual modified time of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.
     * Default value: null
     * Time format: ddd, dd MMM yyyy HH:mm:ss GMT.
     * Example: Fri, 13 Nov 2015 14:47:53 GMT.
     */
    public val copySourceIfModifiedSince: String?
        get() = headers["x-oss-copy-source-if-modified-since"]

    /**
     * The number of parts.
     */
    public val partNumber: Long?
        get() = parameters["partNumber"]?.toLong()

    /**
     * The ID that identifies the object to which the parts to upload belong.
     */
    public val uploadId: String?
        get() = parameters["uploadId"]

    public inline fun copy(block: Builder.() -> Unit = {}): UploadPartCopyRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): UploadPartCopyRequest =
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
         * The name of the source bucket.
         */
        public var sourceBucket: String? = null

        /**
         * The full path of the source object.
         */
        public var sourceKey: String? = null

        /**
         * The version id of the source object.
         */
        public var sourceVersionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        /**
         * The range of bytes to copy data from the source object. For example, if you specify bytes to 0 to 9, the system transfers byte 0 to byte 9, a total of 10 bytes.
         * Default value: null
         * - If the x-oss-copy-source-range request header is not specified, the entire source object is copied.
         * - If the x-oss-copy-source-range request header is specified, the response contains the length of the entire object and the range of bytes to be copied for this operation.
         * For example, Content-Range: bytes 0~9/44 indicates that the length of the entire object is 44 bytes. The range of bytes to be copied is byte 0 to byte 9.
         * - If the specified range does not conform to the range conventions, OSS copies the entire object and does not include Content-Range in the response.
         */
        public var copySourceRange: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-range"] = it }
            }
            get() = headers["x-oss-copy-source-range"]

        /**
         * The copy operation condition. If the ETag value of the source object is the same as the ETag value provided by the user, OSS copies data. Otherwise, OSS returns 412 Precondition Failed.
         * Default value: null
         */
        public var copySourceIfMatch: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-match"] = it }
            }
            get() = headers["x-oss-copy-source-if-match"]

        /**
         * The object transfer condition. If the input ETag value does not match the ETag value of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.
         * Default value: null
         */
        public var copySourceIfNoneMatch: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-none-match"] = it }
            }
            get() = headers["x-oss-copy-source-if-none-match"]

        /**
         * The object transfer condition. If the specified time is the same as or later than the actual modified time of the object, OSS transfers the object normally and returns 200 OK. Otherwise, OSS returns 412 Precondition Failed.
         * Default value: null
         */
        public var copySourceIfUnmodifiedSince: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-unmodified-since"] = it }
            }
            get() = headers["x-oss-copy-source-if-unmodified-since"]

        /**
         * The object transfer condition. If the specified time is earlier than the actual modified time of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.
         * Default value: null
         * Time format: ddd, dd MMM yyyy HH:mm:ss GMT.
         * Example: Fri, 13 Nov 2015 14:47:53 GMT.
         */
        public var copySourceIfModifiedSince: String?
            set(value) {
                value?.let { this.headers["x-oss-copy-source-if-modified-since"] = it }
            }
            get() = headers["x-oss-copy-source-if-modified-since"]

        /**
         * The number of parts.
         */
        public var partNumber: Long?
            set(value) {
                value?.let { this.parameters["partNumber"] = it.toString() }
            }
            get() = parameters["partNumber"]?.toLong()

        /**
         * The ID that identifies the object to which the parts to upload belong.
         */
        public var uploadId: String?
            set(value) {
                value?.let { this.parameters["uploadId"] = it }
            }
            get() = parameters["uploadId"]

        public fun build(): UploadPartCopyRequest {
            return UploadPartCopyRequest(this)
        }

        public constructor(from: UploadPartCopyRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
