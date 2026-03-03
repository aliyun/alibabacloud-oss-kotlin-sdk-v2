package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteUserDefinedLogFieldsConfig operation.
 */
public class DeleteUserDefinedLogFieldsConfigResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteUserDefinedLogFieldsConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteUserDefinedLogFieldsConfigResult {
            return DeleteUserDefinedLogFieldsConfigResult(this)
        }
    }
}
