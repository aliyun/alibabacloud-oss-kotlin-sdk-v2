package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The request for the GetBucketStat operation.
 */
public class GetBucketStatRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The bucket about which you want to query the information.
     */
    public val bucket: String? = builder.bucket

    public inline fun copy(block: Builder.() -> Unit = {}): GetBucketStatRequest = Builder(this).apply(block).build()

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketStatRequest =
            Builder().apply(builder).build()
    }

    public class Builder() : RequestModel.Builder() {

        /**
         * The bucket about which you want to query the information.
         */
        public var bucket: String? = null

        public fun build(): GetBucketStatRequest {
            return GetBucketStatRequest(this)
        }

        public constructor(from: GetBucketStatRequest) : this() {
            this.headers.putAll(from.headers)
            this.parameters.putAll(from.parameters)
            this.bucket = from.bucket
        }
    }
}
