package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutStyle operation.
 */
public class PutStyleResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutStyleResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutStyleResult {
            return PutStyleResult(this)
        }
    }
}
