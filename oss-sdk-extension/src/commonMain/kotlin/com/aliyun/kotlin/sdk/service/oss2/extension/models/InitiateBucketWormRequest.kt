package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the InitiateBucketWorm operation.
 */
public class InitiateBucketWormRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The container of the request body.
     */
    public var initiateWormConfiguration: InitiateWormConfiguration? = builder.initiateWormConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): InitiateBucketWormRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): InitiateBucketWormRequest =
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
        public var initiateWormConfiguration: InitiateWormConfiguration? = null

        public fun build(): InitiateBucketWormRequest {
            return InitiateBucketWormRequest(this)
        }

        public constructor(from: InitiateBucketWormRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.initiateWormConfiguration = from.initiateWormConfiguration
        }
    }
}
