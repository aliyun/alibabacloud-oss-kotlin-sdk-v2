package com.aliyun.kotlin.sdk.service.oss2.retry

import kotlin.time.Duration

/**
 * Implements a retry backoff strategy with a fixed delay.
 */
public class FixedDelayBackoff(
    /**
     * The fixed delay applied on each retry attempt.
     */
    private val delay: Duration
) : BackoffDelayer {
    /**
     * Returns the fixed delay regardless of the retry attempt count.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return The fixed delay duration
     */
    override fun backoffDelay(attempt: Int, error: Throwable?): Duration {
        return delay
    }

    override fun toString(): String {
        return String.format("<FixedDelayBackoff, delay: '%s'>", delay)
    }
}
