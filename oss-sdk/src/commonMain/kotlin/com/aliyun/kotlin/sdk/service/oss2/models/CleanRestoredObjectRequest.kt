package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the CleanRestoredObject operation.
 */
public class CleanRestoredObjectRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket
     */
    public val bucket: String? = builder.bucket

    /**
     * The name of the object.
     */
    public val key: String? = builder.key

    public inline fun copy(
        block: Builder.() -> Unit = {
        }
    ): CleanRestoredObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CleanRestoredObjectRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The name of the bucket
         */
        public var bucket: String? = null

        /**
         * The name of the object.
         */
        public var key: String? = null

        public fun build(): CleanRestoredObjectRequest {
            return CleanRestoredObjectRequest(this)
        }

        public constructor(from: CleanRestoredObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
        }
    }
}
