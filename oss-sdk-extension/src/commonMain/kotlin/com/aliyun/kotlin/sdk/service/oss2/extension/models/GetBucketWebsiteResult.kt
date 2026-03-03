package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketWebsite operation.
 */
public class GetBucketWebsiteResult(builder: Builder): ResultModel(builder) { 

    /**
     * The containers of the website configuration.
     */
    public val websiteConfiguration: WebsiteConfiguration?
        get() = innerBody as? WebsiteConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketWebsiteResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketWebsiteResult {
            return GetBucketWebsiteResult(this)
        }
    }
}
