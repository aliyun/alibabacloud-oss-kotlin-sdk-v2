package com.aliyun.kotlin.sdk.service.oss2.retry

import kotlin.time.Duration

/**
 * A no-operation retry strategy that never performs any retries.
 */
public class NopRetryer : Retryer {
    /**
     * Always returns false, indicating the error should not be retried.
     *
     * @param error The error to be checked
     * @return Always returns false
     */
    override fun isErrorRetryable(error: Throwable?): Boolean {
        return false
    }

    /**
     * Returns the maximum number of retry attempts, which is always 1 (no retries).
     *
     * @return Always returns 1
     */
    override fun maxAttempts(): Int {
        return 1
    }

    /**
     * Returns the delay duration before the next retry attempt, which is always zero seconds.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return A zero-duration delay
     */
    override fun retryDelay(attempt: Int, error: Throwable?): Duration {
        return Duration.ZERO
    }
}
