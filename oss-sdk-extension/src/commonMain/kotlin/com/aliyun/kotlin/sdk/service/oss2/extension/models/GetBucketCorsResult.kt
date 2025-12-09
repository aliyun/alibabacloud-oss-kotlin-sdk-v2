package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the GetBucketCors operation.
 */
public class GetBucketCorsResult(builder: Builder) : ResultModel(builder) {
    /**
     * The container that stores the ACL information.
     */
    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketCorsResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketCorsResult {
            return GetBucketCorsResult(this)
        }
    }
}
