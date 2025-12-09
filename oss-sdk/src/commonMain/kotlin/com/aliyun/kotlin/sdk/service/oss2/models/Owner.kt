package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container for the information about the bucket owner.
 */
public class Owner(builder: Builder) {
    /**
     * The user ID of the bucket owner.
     */
    public val id: String? = builder.id

    /**
     * The name of the bucket owner, which is the same as the user ID.
     */
    public val displayName: String? = builder.displayName

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): Owner =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The user ID of the bucket owner.
         */
        public var id: String? = null

        /**
         * The name of the bucket owner, which is the same as the user ID.
         */
        public var displayName: String? = null

        public fun build(): Owner {
            return Owner(this)
        }
    }
}
