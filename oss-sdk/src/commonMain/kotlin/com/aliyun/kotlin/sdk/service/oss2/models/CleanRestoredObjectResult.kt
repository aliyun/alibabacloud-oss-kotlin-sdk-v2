package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The result for the CleanRestoredObject operation.
 */
public class CleanRestoredObjectResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CleanRestoredObjectResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): CleanRestoredObjectResult {
            return CleanRestoredObjectResult(this)
        }
    }
}
