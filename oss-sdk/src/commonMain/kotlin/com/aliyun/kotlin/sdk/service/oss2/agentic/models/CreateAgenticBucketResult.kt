package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the CreateAgenticBucket operation.
 */
public class CreateAgenticBucketResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CreateAgenticBucketResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): CreateAgenticBucketResult {
            return CreateAgenticBucketResult(this)
        }
    }
}
