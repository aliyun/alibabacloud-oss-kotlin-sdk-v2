package com.aliyun.kotlin.sdk.service.oss2.agentic.models

/**
 * The status of an agentic bucket.
 */
public class AgenticBucketStatus(builder: Builder) {
    /**
     * The status of the bucket.
     */
    public val status: String? = builder.status

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): AgenticBucketStatus =
            Builder().apply(builder).build()
    }

    public class Builder {
        public var status: String? = null

        public fun build(): AgenticBucketStatus = AgenticBucketStatus(this)
    }
}
