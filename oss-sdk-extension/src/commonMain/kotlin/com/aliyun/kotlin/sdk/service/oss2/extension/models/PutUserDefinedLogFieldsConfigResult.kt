package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the PutUserDefinedLogFieldsConfig operation.
 */
public class PutUserDefinedLogFieldsConfigResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): PutUserDefinedLogFieldsConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): PutUserDefinedLogFieldsConfigResult {
            return PutUserDefinedLogFieldsConfigResult(this)
        }
    }
}
