package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the DeleteAgenticBucket operation.
 */
public class DeleteAgenticBucketRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket. This is used as a prefix and resolved to the physical bucket name.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): DeleteAgenticBucketRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteAgenticBucketRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): DeleteAgenticBucketRequest {
            return DeleteAgenticBucketRequest(this)
        }

        public constructor(from: DeleteAgenticBucketRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
