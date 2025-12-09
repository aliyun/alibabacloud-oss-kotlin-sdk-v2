package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListParts operation.
 */
public class ListPartsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    /**
     * The ID of the multipart upload task.By default, this parameter is left empty.
     */
    public val uploadId: String?
        get() = parameters["uploadId"]

    /**
     * The maximum number of parts that can be returned by OSS.
     * Default value: 1000.
     * Maximum value: 1000.
     */
    public val maxParts: Long?
        get() = parameters["max-parts"]?.toLong()

    /**
     * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.By default, this parameter is left empty.
     */
    public val partNumberMarker: Long?
        get() = parameters["part-number-marker"]?.toLong()

    /**
     * The maximum number of parts that can be returned by OSS.
     * Default value: 1000.
     * Maximum value: 1000.
     */
    public val encodingType: String?
        get() = parameters["encoding-type"]

    public inline fun copy(block: Builder.() -> Unit = {}): ListPartsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListPartsRequest =
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
         * The ID of the multipart upload task.By default, this parameter is left empty.
         */
        public var uploadId: String?
            set(value) {
                value?.let { this.parameters["uploadId"] = it }
            }
            get() = parameters["uploadId"]

        /**
         * The maximum number of parts that can be returned by OSS.
         * Default value: 1000.
         * Maximum value: 1000.
         */
        public var maxParts: Long?
            set(value) {
                value?.let { this.parameters["max-parts"] = it.toString() }
            }
            get() = parameters["max-parts"]?.toLong()

        /**
         * The position from which the list starts. All parts whose part numbers are greater than the value of this parameter are listed.By default, this parameter is left empty.
         */
        public var partNumberMarker: Long?
            set(value) {
                value?.let { this.parameters["part-number-marker"] = it.toString() }
            }
            get() = parameters["part-number-marker"]?.toLong()

        /**
         * The maximum number of parts that can be returned by OSS.
         * Default value: 1000.
         * Maximum value: 1000.
         */
        public var encodingType: String?
            set(value) {
                value?.let { this.parameters["encoding-type"] = it }
            }
            get() = parameters["encoding-type"]

        public fun build(): ListPartsRequest {
            return ListPartsRequest(this)
        }

        public constructor(from: ListPartsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
