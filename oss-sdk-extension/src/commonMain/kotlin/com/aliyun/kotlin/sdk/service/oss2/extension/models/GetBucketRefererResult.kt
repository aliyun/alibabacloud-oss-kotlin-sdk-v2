package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketReferer operation.
 */
public class GetBucketRefererResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores the hotlink protection configurations.
     */
    public val refererConfiguration: RefererConfiguration?
        get() = innerBody as? RefererConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketRefererResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketRefererResult {
            return GetBucketRefererResult(this)
        }
    }
}
