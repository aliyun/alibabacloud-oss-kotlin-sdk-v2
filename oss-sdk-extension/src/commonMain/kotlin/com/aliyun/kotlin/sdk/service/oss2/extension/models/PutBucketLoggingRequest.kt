package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketLogging operation.
 */
public class PutBucketLoggingRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The request body schema.
     */
    public var bucketLoggingStatus: BucketLoggingStatus? = builder.bucketLoggingStatus

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketLoggingRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketLoggingRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The request body schema.
         */
        public var bucketLoggingStatus: BucketLoggingStatus? = null

        public fun build(): PutBucketLoggingRequest {
            return PutBucketLoggingRequest(this)
        }

        public constructor(from: PutBucketLoggingRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.bucketLoggingStatus = from.bucketLoggingStatus
        }
    }
}
