package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container for tags.
 */
public class TagSet(builder: Builder) {

    /**
     * The tags.
     */
    public val tags: List<Tag>? = builder.tags

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): TagSet =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The tags.
         */
        public var tags: List<Tag>? = null

        public fun build(): TagSet {
            return TagSet(this)
        }
    }
}
