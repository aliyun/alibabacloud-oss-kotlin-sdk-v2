package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the GetBucketVersioning operation.
 */
public class GetBucketVersioningRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): GetBucketVersioningRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketVersioningRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): GetBucketVersioningRequest {
            return GetBucketVersioningRequest(this)
        }

        public constructor(from: GetBucketVersioningRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
