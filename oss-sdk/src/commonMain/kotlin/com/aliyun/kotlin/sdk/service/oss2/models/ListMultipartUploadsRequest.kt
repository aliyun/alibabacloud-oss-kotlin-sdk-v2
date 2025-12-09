package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListMultipartUploads operation.
 */
public class ListMultipartUploadsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The character used to group objects by name. Objects whose names contain the same string that ranges from the specified prefix to the delimiter that appears for the first time are grouped as a CommonPrefixes element.
     */
    public val delimiter: String?
        get() = parameters["delimiter"]

    /**
     * The maximum number of multipart upload tasks that can be returned for the current request. Default value: 1000. Maximum value: 1000.
     */
    public val maxUploads: Long?
        get() = parameters["max-uploads"]?.toLong()

    /**
     * This parameter is used together with the upload-id-marker parameter to specify the position from which the next list begins.
     * - If the upload-id-marker parameter is not set, Object Storage Service (OSS) returns all multipart upload tasks in which object names are alphabetically after the key-marker value.
     * - If the upload-id-marker parameter is set, the response includes the following tasks:
     *      - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order
     *      - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
     */
    public val keyMarker: String?
        get() = parameters["key-marker"]

    /**
     * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.You can use prefixes to group and manage objects in buckets in the same way you manage a folder in a file system.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The upload ID of the multipart upload task after which the list begins. This parameter is used together with the key-marker parameter.
     * - If the key-marker parameter is not set, OSS ignores the upload-id-marker parameter.
     * - If the key-marker parameter is configured, the query result includes:
     *      - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order
     *      - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
     */
    public val uploadIdMarker: String?
        get() = parameters["upload-id-marker"]

    /**
     * The encoding type of the object name in the response. Values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key can be encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters such as characters with an American Standard Code for Information Interchange (ASCII) value from 0 to 10. You can set the encoding-type parameter to encode values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key in the response.Default value: null
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): ListMultipartUploadsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListMultipartUploadsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The character used to group objects by name. Objects whose names contain the same string that ranges from the specified prefix to the delimiter that appears for the first time are grouped as a CommonPrefixes element.
         */
        public var delimiter: String?
            set(value) {
                value?.let { this.parameters["delimiter"] = it }
            }
            get() = parameters["delimiter"]

        /**
         * The maximum number of multipart upload tasks that can be returned for the current request. Default value: 1000. Maximum value: 1000.
         */
        public var maxUploads: Long?
            set(value) {
                value?.let { this.parameters["max-uploads"] = it.toString() }
            }
            get() = parameters["max-uploads"]?.toLong()

        /**
         * This parameter is used together with the upload-id-marker parameter to specify the position from which the next list begins.
         * - If the upload-id-marker parameter is not set, Object Storage Service (OSS) returns all multipart upload tasks in which object names are alphabetically after the key-marker value.
         * - If the upload-id-marker parameter is set, the response includes the following tasks:
         *      - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order
         *      - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
         */
        public var keyMarker: String?
            set(value) {
                value?.let { this.parameters["key-marker"] = it }
            }
            get() = parameters["key-marker"]

        /**
         * The prefix that the returned object names must contain. If you specify a prefix in the request, the specified prefix is included in the response.You can use prefixes to group and manage objects in buckets in the same way you manage a folder in a file system.
         */
        public var prefix: String?
            set(value) {
                value?.let { this.parameters["prefix"] = it }
            }
            get() = parameters["prefix"]

        /**
         * The upload ID of the multipart upload task after which the list begins. This parameter is used together with the key-marker parameter.
         * - If the key-marker parameter is not set, OSS ignores the upload-id-marker parameter.
         * - If the key-marker parameter is configured, the query result includes:
         *      - Multipart upload tasks in which object names are alphabetically after the key-marker value in alphabetical order
         *      - Multipart upload tasks in which object names are the same as the key-marker parameter value but whose upload IDs are greater than the upload-id-marker parameter value
         */
        public var uploadIdMarker: String?
            set(value) {
                value?.let { this.parameters["upload-id-marker"] = it }
            }
            get() = parameters["upload-id-marker"]

        /**
         * The encoding type of the object name in the response. Values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key can be encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse control characters such as characters with an American Standard Code for Information Interchange (ASCII) value from 0 to 10. You can set the encoding-type parameter to encode values of Delimiter, KeyMarker, Prefix, NextKeyMarker, and Key in the response.Default value: null
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        public fun build(): ListMultipartUploadsRequest {
            return ListMultipartUploadsRequest(this)
        }

        public constructor(from: ListMultipartUploadsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
