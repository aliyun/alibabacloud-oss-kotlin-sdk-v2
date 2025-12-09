package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.Defaults
import kotlin.time.Duration

/**
 * A standard retry strategy implementation that combines error retry checkers and backoff algorithms.
 */
public class StandardRetryer private constructor(builder: Builder) : Retryer {
    private val maxAttempts: Int
    private val errorRetryable: List<ErrorRetryable>
    private val backoffDelayer: BackoffDelayer

    init {
        this.maxAttempts = builder.maxAttempts ?: Defaults.MAX_ATTEMPTS
        this.errorRetryable = builder.errorRetryable ?: listOf<ErrorRetryable>(
            HTTPStatusCodeRetryable(),
            ServiceErrorCodeRetryable(),
            ClientErrorRetryable()
        )
        this.backoffDelayer = builder.backoffDelayer ?: FullJitterBackoff(Defaults.BASE_DELAY, Defaults.MAX_BACKOFF)
    }

    /**
     * Checks whether the given error is retryable by any registered checker.
     *
     * @param error The error to be checked
     * @return true if the error should be retried, false otherwise
     */
    override fun isErrorRetryable(error: Throwable?): Boolean {
        for (retryable in this.errorRetryable) {
            if (retryable.isErrorRetryable(error)) {
                return true
            }
        }
        return false
    }

    /**
     * Returns the maximum number of retry attempts.
     *
     * @return The maximum retry attempts
     */
    override fun maxAttempts(): Int {
        return this.maxAttempts
    }

    /**
     * Calculates the delay duration before the next retry attempt.
     *
     * @param attempt The current retry attempt number (starting from 1)
     * @param error   The error that caused the retry
     * @return The calculated delay duration
     */
    override fun retryDelay(attempt: Int, error: Throwable?): Duration {
        return this.backoffDelayer.backoffDelay(attempt, error)
    }

    /**
     * A mutable builder for config for [StandardRetryer]
     */
    public class Builder internal constructor() {
        internal var maxAttempts: Int? = null
        internal var errorRetryable: List<ErrorRetryable>? = null
        internal var backoffDelayer: BackoffDelayer? = null

        /**
         * Specifies the maximum number of retry attempts allowed.
         */
        public fun setMaxAttempts(value: Int): Builder {
            this.maxAttempts = value
            return this
        }

        /**
         * Specifies the maximum allowed backoff duration to prevent excessive delays.
         */
        public fun setErrorRetryable(value: List<ErrorRetryable>): Builder {
            this.errorRetryable = value
            return this
        }

        /**
         * Specifies the backoff algorithm used to calculate retry delay durations.
         */
        public fun setBackoffDelayer(value: BackoffDelayer): Builder {
            this.backoffDelayer = value
            return this
        }

        public fun build(): StandardRetryer {
            return StandardRetryer(this)
        }
    }

    public companion object {
        public fun newBuilder(): Builder {
            return StandardRetryer.Builder()
        }
    }
}
