package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the PutBucketVersioning operation.
 */
public class PutBucketVersioningRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The container of the request body.
     */
    public var versioningConfiguration: VersioningConfiguration? = builder.versioningConfiguration

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): PutBucketVersioningRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketVersioningRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The container of the request body.
         */
        public var versioningConfiguration: VersioningConfiguration? = null

        public fun build(): PutBucketVersioningRequest {
            return PutBucketVersioningRequest(this)
        }

        public constructor(from: PutBucketVersioningRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.versioningConfiguration = from.versioningConfiguration
        }
    }
}
