package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container used to store the tag that you want to configure.
 */
public class Tag(builder: Builder) {
    /**
     * The key of a tag.
     * *   A tag key can be up to 64 bytes in length.
     * *   A tag key cannot start with `http://`, `https://`, or `Aliyun`.
     * *   A tag key must be UTF-8 encoded.
     * *   A tag key cannot be left empty.
     */
    public val key: String? = builder.key

    /**
     * The value of the tag that you want to add or modify.
     * *   A tag value can be up to 128 bytes in length.
     * *   A tag value must be UTF-8 encoded.
     * *   The tag value can be left empty.
     */
    public val value: String? = builder.value

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Tag =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The key of a tag.
         * *   A tag key can be up to 64 bytes in length.
         * *   A tag key cannot start with `http://`, `https://`, or `Aliyun`.
         * *   A tag key must be UTF-8 encoded.
         * *   A tag key cannot be left empty.
         */
        public var key: String? = null

        /**
         * The value of the tag that you want to add or modify.
         * *   A tag value can be up to 128 bytes in length.
         * *   A tag value must be UTF-8 encoded.
         * *   The tag value can be left empty.
         */
        public var value: String? = null

        public fun build(): Tag {
            return Tag(this)
        }
    }
}
