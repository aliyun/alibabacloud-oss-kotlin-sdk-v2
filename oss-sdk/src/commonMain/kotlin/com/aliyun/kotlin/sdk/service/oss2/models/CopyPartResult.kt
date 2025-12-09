package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the copy result.
 */
public class CopyPartResult(builder: Builder) {
    /**
     * The last modified time of copy source.
     */
    public val lastModified: String? = builder.lastModified

    /**
     * The ETag of the copied part.
     */
    public val eTag: String? = builder.eTag

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CopyPartResult =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The last modified time of copy source.
         */
        public var lastModified: String? = null

        /**
         * The ETag of the copied part.
         */
        public var eTag: String? = null

        public fun build(): CopyPartResult {
            return CopyPartResult(this)
        }
    }
}
