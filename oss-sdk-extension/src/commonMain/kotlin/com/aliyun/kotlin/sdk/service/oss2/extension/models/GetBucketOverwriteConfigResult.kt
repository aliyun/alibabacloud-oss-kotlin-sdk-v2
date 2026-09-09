package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketCors operation.
 */
public class GetBucketOverwriteConfigResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores CORS configuration.
     */
    public val overwriteConfiguration: OverwriteConfiguration?
        get() = innerBody as? OverwriteConfiguration

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketOverwriteConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetBucketOverwriteConfigResult {
            return GetBucketOverwriteConfigResult(this)
        }
    }
}
