package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the DeleteStyle operation.
 */
public class DeleteStyleResult(builder: Builder) : ResultModel(builder) {

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): DeleteStyleResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): DeleteStyleResult {
            return DeleteStyleResult(this)
        }
    }
}
