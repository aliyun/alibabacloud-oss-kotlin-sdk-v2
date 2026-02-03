package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketArchiveDirectRead operation.
 */
public class GetBucketArchiveDirectReadResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores the configurations for real-time access of Archive objects.
     */
    public val archiveDirectReadConfiguration: ArchiveDirectReadConfiguration?
        get() = innerBody as? ArchiveDirectReadConfiguration
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketArchiveDirectReadResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketArchiveDirectReadResult {
            return GetBucketArchiveDirectReadResult(this)
        }
    }
}
