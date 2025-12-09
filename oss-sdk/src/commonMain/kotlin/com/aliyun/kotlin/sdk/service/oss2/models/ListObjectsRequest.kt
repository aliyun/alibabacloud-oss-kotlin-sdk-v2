package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListObjects operation.
 */
public class ListObjectsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The character that is used to group objects by name. If you specify delimiter in the request, the response contains CommonPrefixes. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
     */
    public val delimiter: String?
        get() = parameters["delimiter"]

    /**
     * The name of the object after which the GetBucket (ListObjects) operation begins. If this parameter is specified, objects whose names are alphabetically after the value of marker are returned.The objects are returned by page based on marker. The value of marker can be up to 1,024 bytes.If the value of marker does not exist in the list when you perform a conditional query, the GetBucket (ListObjects) operation starts from the object whose name is alphabetically after the value of marker.
     */
    public val marker: String?
        get() = parameters["marker"]

    /**
     * The maximum number of objects that can be returned. If the number of objects to be returned exceeds the value of max-keys specified in the request, NextMarker is included in the returned response. The value of NextMarker is used as the value of marker for the next request.
     * Valid values: 1 to 999.Default value: 100.
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLong()

    /**
     * The prefix that must be contained in names of the returned objects.
     * *   The value of prefix can be up to 1,024 bytes in length.
     * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the object whose names start with this prefix are listed. The objects consist of all recursive objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are listed in CommonPrefixes. Recursive objects and subdirectories in the subdirectories are not listed.
     *
     * For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The encoding format of the content in the response.
     * The value of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the values of Delimiter, Marker, Prefix, NextMarker, and Key contain a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    public inline fun copy(block: Builder.() -> Unit = {}): ListObjectsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The character that is used to group objects by name. If you specify delimiter in the request, the response contains CommonPrefixes. The objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
         */
        public var delimiter: String?
            set(value) {
                value?.let { this.parameters["delimiter"] = it }
            }
            get() = parameters["delimiter"]

        /**
         * The name of the object after which the GetBucket (ListObjects) operation begins. If this parameter is specified, objects whose names are alphabetically after the value of marker are returned.The objects are returned by page based on marker. The value of marker can be up to 1,024 bytes.If the value of marker does not exist in the list when you perform a conditional query, the GetBucket (ListObjects) operation starts from the object whose name is alphabetically after the value of marker.
         */
        public var marker: String?
            set(value) {
                value?.let { this.parameters["marker"] = it }
            }
            get() = parameters["marker"]

        /**
         * The maximum number of objects that can be returned. If the number of objects to be returned exceeds the value of max-keys specified in the request, NextMarker is included in the returned response. The value of NextMarker is used as the value of marker for the next request.
         * Valid values: 1 to 999.Default value: 100.
         */
        public var maxKeys: Long?
            set(value) {
                value?.let { this.parameters["max-keys"] = it.toString() }
            }
            get() = parameters["max-keys"]?.toLong()

        /**
         * The prefix that must be contained in names of the returned objects.
         * *   The value of prefix can be up to 1,024 bytes in length.
         * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the object whose names start with this prefix are listed. The objects consist of all recursive objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are listed in CommonPrefixes. Recursive objects and subdirectories in the subdirectories are not listed.
         *
         * For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
         */
        public var prefix: String?
            set(value) {
                value?.let { this.parameters["prefix"] = it }
            }
            get() = parameters["prefix"]

        /**
         * The encoding format of the content in the response.
         * The value of Delimiter, Marker, Prefix, NextMarker, and Key are UTF-8 encoded. If the values of Delimiter, Marker, Prefix, NextMarker, and Key contain a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        public fun build(): ListObjectsRequest {
            return ListObjectsRequest(this)
        }

        public constructor(from: ListObjectsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
