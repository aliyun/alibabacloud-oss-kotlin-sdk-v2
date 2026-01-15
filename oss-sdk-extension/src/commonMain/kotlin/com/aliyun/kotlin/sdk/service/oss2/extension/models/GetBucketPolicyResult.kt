package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

/**
 * The result for the GetBucketPolicy operation.
 */
public class GetBucketPolicyResult(builder: Builder): ResultModel(builder) { 

    /**
     * <no value>
     */
    public val body: ByteStream?
        get() = innerBody as? ByteStream
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketPolicyResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketPolicyResult {
            return GetBucketPolicyResult(this)
        }
    }
}
