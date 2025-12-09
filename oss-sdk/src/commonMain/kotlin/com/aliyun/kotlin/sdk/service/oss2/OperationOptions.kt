package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import kotlin.time.Duration

/**
 * An operation options
 */
public class OperationOptions(builder: Builder) {
    /**
     * Returns an Optional containing the maximum number attempts.
     * If no retryMaxAttempts was set in this OperationOptions's builder, then the Optional is empty.
     */
    public val retryMaxAttempts: Int? = builder.retryMaxAttempts

    /**
     * Returns an Optional containing the read write timeout duration.
     * If no readWriteTimeout was set in this OperationOptions's builder, then the Optional is empty.
     */
    public val readWriteTimeout: Duration? = builder.readWriteTimeout

    /**
     * Returns an Optional containing the way in which it is signed
     */
    public val authMethod: AuthMethodType? = builder.authMethod

    public companion object {

        /**
         * Empty [OperationOptions] instance
         */
        public val Default: OperationOptions = OperationOptions(Builder())

        /**
         * Builds a [OperationOptions] instance with the given [builder] function
         *
         * @param builder specifies a function to build a map
         */
        public inline fun build(
            builder: OperationOptions.Builder.() -> Unit
        ): OperationOptions = Builder().apply(builder).build()
    }

    public class Builder {

        /**
         * Sets the maximum number attempts an API client will call an operation that fails with a retryable error.
         */
        public var retryMaxAttempts: Int? = null

        /**
         * Sets the read write timeout duration。
         */
        public var readWriteTimeout: Duration? = null

        /**
         * Sets the way in which it is signed
         */
        public var authMethod: AuthMethodType? = null

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new OperationOptions
         */
        public fun build(): OperationOptions {
            return OperationOptions(this)
        }
    }
}
