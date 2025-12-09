package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the CompleteMultipartUpload operation.
 */
public class CompleteMultipartUploadRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * Specifies whether the object with the same object name is overwritten when you call the CompleteMultipartUpload operation.
     * - If the value of x-oss-forbid-overwrite is not specified or set to false, the existing object can be overwritten by the object that has the same name.
     * - If the value of x-oss-forbid-overwrite is set to true, the existing object cannot be overwritten by the object that has the same name.
     * - The x-oss-forbid-overwrite request header is invalid if versioning is enabled or suspended for the bucket. In this case, the existing object can be overwritten by the object that has the same name when you call the CompleteMultipartUpload operation.
     * - If you specify the x-oss-forbid-overwrite request header, the queries per second (QPS) performance of OSS may be degraded. If you want to configure the x-oss-forbid-overwrite header in a large number of requests (QPS  1,000), submit a ticket.
     */
    public val forbidOverwrite: Boolean?
        get() = headers["x-oss-forbid-overwrite"].toBoolean()

    /**
     * Specifies whether to list all parts that are uploaded by using the current upload ID.Valid value: yes.
     * - If x-oss-complete-all is set to yes in the request, OSS lists all parts that are uploaded by using the current upload ID, sorts the parts by part number, and then performs the CompleteMultipartUpload operation. When OSS performs the CompleteMultipartUpload operation, OSS cannot detect the parts that are not uploaded or currently being uploaded. Before you call the CompleteMultipartUpload operation, make sure that all parts are uploaded.
     * - If x-oss-complete-all is specified in the request, the request body cannot be specified. Otherwise, an error occurs.- If x-oss-complete-all is specified in the request, the format of the response remains unchanged.
     */
    public val completeAll: String?
        get() = headers["x-oss-complete-all"]

    /**
     * The identifier of the multipart upload task.
     */
    public val uploadId: String?
        get() = parameters["uploadId"]

    /**
     * The encoding type of the object name in the response. Only URL encoding is supported.The object name can contain characters that are encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters, such as characters with an ASCII value from 0 to 10. You can configure this parameter to encode the object name in the response.
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    /**
     * The request body schema.
     */
    public var completeMultipartUpload: CompleteMultipartUpload? = builder.completeMultipartUpload

    /**
     * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
     */
    public val callback: String?
        get() = headers["x-oss-callback"]

    /**
     * Configure custom parameters by using the callback-var parameter.
     */
    public val callbackVar: String?
        get() = headers["x-oss-callback-var"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): CompleteMultipartUploadRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CompleteMultipartUploadRequest =
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
         * Specifies whether the object with the same object name is overwritten when you call the CompleteMultipartUpload operation.
         * - If the value of x-oss-forbid-overwrite is not specified or set to false, the existing object can be overwritten by the object that has the same name.
         * - If the value of x-oss-forbid-overwrite is set to true, the existing object cannot be overwritten by the object that has the same name.
         * - The x-oss-forbid-overwrite request header is invalid if versioning is enabled or suspended for the bucket. In this case, the existing object can be overwritten by the object that has the same name when you call the CompleteMultipartUpload operation.
         * - If you specify the x-oss-forbid-overwrite request header, the queries per second (QPS) performance of OSS may be degraded. If you want to configure the x-oss-forbid-overwrite header in a large number of requests (QPS  1,000), submit a ticket.
         */
        public var forbidOverwrite: Boolean?
            set(value) {
                value?.let { this.headers["x-oss-forbid-overwrite"] = it.toString() }
            }
            get() = headers["x-oss-forbid-overwrite"].toBoolean()

        /**
         * Specifies whether to list all parts that are uploaded by using the current upload ID.Valid value: yes.
         * - If x-oss-complete-all is set to yes in the request, OSS lists all parts that are uploaded by using the current upload ID, sorts the parts by part number, and then performs the CompleteMultipartUpload operation. When OSS performs the CompleteMultipartUpload operation, OSS cannot detect the parts that are not uploaded or currently being uploaded. Before you call the CompleteMultipartUpload operation, make sure that all parts are uploaded.
         * - If x-oss-complete-all is specified in the request, the request body cannot be specified. Otherwise, an error occurs.- If x-oss-complete-all is specified in the request, the format of the response remains unchanged.
         */
        public var completeAll: String?
            set(value) {
                value?.let { this.headers["x-oss-complete-all"] = it }
            }
            get() = headers["x-oss-complete-all"]

        /**
         * The identifier of the multipart upload task.
         */
        public var uploadId: String?
            set(value) {
                value?.let { this.parameters["uploadId"] = it }
            }
            get() = parameters["uploadId"]

        /**
         * The encoding type of the object name in the response. Only URL encoding is supported.The object name can contain characters that are encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters, such as characters with an ASCII value from 0 to 10. You can configure this parameter to encode the object name in the response.
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        /**
         * The request body schema.
         */
        public var completeMultipartUpload: CompleteMultipartUpload? = null

        /**
         * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
         */
        public var callback: String?
            set(value) {
                value?.let { this.headers["x-oss-callback"] = it }
            }
            get() = headers["x-oss-callback"]

        /**
         * Configure custom parameters by using the callback-var parameter.
         */
        public var callbackVar: String?
            set(value) {
                value?.let { this.headers["x-oss-callback-var"] = it }
            }
            get() = headers["x-oss-callback-var"]

        public fun build(): CompleteMultipartUploadRequest {
            return CompleteMultipartUploadRequest(this)
        }

        public constructor(from: CompleteMultipartUploadRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.completeMultipartUpload = from.completeMultipartUpload
        }
    }
}
