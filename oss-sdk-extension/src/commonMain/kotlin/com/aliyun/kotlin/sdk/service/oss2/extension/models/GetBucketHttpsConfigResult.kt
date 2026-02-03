package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketHttpsConfig operation.
 */
public class GetBucketHttpsConfigResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores HTTPS configurations.
     */
    public val httpsConfiguration: HttpsConfiguration?
        get() = innerBody as? HttpsConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketHttpsConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketHttpsConfigResult {
            return GetBucketHttpsConfigResult(this)
        }
    }
}
