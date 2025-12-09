package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the versioning state of the bucket.
 */
public class VersioningConfiguration(builder: Builder) {
    /**
     * The versioning state of the bucket.
     */
    public val status: String? = builder.status

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): VersioningConfiguration =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The versioning state of the bucket.
         */
        public var status: String? = null

        public fun build(): VersioningConfiguration {
            return VersioningConfiguration(this)
        }
    }
}
