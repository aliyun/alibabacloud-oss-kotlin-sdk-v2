package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the PutAgenticBucketStatus operation.
 */
public class PutAgenticBucketStatusRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket. This is used as a prefix and resolved to the physical bucket name.
     */
    public val bucket: String? = builder.bucket

    /**
     * The status configuration of the bucket.
     */
    public val agenticBucketStatus: AgenticBucketStatus? = builder.agenticBucketStatus

    public inline fun copy(block: Builder.() -> Unit = {}): PutAgenticBucketStatusRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutAgenticBucketStatusRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The status configuration of the bucket.
         */
        public var agenticBucketStatus: AgenticBucketStatus? = null

        public fun build(): PutAgenticBucketStatusRequest {
            return PutAgenticBucketStatusRequest(this)
        }

        public constructor(from: PutAgenticBucketStatusRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.agenticBucketStatus = from.agenticBucketStatus
        }
    }
}
