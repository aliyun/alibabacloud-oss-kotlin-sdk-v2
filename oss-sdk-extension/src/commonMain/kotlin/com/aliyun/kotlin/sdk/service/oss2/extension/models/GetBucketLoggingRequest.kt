package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the GetBucketLogging operation.
 */
public class GetBucketLoggingRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketLoggingRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketLoggingRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): GetBucketLoggingRequest {
            return GetBucketLoggingRequest(this)
        }

        public constructor(from: GetBucketLoggingRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
