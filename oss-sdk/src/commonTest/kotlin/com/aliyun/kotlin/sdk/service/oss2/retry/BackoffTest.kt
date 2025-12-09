package com.aliyun.kotlin.sdk.service.oss2.retry

import kotlin.test.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class BackoffTest {
    @Test
    fun testEqualJitterBackoff() {
        val min = 0.toDuration(DurationUnit.SECONDS)
        val max = 10.toDuration(DurationUnit.SECONDS)

        val delay: BackoffDelayer = EqualJitterBackoff(
            10.toDuration(DurationUnit.MILLISECONDS),
            max
        )
        val e = Exception()
        assertTrue(delay.backoffDelay(0, e) >= min)

        var nonZeroCnt = 0
        for (i in 0..127) {
            val value = delay.backoffDelay(i, e)
            assertTrue(value >= min)
            assertTrue(value <= max)
            if (value > Duration.ZERO) {
                nonZeroCnt++
            }
        }
        assertTrue(nonZeroCnt >= 100)
    }

    @Test
    fun testFixedDelayBackoff() {
        val delay = 20.toDuration(DurationUnit.SECONDS)
        val delayer: BackoffDelayer = FixedDelayBackoff(delay)
        for (i in 0..127) {
            assertEquals(delay, delayer.backoffDelay(i, Exception()))
        }
    }

    @Test
    fun testFullJitterBackoff() {
        val min = 0.toDuration(DurationUnit.SECONDS)
        val max = 20.toDuration(DurationUnit.SECONDS)
        val baseDelay = 200.toDuration(DurationUnit.MILLISECONDS)

        val delay: BackoffDelayer = FullJitterBackoff(baseDelay, max)
        val e = Exception()

        assertTrue(delay.backoffDelay(0, e) >= min)

        var nonZeroCnt = 0
        for (i in 0..127) {
            val value = delay.backoffDelay(i, e)
            assertTrue(value >= min)
            assertTrue(value <= max)
            if (value > Duration.ZERO) {
                nonZeroCnt++
            }
        }
        assertTrue(nonZeroCnt >= 100)
    }
}
