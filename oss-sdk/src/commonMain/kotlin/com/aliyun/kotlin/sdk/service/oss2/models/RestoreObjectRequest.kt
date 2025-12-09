package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the RestoreObject operation.
 */
public class RestoreObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * The version number of the object that you want to restore.
     */
    public val versionId: String?
        get() = parameters["versionId"]

    /**
     * The request body schema.
     */
    public var restoreRequest: RestoreRequest? = builder.restoreRequest

    public inline fun copy(block: Builder.() -> Unit = {}): RestoreObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): RestoreObjectRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        /**
         * The full path of the object.
         */
        public var key: String? = null

        /**
         * The version number of the object that you want to restore.
         */
        public var versionId: String?
            set(value) {
                value?.let { this.parameters["versionId"] = it }
            }
            get() = parameters["versionId"]

        /**
         * The request body schema.
         */
        public var restoreRequest: RestoreRequest? = null

        public fun build(): RestoreObjectRequest {
            return RestoreObjectRequest(this)
        }

        public constructor(from: RestoreObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.restoreRequest = from.restoreRequest
        }
    }
}
