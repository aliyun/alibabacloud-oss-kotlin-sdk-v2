package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the CompleteBucketWorm operation.
 */
public class CompleteBucketWormRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The ID of the retention policy.
     */
    public val wormId: String?
        get() = parameters["wormId"]

    public inline fun copy(block: Builder.() -> Unit = {}): CompleteBucketWormRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CompleteBucketWormRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The ID of the retention policy.
         */
        public var wormId: String?
            set(value) {
                this.parameters["wormId"] = requireNotNull(value)
            }
            get() = parameters["wormId"]

        public fun build(): CompleteBucketWormRequest {
            return CompleteBucketWormRequest(this)
        }

        public constructor(from: CompleteBucketWormRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
