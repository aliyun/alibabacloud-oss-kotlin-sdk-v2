package com.aliyun.kotlin.sdk.service.oss2.extension.models

/**
 * The result for the ListStyle operation.
 */
public class ListStyleResult(builder: Builder) : ResultModel(builder) {

    /**
     * The container that was used to query the information about image styles.
     */
    public val styleList: StyleList?
        get() = innerBody as? StyleList

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ListStyleResult =
            Builder().apply(builder).build()
    }

    public class Builder : ResultModel.Builder() {
        public fun build(): ListStyleResult {
            return ListStyleResult(this)
        }
    }
}
