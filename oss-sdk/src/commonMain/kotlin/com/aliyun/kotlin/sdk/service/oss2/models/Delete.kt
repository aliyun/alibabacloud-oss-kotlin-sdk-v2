package com.aliyun.kotlin.sdk.service.oss2.models

public class Delete(builder: Builder) {
    /**
     * The objects to be deleted.
     */
    public val objects: List<ObjectIdentifier>? = builder.objects

    /**
     * Specifies whether to enable the Quiet return mode.
     */
    public val quiet: Boolean? = builder.quiet

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Delete.Builder.() -> Unit): Delete =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The objects to be deleted.
         */
        public var objects: List<ObjectIdentifier>? = null

        /**
         * Specifies whether to enable the Quiet return mode.
         */
        public var quiet: Boolean? = null

        public fun build(): Delete {
            return Delete(this)
        }
    }
}
