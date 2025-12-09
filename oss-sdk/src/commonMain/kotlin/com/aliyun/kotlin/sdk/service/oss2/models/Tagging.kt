package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the returned tag of the bucket.
 */
public class Tagging(builder: Builder) {
    /**
     * The tag set of the target object.
     */
    public val tagSet: TagSet? = builder.tagSet

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Tagging =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The tag set of the target object.
         */
        public var tagSet: TagSet? = null

        public fun build(): Tagging {
            return Tagging(this)
        }
    }
}
