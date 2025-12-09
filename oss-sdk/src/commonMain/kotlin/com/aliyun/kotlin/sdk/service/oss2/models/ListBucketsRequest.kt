package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the ListBuckets operation.
 */
public class ListBucketsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The ID of the resource group to which the bucket belongs.
     */
    public val resourceGroupId: String?
        get() = headers["x-oss-resource-group-id"]

    /**
     * The prefix that the names of returned buckets must contain. If this parameter is not specified, prefixes are not used to filter returned buckets. By default, this parameter is left empty.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The name of the bucket from which the buckets start to return. The buckets whose names are alphabetically after the value of marker are returned. If this parameter is not specified, all results are returned. By default, this parameter is left empty.
     */
    public val marker: String?
        get() = parameters["marker"]

    /**
     * The maximum number of buckets that can be returned. Valid values: 1 to 1000. Default value: 100
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLong()

    /**
     * A tag key of target buckets. The listing results will only include Buckets that have been tagged with this key.
     */
    public val tagKey: String?
        get() = parameters["tag-key"]

    /**
     * A tag value for the target buckets. If this parameter is specified in the request, the tag-key must also be specified. The listing results will only include Buckets that have been tagged with this key-value pair.
     */
    public val tagValue: String?
        get() = parameters["tag-value"]

    /**
     * Tag list of target buckets. Only Buckets that match all the key-value pairs in the list will added into the listing results. The tagging parameter cannot be used with the tag-key and tag-value parameters in a request.
     */
    public val tagging: String?
        get() = parameters["tagging"]

    public inline fun copy(block: Builder.() -> Unit = {}): ListBucketsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The ID of the resource group to which the bucket belongs.
         */
        public var resourceGroupId: String?
            set(value) {
                value?.let { this.headers["x-oss-resource-group-id"] = it }
            }
            get() = headers["x-oss-resource-group-id"]

        /**
         * The prefix that the names of returned buckets must contain. If this parameter is not specified, prefixes are not used to filter returned buckets. By default, this parameter is left empty.
         */
        public var prefix: String?
            set(value) {
                value?.let { this.parameters["prefix"] = it }
            }
            get() = parameters["prefix"]

        /**
         * The name of the bucket from which the buckets start to return. The buckets whose names are alphabetically after the value of marker are returned. If this parameter is not specified, all results are returned. By default, this parameter is left empty.
         */
        public var marker: String?
            set(value) {
                value?.let { this.parameters["marker"] = it }
            }
            get() = parameters["marker"]

        /**
         * The maximum number of buckets that can be returned. Valid values: 1 to 1000. Default value: 100
         */
        public var maxKeys: Long?
            set(value) {
                value?.let { this.parameters["max-keys"] = it.toString() }
            }
            get() = parameters["max-keys"]?.toLong()

        /**
         * A tag key of target buckets. The listing results will only include Buckets that have been tagged with this key.
         */
        public var tagKey: String?
            set(value) {
                value?.let { this.parameters["tag-key"] = it }
            }
            get() = parameters["tag-key"]

        /**
         * A tag value for the target buckets. If this parameter is specified in the request, the tag-key must also be specified. The listing results will only include Buckets that have been tagged with this key-value pair.
         */
        public var tagValue: String?
            set(value) {
                value?.let { this.parameters["tag-value"] = it }
            }
            get() = parameters["tag-value"]

        /**
         * Tag list of target buckets. Only Buckets that match all the key-value pairs in the list will added into the listing results. The tagging parameter cannot be used with the tag-key and tag-value parameters in a request.
         */
        public var tagging: String?
            set(value) {
                value?.let { this.parameters["tagging"] = it }
            }
            get() = parameters["tagging"]

        public fun build(): ListBucketsRequest {
            return ListBucketsRequest(this)
        }

        public constructor(from: ListBucketsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
        }
    }
}
