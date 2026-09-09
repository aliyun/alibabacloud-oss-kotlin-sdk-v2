package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The request for the ExtendBucketWorm operation.
 */
public class ExtendBucketWormRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The ID of the retention policy.  If the ID of the retention policy that specifies the number of days for which objects can be retained does not exist, the HTTP status code 404 is returned.
     */
    public val wormId: String?
        get() = parameters["wormId"]

    /**
     * The container of the request body.
     */
    public var extendWormConfiguration: ExtendWormConfiguration? = builder.extendWormConfiguration

    public inline fun copy(block: Builder.() -> Unit = {}): ExtendBucketWormRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ExtendBucketWormRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The ID of the retention policy.  If the ID of the retention policy that specifies the number of days for which objects can be retained does not exist, the HTTP status code 404 is returned.
         */
        public var wormId: String?
            set(value) {
                this.parameters["wormId"] = requireNotNull(value)
            }
            get() = parameters["wormId"]

        /**
         * The container of the request body.
         */
        public var extendWormConfiguration: ExtendWormConfiguration? = null

        public fun build(): ExtendBucketWormRequest {
            return ExtendBucketWormRequest(this)
        }

        public constructor(from: ExtendBucketWormRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.extendWormConfiguration = from.extendWormConfiguration
        }
    }
}
