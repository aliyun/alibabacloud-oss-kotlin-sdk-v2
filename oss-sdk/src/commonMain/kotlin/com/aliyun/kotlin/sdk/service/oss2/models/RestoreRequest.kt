package com.aliyun.kotlin.sdk.service.oss2.models

/**
 * The container that stores information about the RestoreObject request.
 */
public class RestoreRequest(builder: Builder) {
    /**
     * The duration in which the object can remain in the restored state. Unit: days. Valid values: 1 to 7.
     */
    public val days: Long? = builder.days

    /**
     * The container that stores the restoration priority configuration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
     */
    public val jobParameters: JobParameters? = builder.jobParameters

    public constructor() : this(Builder())

    public companion object {
        public operator fun invoke(builder: Builder.() -> Unit): RestoreRequest =
            Builder().apply(builder).build()
    }

    public class Builder {
        /**
         * The duration in which the object can remain in the restored state. Unit: days. Valid values: 1 to 7.
         */
        public var days: Long? = null

        /**
         * The container that stores the restoration priority configuration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
         */
        public var jobParameters: JobParameters? = null

        public fun build(): RestoreRequest {
            return RestoreRequest(this)
        }
    }
}
