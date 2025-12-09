package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectRequest.Builder

public class SealAppendObjectRequest(builder: SealAppendObjectRequest.Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    /**
     * The full path of the object.
     */
    public val key: String? = builder.key

    /**
     * Specifies the expected length of the object when you call the SealAppendObject operation.
     * OSS checks whether this length matches the actual length of the object. If the lengths do not match, the request fails and the PositionNotEqualToLength error is returned.
     */
    public val position: Long?
        get() = parameters["position"]?.toLong()

    public inline fun copy(
        block: SealAppendObjectRequest.Builder.() -> Unit = {
        }
    ): SealAppendObjectRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: SealAppendObjectRequest.Builder.() -> Unit): SealAppendObjectRequest =
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
         * Specifies the expected length of the object when you call the SealAppendObject operation.
         * OSS checks whether this length matches the actual length of the object. If the lengths do not match, the request fails and the PositionNotEqualToLength error is returned.
         */
        public var position: Long?
            set(value) {
                value?.let { this.parameters["position"] = it.toString() }
            }
            get() = parameters["position"]?.toLong()

        public fun build(): SealAppendObjectRequest {
            return SealAppendObjectRequest(this)
        }

        public constructor(from: SealAppendObjectRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
            this.key = from.key
            this.position = from.position
        }
    }
}
