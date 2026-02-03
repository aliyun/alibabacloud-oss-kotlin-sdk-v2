package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketEncryption operation.
 */
public class GetBucketEncryptionResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores server-side encryption rules.
     */
    public val serverSideEncryptionRule: ServerSideEncryptionRule?
        get() = innerBody as? ServerSideEncryptionRule
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketEncryptionResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketEncryptionResult {
            return GetBucketEncryptionResult(this)
        }
    }
}
