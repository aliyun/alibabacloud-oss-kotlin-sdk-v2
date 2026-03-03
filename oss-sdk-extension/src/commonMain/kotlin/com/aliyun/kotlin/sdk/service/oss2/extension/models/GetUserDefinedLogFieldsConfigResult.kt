package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetUserDefinedLogFieldsConfig operation.
 */
public class GetUserDefinedLogFieldsConfigResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container for the user-defined logging configuration.
     */
    public val userDefinedLogFieldsConfiguration: UserDefinedLogFieldsConfiguration?
        get() = innerBody as? UserDefinedLogFieldsConfiguration

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetUserDefinedLogFieldsConfigResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetUserDefinedLogFieldsConfigResult {
            return GetUserDefinedLogFieldsConfigResult(this)
        }
    }
}
