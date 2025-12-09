package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListObjectVersions operation.
 */
public class ListObjectVersionsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The character that is used to group objects by name. If you specify prefix and delimiter in the request, the response contains CommonPrefixes. The objects whose name contains the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes. If you specify prefix and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.By default, this parameter is left empty.
     */
    public val delimiter: String?
        get() = parameters["delimiter"]

    /**
     * The name of the object after which the GetBucketVersions (ListObjectVersions) operation begins. If this parameter is specified, objects whose name is alphabetically after the value of key-marker are returned. Use key-marker and version-id-marker in combination. The value of key-marker must be less than 1,024 bytes in length.By default, this parameter is left empty.
     * You must also specify key-marker if you specify version-id-marker.
     */
    public val keyMarker: String?
        get() = parameters["key-marker"]

    /**
     * The version ID of the object specified in key-marker after which the GetBucketVersions (ListObjectVersions) operation begins. The versions are returned from the latest version to the earliest version. If version-id-marker is not specified, the GetBucketVersions (ListObjectVersions) operation starts from the latest version of the object whose name is alphabetically after the value of key-marker by default.By default, this parameter is left empty.
     * Valid values: version IDs.
     */
    public val versionIdMarker: String?
        get() = parameters["version-id-marker"]

    /**
     * The maximum number of objects to be returned. If the number of returned objects exceeds the value of max-keys, the response contains `NextKeyMarker` and `NextVersionIdMarker`. Specify the values of `NextKeyMarker` and `NextVersionIdMarker` as the markers for the next request.
     * Valid values: 1 to 999. Default value: 100.
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLong()

    /**
     * The prefix that the names of returned objects must contain.
     * *   The value of prefix must be less than 1,024 bytes in length.
     * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose name starts with the prefix are listed. The returned objects consist of all objects and subdirectories in the directory.By default, this parameter is left empty.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The encoding type of the content in the response. By default, this parameter is left empty. Set the value to URL.  The values of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the value of Delimiter, Marker, Prefix, NextMarker, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): ListObjectVersionsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectVersionsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The character that is used to group objects by name. If you specify prefix and delimiter in the request, the response contains CommonPrefixes. The objects whose name contains the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes. If you specify prefix and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.By default, this parameter is left empty.
         */
        public var delimiter: String?
            set(value) {
                value?.let { this.parameters["delimiter"] = it }
            }
            get() = parameters["delimiter"]

        /**
         * The name of the object after which the GetBucketVersions (ListObjectVersions) operation begins. If this parameter is specified, objects whose name is alphabetically after the value of key-marker are returned. Use key-marker and version-id-marker in combination. The value of key-marker must be less than 1,024 bytes in length.By default, this parameter is left empty.  You must also specify key-marker if you specify version-id-marker.
         */
        public var keyMarker: String?
            set(value) {
                value?.let { this.parameters["key-marker"] = it }
            }
            get() = parameters["key-marker"]

        /**
         * The version ID of the object specified in key-marker after which the GetBucketVersions (ListObjectVersions) operation begins. The versions are returned from the latest version to the earliest version. If version-id-marker is not specified, the GetBucketVersions (ListObjectVersions) operation starts from the latest version of the object whose name is alphabetically after the value of key-marker by default.By default, this parameter is left empty.Valid values: version IDs.
         */
        public var versionIdMarker: String?
            set(value) {
                value?.let { this.parameters["version-id-marker"] = it }
            }
            get() = parameters["version-id-marker"]

        /**
         * The maximum number of objects to be returned. If the number of returned objects exceeds the value of max-keys, the response contains `NextKeyMarker` and `NextVersionIdMarker`. Specify the values of `NextKeyMarker` and `NextVersionIdMarker` as the markers for the next request.
         * Valid values: 1 to 999. Default value: 100.
         */
        public var maxKeys: Long?
            set(value) {
                value?.let { this.parameters["max-keys"] = it.toString() }
            }
            get() = parameters["max-keys"]?.toLong()

        /**
         * The prefix that the names of returned objects must contain.
         * *   The value of prefix must be less than 1,024 bytes in length.
         * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose name starts with the prefix are listed. The returned objects consist of all objects and subdirectories in the directory.By default, this parameter is left empty.
         */
        public var prefix: String?
            set(value) {
                value?.let { this.parameters["prefix"] = it }
            }
            get() = parameters["prefix"]

        /**
         * The encoding type of the content in the response. By default, this parameter is left empty. Set the value to URL.
         * The values of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the value of Delimiter, Marker, Prefix, NextMarker, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        public fun build(): ListObjectVersionsRequest {
            return ListObjectVersionsRequest(this)
        }

        public constructor(from: ListObjectVersionsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
