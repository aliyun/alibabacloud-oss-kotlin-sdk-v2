package com.aliyun.kotlin.sdk.service.oss2.models

public class ObjectIdentifier(builder: Builder) {

    /**
     * The name of the object that you want to delete.
     */
    public val key: String? = builder.key

    /**
     * The version ID of the object that you want to delete.
     */
    public val versionId: String? = builder.versionId

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): ObjectIdentifier =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The name of the object that you want to delete.
         */
        public var key: String? = null

        /**
         * The version ID of the object that you want to delete.
         */
        public var versionId: String? = null

        public fun build(): ObjectIdentifier {
            return ObjectIdentifier(this)
        }
    }
}
