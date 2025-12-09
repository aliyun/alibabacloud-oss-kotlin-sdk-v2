package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * If the delimiter parameter is specified in the request, the response contains CommonPrefixes. Objects whose names contain the same string from the prefix to the next occurrence of the delimiter are grouped as a single result element in CommonPrefixes.
 */
public class CommonPrefix(builder: Builder) {
    /**
     * The prefix contained in the names of returned objects.
     */
    public val prefix: String? = builder.prefix

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): CommonPrefix =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The prefix contained in the names of returned objects.
         */
        public var prefix: String? = null

        public fun build(): CommonPrefix {
            return CommonPrefix(this)
        }
    }
}
