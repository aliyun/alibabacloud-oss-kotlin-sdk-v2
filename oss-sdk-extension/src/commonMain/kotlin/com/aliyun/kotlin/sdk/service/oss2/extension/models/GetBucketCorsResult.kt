package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketCors operation.
 */
public class GetBucketCorsResult(builder: Builder): ResultModel(builder) {

    /**
     * The container that stores CORS configuration.
     */
    public val corsConfiguration: CORSConfiguration?
        get() = innerBody as? CORSConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketCorsResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketCorsResult {
            return GetBucketCorsResult(this)
        }
    }
}
