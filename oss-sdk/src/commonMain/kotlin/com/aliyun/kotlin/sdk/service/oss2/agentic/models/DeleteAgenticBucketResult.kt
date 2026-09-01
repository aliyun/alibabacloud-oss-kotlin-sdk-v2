package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the DeleteAgenticBucket operation.
 */
public class DeleteAgenticBucketResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteAgenticBucketResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteAgenticBucketResult {
            return DeleteAgenticBucketResult(this)
        }
    }
}
