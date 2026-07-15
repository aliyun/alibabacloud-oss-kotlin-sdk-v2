package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the CreateAgenticBucket operation.
 */
public class CreateAgenticBucketRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket. This is used as a prefix and resolved to the physical bucket name.
     */
    public val bucket: String? = builder.bucket

    /**
     * The configuration for creating the agentic bucket.
     */
    public val createAgenticBucketConfiguration: CreateAgenticBucketConfiguration? = builder.createAgenticBucketConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): CreateAgenticBucketRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CreateAgenticBucketRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The configuration for creating the agentic bucket.
         */
        public var createAgenticBucketConfiguration: CreateAgenticBucketConfiguration? = null

        public fun build(): CreateAgenticBucketRequest {
            return CreateAgenticBucketRequest(this)
        }

        public constructor(from: CreateAgenticBucketRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.createAgenticBucketConfiguration = from.createAgenticBucketConfiguration
        }
    }
}
