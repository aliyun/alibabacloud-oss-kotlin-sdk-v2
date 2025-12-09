package com.aliyun.kotlin.sdk.service.oss2.retry

import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.min
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Implements an exponential backoff strategy with equal jitter for retry delays.
 */
public class EqualJitterBackoff(
    /**
     * The base delay used for the first retry attempt.
     */
    private val baseDelay: Duration,
    /**
     * The maximum allowed backoff time to prevent excessive delays.
     */
    private val maxBackoff: Duration
) : BackoffDelayer {
    /**
     * Max permitted retry times. To prevent exponentialDelay from overflow, there must be 2 ^ retriesAttempted
     * <= 2 ^ 31 - 1, which means retriesAttempted <= 30, so that is the ceil for retriesAttempted.
     */
    private val attemptCeiling = floor(ln(Int.Companion.MAX_VALUE.toDouble()) / ln(2.0)).toInt()

    private val random = Random.Default

    /**
     * Calculates the jittered backoff delay for the current attempt.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return The calculated delay duration
     */
    override fun backoffDelay(attempt: Int, error: Throwable?): Duration {
        val retry = min(attempt, attemptCeiling)
        val calculatedDelayMs = baseDelay.inWholeMilliseconds * (1L shl retry)
        val maxDelayMs = min(calculatedDelayMs, this.maxBackoff.toLong(DurationUnit.MILLISECONDS))
        val delayMs = (maxDelayMs / 2) + random.nextLong((maxDelayMs / 2) + 1)
        return delayMs.toDuration(DurationUnit.MILLISECONDS)
    }

    override fun toString(): String {
        return String.format("<EqualJitterBackoff, base delay: '%s', max backoff: '%s'>", baseDelay, maxBackoff)
    }
}
