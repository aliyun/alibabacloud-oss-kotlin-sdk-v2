package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the ListBucketSpaces operation.
 */
public class ListBucketSpacesRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket. This is used as a prefix and resolved to the physical bucket name.
     */
    public val bucket: String? = builder.bucket

    /**
     * The prefix that the returned names must contain.
     */
    public val prefix: String?
        get() = parameters["prefix"]

    /**
     * The token from which the list operation continues.
     */
    public val continuationToken: String?
        get() = parameters["continuation-token"]

    /**
     * The name of the bucket space after which the list operation begins.
     */
    public val startAfter: String?
        get() = parameters["start-after"]

    /**
     * The maximum number of results to return.
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLongOrNull()

    public inline fun copy(block: Builder.() -> Unit = {}): ListBucketSpacesRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListBucketSpacesRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The prefix that the returned names must contain.
         */
        public var prefix: String?
            set(value) {
                this.parameters["prefix"] = value
            }
            get() = parameters["prefix"]

        /**
         * The token from which the list operation continues.
         */
        public var continuationToken: String?
            set(value) {
                this.parameters["continuation-token"] = value
            }
            get() = parameters["continuation-token"]

        /**
         * The name of the bucket space after which the list operation begins.
         */
        public var startAfter: String?
            set(value) {
                this.parameters["start-after"] = value
            }
            get() = parameters["start-after"]

        /**
         * The maximum number of results to return.
         */
        public var maxKeys: Long?
            set(value) {
                this.parameters["max-keys"] = value?.toString()
            }
            get() = parameters["max-keys"]?.toLongOrNull()

        public fun build(): ListBucketSpacesRequest {
            return ListBucketSpacesRequest(this)
        }

        public constructor(from: ListBucketSpacesRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
