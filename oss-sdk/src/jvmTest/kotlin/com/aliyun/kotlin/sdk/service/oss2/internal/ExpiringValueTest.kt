@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.Clock
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ExpiringValueTest {

    // / Test value returned from closure is given back
    @Test
    fun testValue() {
        val expiringValue = ExpiringValue<Int>()
        runBlocking {
            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(1, Clock.System.now())
            }
            assertEquals(1, value)
        }
    }

    // / Test an expired value is updated
    @Test
    fun testExpiredValue() {
        val expiringValue = ExpiringValue<Int>()
        runBlocking {
            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(0, Clock.System.now())
            }
            assertEquals(0, value)

            delay(1.microseconds)

            val value1 = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(1, Clock.System.now())
            }
            assertEquals(1, value1)
        }
    }

    // / Test when a value is just about to expire it returns current value and kicks off
    // / new task to get new value
    @Test
    fun testJustAboutToExpireValue() {
        val expiringValue = ExpiringValue<Int>(threshold = 3.seconds)
        runBlocking {
            // init value
            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(0, Clock.System.now().plus(1.seconds))
            }
            assertEquals(0, value)

            // 200ms, kicks off new value
            delay(200.milliseconds)
            var next = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(1, Clock.System.now().plus(1.seconds))
            }
            assertEquals(0, next)

            // 200ms + 200ms, kicks off new value
            delay(200.milliseconds)
            next = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(2, Clock.System.now().plus(1.seconds))
            }
            assertEquals(1, next)

            // new value is expire, and get next new value
            delay(2.seconds)
            delay(200.milliseconds)
            next = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(3, Clock.System.now().plus(1.seconds))
            }
            assertEquals(3, next)
        }
    }

    // value has not expired
    @Test
    fun testNotExpireValue() {
        val expiringValue = ExpiringValue<Int>(threshold = 3.seconds)
        runBlocking {
            // init value
            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(0, Instant.DISTANT_FUTURE)
            }
            assertEquals(0, value)

            // 200ms, kicks off new value
            delay(200.milliseconds)
            var next = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(1, Clock.System.now().plus(1.seconds))
            }
            assertEquals(0, next)

            // 200ms + 200ms, kicks off new value
            delay(200.milliseconds)
            next = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(2, Clock.System.now().plus(1.seconds))
            }
            assertEquals(0, next)
        }
    }

    // / Test throw Exception
    @Test
    fun testThrowException() {
        val expiringValue = ExpiringValue<Int>()
        runBlocking {
            try {
                val value = expiringValue.getValue {
                    throw RuntimeException("get value fail")
                }
                assertFails {
                    "should not here"
                }
            } catch (e: Exception) {
                assertContains(e.toString(), "get value fail")
            }

            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(0, Instant.DISTANT_FUTURE)
            }
            assertEquals(0, value)
        }
    }

    // / Test cancel task
    @Test
    fun testCancelTask() {
        val expiringValue = ExpiringValue<Int>()
        runBlocking {
            val value = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(0, Clock.System.now().plus(1.seconds))
            }
            assertEquals(0, value)

            delay(1.microseconds)

            val value1 = expiringValue.getValue {
                delay(1000.nanoseconds)
                Pair(1, Clock.System.now())
            }
            assertEquals(0, value)

            expiringValue.cancel()
            expiringValue.cancel()
        }
    }
}
