package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the ACL information.
 */
public class AccessControlList(builder: Builder) {
    /**
     * The ACL of the object. Default value: default.
     */
    public val grant: String? = builder.grant

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AccessControlList =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The ACL of the object. Default value: default.
         */
        public var grant: String? = null

        public fun build(): AccessControlList {
            return AccessControlList(this)
        }
    }
}
