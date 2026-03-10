package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the GetStyle operation.
 */
public class GetStyleResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container in which the queried image styles are stored.
     */
    public val style: StyleInfo?
        get() = innerBody as? StyleInfo

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): GetStyleResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): GetStyleResult {
            return GetStyleResult(this)
        }
    }
}
