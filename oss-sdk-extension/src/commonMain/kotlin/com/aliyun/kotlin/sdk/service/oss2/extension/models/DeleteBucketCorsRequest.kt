package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.models.RequestModel

/**
 * The request for the DeleteBucketCors operation.
 */
public class DeleteBucketCorsRequest(builder: Builder) : RequestModel(builder) {

    /**
     * The name of the bucket.
     */
    public val bucket: String? = builder.bucket

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteBucketCorsRequest =
            Builder().apply(builder).build()
    }

    public class Builder : RequestModel.Builder() {

        /**
         * The name of the bucket.
         */
        public var bucket: String? = null

        public fun build(): DeleteBucketCorsRequest {
            return DeleteBucketCorsRequest(this)
        }
    }
}
