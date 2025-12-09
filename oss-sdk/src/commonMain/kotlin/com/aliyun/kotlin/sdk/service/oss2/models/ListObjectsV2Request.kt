package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListObjectsV2 operation.
 */
public class ListObjectsV2Request(builder: Builder) : RequestModel(builder) {

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
     * The maximum number of objects to be returned.Valid values: 1 to 999.Default value: 100.  If the number of returned objects exceeds the value of max-keys, the response contains NextContinuationToken.Use the value of NextContinuationToken as the value of continuation-token in the next request.
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLong()

    /**
     * The prefix that must be contained in names of the returned objects.
     * *   The value of prefix can be up to 1,024 bytes in length.
     * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose names start with this prefix are listed. The objects consist of all objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The encoding format of the returned objects in the response.
     * The values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are UTF-8 encoded. If the value of Delimiter, StartAfter, Prefix, NextContinuationToken, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    /**
     * Specifies whether to include the information about the bucket owner in the response.
     * Valid values:
     * *   true
     * *   false
     */
    public val fetchOwner: Boolean?
        get() = parameters["fetch-owner"]?.toBoolean()

    /**
     * The name of the object after which the list operation begins. If this parameter is specified, objects whose names are alphabetically after the value of start-after are returned.The objects are returned by page based on start-after. The value of start-after can be up to 1,024 bytes in length.If the value of start-after does not exist when you perform a conditional query, the list starts from the object whose name is alphabetically after the value of start-after.
     */
    public val startAfter: String?
        get() = parameters["start-after"]

    /**
     * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListObjectsV2 request.
     */
    public val continuationToken: String?
        get() = parameters["continuation-token"]

    public inline fun copy(block: Builder.() -> Unit = {}): ListObjectsV2Request = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListObjectsV2Request =
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
         * The maximum number of objects to be returned.Valid values: 1 to 999.Default value: 100.
         * If the number of returned objects exceeds the value of max-keys, the response contains NextContinuationToken.Use the value of NextContinuationToken as the value of continuation-token in the next request.
         */
        public var maxKeys: Long?
            set(value) {
                value?.let { this.parameters["max-keys"] = it.toString() }
            }
            get() = parameters["max-keys"]?.toLong()

        /**
         * The prefix that must be contained in names of the returned objects.
         * *   The value of prefix can be up to 1,024 bytes in length.
         * *   If you specify prefix, the names of the returned objects contain the prefix.If you set prefix to a directory name, the objects whose names start with this prefix are listed. The objects consist of all objects and subdirectories in this directory.If you set prefix to a directory name and set delimiter to a forward slash (/), only the objects in the directory are listed. The subdirectories in the directory are returned in CommonPrefixes. Objects and subdirectories in the subdirectories are not listed.For example, a bucket contains the following three objects: fun/test.jpg, fun/movie/001.avi, and fun/movie/007.avi. If prefix is set to fun/, the three objects are returned. If prefix is set to fun/ and delimiter is set to a forward slash (/), fun/test.jpg and fun/movie/ are returned.
         */
        public var prefix: String?
            set(value) {
                value?.let { this.parameters["prefix"] = it }
            }
            get() = parameters["prefix"]

        /**
         * The encoding format of the returned objects in the response.
         * The values of Delimiter, StartAfter, Prefix, NextContinuationToken, and Key are UTF-8 encoded. If the value of Delimiter, StartAfter, Prefix, NextContinuationToken, or Key contains a control character that is not supported by Extensible Markup Language (XML) 1.0, you can specify encoding-type to encode the value in the response.
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        /**
         * Specifies whether to include the information about the bucket owner in the response.
         * Valid values:
         * *   true
         * *   false
         */
        public var fetchOwner: Boolean?
            set(value) {
                value?.let { this.parameters["fetch-owner"] = it.toString() }
            }
            get() = parameters["fetch-owner"]?.toBoolean()

        /**
         * The name of the object after which the list operation begins. If this parameter is specified, objects whose names are alphabetically after the value of start-after are returned.The objects are returned by page based on start-after. The value of start-after can be up to 1,024 bytes in length.If the value of start-after does not exist when you perform a conditional query, the list starts from the object whose name is alphabetically after the value of start-after.
         */
        public var startAfter: String?
            set(value) {
                value?.let { this.parameters["start-after"] = it }
            }
            get() = parameters["start-after"]

        /**
         * The token from which the list operation starts. You can obtain the token from NextContinuationToken in the response of the ListObjectsV2 request.
         */
        public var continuationToken: String?
            set(value) {
                value?.let { this.parameters["continuation-token"] = it }
            }
            get() = parameters["continuation-token"]

        public fun build(): ListObjectsV2Request {
            return ListObjectsV2Request(this)
        }

        public constructor(from: ListObjectsV2Request) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
