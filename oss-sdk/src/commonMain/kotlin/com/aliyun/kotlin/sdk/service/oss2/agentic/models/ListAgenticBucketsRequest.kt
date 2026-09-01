package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the ListAgenticBuckets operation. This operation is region-level and does not target a bucket.
 */
public class ListAgenticBucketsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The token from which the list operation continues.
     */
    public val continuationToken: String?
        get() = parameters["continuation-token"]

    /**
     * The maximum number of results to return.
     */
    public val maxKeys: Long?
        get() = parameters["max-keys"]?.toLongOrNull()

    public inline fun copy(block: Builder.() -> Unit = {}): ListAgenticBucketsRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListAgenticBucketsRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The token from which the list operation continues.
         */
        public var continuationToken: String?
            set(value) {
                this.parameters["continuation-token"] = value
            }
            get() = parameters["continuation-token"]

        /**
         * The maximum number of results to return.
         */
        public var maxKeys: Long?
            set(value) {
                this.parameters["max-keys"] = value?.toString()
            }
            get() = parameters["max-keys"]?.toLongOrNull()

        public fun build(): ListAgenticBucketsRequest {
            return ListAgenticBucketsRequest(this)
        }

        public constructor(from: ListAgenticBucketsRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
        }
    }
}
