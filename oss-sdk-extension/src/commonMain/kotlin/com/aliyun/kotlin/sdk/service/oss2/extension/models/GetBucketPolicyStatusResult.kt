package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetBucketPolicyStatus operation.
 */
public class GetBucketPolicyStatusResult(builder: Builder): ResultModel(builder) { 

    /**
     * The container that stores public access information.
     */
    public val policyStatus: PolicyStatus?
        get() = innerBody as? PolicyStatus
     

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetBucketPolicyStatusResult =
            Builder().apply(builder).build()
    }

    public class Builder: ResultModel.Builder() {
        public fun build(): GetBucketPolicyStatusResult {
            return GetBucketPolicyStatusResult(this)
        }
    }
}
