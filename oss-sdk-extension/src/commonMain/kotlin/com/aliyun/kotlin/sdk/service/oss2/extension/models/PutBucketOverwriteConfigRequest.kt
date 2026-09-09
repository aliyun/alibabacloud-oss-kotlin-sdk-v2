package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the PutBucketCors operation.
 */
public class PutBucketOverwriteConfigRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The request body schema.
     */
    public var overwriteConfiguration: OverwriteConfiguration? = builder.overwriteConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): PutBucketOverwriteConfigRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketOverwriteConfigRequest =
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
        public var overwriteConfiguration: OverwriteConfiguration? = null

        public fun build(): PutBucketOverwriteConfigRequest {
            return PutBucketOverwriteConfigRequest(this)
        }

        public constructor(from: PutBucketOverwriteConfigRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.overwriteConfiguration = from.overwriteConfiguration
        }
    }
}
