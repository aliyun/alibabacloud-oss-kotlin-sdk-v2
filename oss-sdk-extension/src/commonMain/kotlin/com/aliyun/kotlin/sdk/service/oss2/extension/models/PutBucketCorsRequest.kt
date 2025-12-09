package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the PutBucketCors operation.
 */
public class PutBucketCorsRequest(builder: Builder) : RequestModel(builder) {
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutBucketCorsRequest =
            Builder().apply(builder).build()
    }

    public class Builder : RequestModel.Builder() {
        public var bucket: String? = null

        public fun build(): PutBucketCorsRequest {
            return PutBucketCorsRequest(this)
        }
    }
}
