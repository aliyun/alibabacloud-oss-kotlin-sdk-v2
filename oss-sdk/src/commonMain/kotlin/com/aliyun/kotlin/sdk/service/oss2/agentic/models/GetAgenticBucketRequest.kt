package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the GetAgenticBucket operation.
 */
public class GetAgenticBucketRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket. This is used as a prefix and resolved to the physical bucket name.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): GetAgenticBucketRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetAgenticBucketRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): GetAgenticBucketRequest {
            return GetAgenticBucketRequest(this)
        }

        public constructor(from: GetAgenticBucketRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
