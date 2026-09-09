package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the AbortBucketWorm operation.
 */
public class AbortBucketWormRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): AbortBucketWormRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AbortBucketWormRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): AbortBucketWormRequest {
            return AbortBucketWormRequest(this)
        }

        public constructor(from: AbortBucketWormRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
