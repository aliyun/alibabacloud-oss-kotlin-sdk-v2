package com.aliyun.kotlin.sdk.service.oss2.paginator

/**
 * A paginator options
 */
public class PaginatorOptions(builder: Builder) {
    /**
     * The maximum number of items in the response.
     */
    public val limit: Long? = builder.limit

    public companion object {
        /**
         * Builds a [PaginatorOptions] instance with the given [builder] function
         *
         * @param builder specifies a function to build a map
         */
        public operator fun invoke(builder: Builder.() -> Unit): PaginatorOptions = Builder().apply(builder).build()
    }

    public class Builder {

        /**
         * Sets The maximum number of items in the response.
         */
        public var limit: Long? = null

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new PaginatorOptions
         */
        public fun build(): PaginatorOptions {
            return PaginatorOptions(this)
        }
    }
}
