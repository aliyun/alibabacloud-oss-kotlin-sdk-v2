package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the GetBucketAcl operation.
 */
public class GetBucketCorsRequest(builder: Builder) : RequestModel(builder) {
    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketCorsRequest =
            Builder().apply(builder).build()
    }

    public class Builder : RequestModel.Builder() {
        public var bucket: String? = null

        public fun build(): GetBucketCorsRequest {
            return GetBucketCorsRequest(this)
        }
    }
}
