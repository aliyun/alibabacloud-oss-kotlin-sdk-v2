package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the GetAgenticBucket operation.
 */
public class GetAgenticBucketResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that stores the agentic bucket information.
     */
    public val agenticBucketInfo: AgenticBucketInfo?
        get() = innerBody as? AgenticBucketInfo

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetAgenticBucketResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetAgenticBucketResult {
            return GetAgenticBucketResult(this)
        }
    }
}
