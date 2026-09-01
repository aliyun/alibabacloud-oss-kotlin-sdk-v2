package com.aliyun.kotlin.sdk.service.oss2.agentic.models

import com.aliyun.kotlin.sdk.service.oss2.models.ResultModel

/**
 * The result for the PutAgenticBucketStatus operation.
 */
public class PutAgenticBucketStatusResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutAgenticBucketStatusResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutAgenticBucketStatusResult {
            return PutAgenticBucketStatusResult(this)
        }
    }
}
