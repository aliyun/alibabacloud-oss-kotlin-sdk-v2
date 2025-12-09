package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the results of the GetObjectACL request.
 */
public class AccessControlPolicy(builder: Builder) {
    /**
     * The container that stores the information about the bucket owner.
     */
    public val owner: Owner? = builder.owner

    /**
     * The container that stores the ACL information.
     */
    public val accessControlList: AccessControlList? = builder.accessControlList

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AccessControlPolicy =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The container that stores the information about the bucket owner.
         */
        public var owner: Owner? = null

        /**
         * The container that stores the ACL information.
         */
        public var accessControlList: AccessControlList? = null

        public fun build(): AccessControlPolicy {
            return AccessControlPolicy(this)
        }
    }
}
