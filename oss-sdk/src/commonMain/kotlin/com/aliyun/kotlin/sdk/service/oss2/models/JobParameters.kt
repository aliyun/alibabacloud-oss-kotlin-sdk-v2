package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores the restoration priority configuration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
 */
public class JobParameters(builder: Builder) {
    /**
     * The restoration priority.
     * Valid values:
     * *   Expedited: The object is restored within 1 hour.
     * *   Standard: The object is restored within 2 to 5 hours.
     * *   Bulk: The object is restored within 5 to 12 hours.
     */
    public val tier: String? = builder.tier

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): JobParameters =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The restoration priority.
         * Valid values:
         * *   Expedited: The object is restored within 1 hour.
         * *   Standard: The object is restored within 2 to 5 hours.
         * *   Bulk: The object is restored within 5 to 12 hours.
         */
        public var tier: String? = null

        public fun build(): JobParameters {
            return JobParameters(this)
        }
    }
}
