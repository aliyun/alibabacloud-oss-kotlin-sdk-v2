package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the GetBucketLocation operation.
 */
public class GetBucketLocationRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): GetBucketLocationRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketLocationRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): GetBucketLocationRequest {
            return GetBucketLocationRequest(this)
        }

        public constructor(from: GetBucketLocationRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
