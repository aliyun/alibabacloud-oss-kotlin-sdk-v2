package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsFetchException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.RequestException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ResponseException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import kotlin.test.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class RetryerTest {

    @Test
    fun testStandardRetryer_default() {
        val retryer: Retryer = StandardRetryer.newBuilder().build()
        assertEquals(3, retryer.maxAttempts())

        // retryable test
        assertTrue(retryer.isErrorRetryable(RequestException("")))
        assertTrue(retryer.isErrorRetryable(ResponseException("")))
        assertTrue(retryer.isErrorRetryable(InconsistentException("", "", null)))
        assertTrue(retryer.isErrorRetryable(CredentialsFetchException(Exception())))

        assertFalse(retryer.isErrorRetryable(Exception()))
        assertFalse(retryer.isErrorRetryable(null))

        assertTrue(retryer.isErrorRetryable(ServiceException(401)))
        assertTrue(retryer.isErrorRetryable(ServiceException(408)))
        assertTrue(retryer.isErrorRetryable(ServiceException(429)))
        assertTrue(retryer.isErrorRetryable(ServiceException(500)))
        assertTrue(retryer.isErrorRetryable(ServiceException(504)))
        assertTrue(retryer.isErrorRetryable(ServiceException(599)))

        assertFalse(retryer.isErrorRetryable(ServiceException(400)))
        assertFalse(retryer.isErrorRetryable(ServiceException(403)))
        assertFalse(retryer.isErrorRetryable(ServiceException()))

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400,
                    errorFields = mapOf<String, String>("Code" to "BadRequest")
                )
            )
        )

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 403,
                    errorFields = mapOf<String, String>("Code" to "RequestTimeTooSkewed")
                )
            )
        )

        assertFalse(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400
                )
            )
        )

        // dealy test
        val min = 0.toDuration(DurationUnit.SECONDS)
        val max = 20.toDuration(DurationUnit.SECONDS)
        val e = Exception()
        assertTrue(retryer.retryDelay(0, e) >= min)

        var nonZeroCnt = 0
        for (i in 0..127) {
            val value = retryer.retryDelay(i, e)
            assertTrue(value >= min)
            assertTrue(value <= max)
            if (value > Duration.ZERO) {
                nonZeroCnt++
            }
        }
        assertTrue(nonZeroCnt >= 100)
    }

    @Test
    fun testStandardRetryer_withCustomRetryable() {
        // Arrange
        val r1: ErrorRetryable = TestErrorRetryable()
        val r2: ErrorRetryable = ServiceErrorCodeRetryable()

        val retryer = StandardRetryer.newBuilder()
            .setMaxAttempts(3)
            .setErrorRetryable(listOf(r1, r2))
            .build()

        // retryable test
        assertFalse(retryer.isErrorRetryable(RequestException("")))
        assertFalse(retryer.isErrorRetryable(ResponseException("")))
        assertFalse(retryer.isErrorRetryable(InconsistentException("", "", null)))
        assertFalse(retryer.isErrorRetryable(CredentialsFetchException(Exception())))

        assertFalse(retryer.isErrorRetryable(Exception()))
        assertFalse(retryer.isErrorRetryable(null))

        assertFalse(retryer.isErrorRetryable(ServiceException(401)))
        assertFalse(retryer.isErrorRetryable(ServiceException(408)))
        assertFalse(retryer.isErrorRetryable(ServiceException(429)))
        assertFalse(retryer.isErrorRetryable(ServiceException(500)))
        assertFalse(retryer.isErrorRetryable(ServiceException(504)))
        assertFalse(retryer.isErrorRetryable(ServiceException(599)))

        assertFalse(retryer.isErrorRetryable(ServiceException(400)))
        assertFalse(retryer.isErrorRetryable(ServiceException(403)))
        assertFalse(retryer.isErrorRetryable(ServiceException()))

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400,
                    errorFields = mapOf<String, String>("Code" to "BadRequest")
                )
            )
        )

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 403,
                    errorFields = mapOf<String, String>("Code" to "RequestTimeTooSkewed")
                )
            )
        )

        assertFalse(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400
                )
            )
        )
    }

    @Test
    fun testStandardRetryer_withCustomMaxAttempts() {
        val retryer = StandardRetryer.newBuilder()
            .setMaxAttempts(4)
            .build()

        assertEquals(4, retryer.maxAttempts())

        // retryable test
        assertTrue(retryer.isErrorRetryable(RequestException("")))

        assertFalse(retryer.isErrorRetryable(Exception()))
        assertFalse(retryer.isErrorRetryable(null))

        assertTrue(retryer.isErrorRetryable(ServiceException(401)))
        assertTrue(retryer.isErrorRetryable(ServiceException(408)))

        assertFalse(retryer.isErrorRetryable(ServiceException(400)))

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400,
                    errorFields = mapOf<String, String>("Code" to "BadRequest")
                )
            )
        )

        assertFalse(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400
                )
            )
        )
    }

    @Test
    fun testStandardRetryer_withCustomBackoff() {
        val retryer = StandardRetryer.newBuilder()
            .setBackoffDelayer(FixedDelayBackoff(3.toDuration(DurationUnit.SECONDS)))
            .build()

        assertEquals(3, retryer.maxAttempts())

        // retryable test
        assertTrue(retryer.isErrorRetryable(RequestException("")))

        assertFalse(retryer.isErrorRetryable(Exception()))
        assertFalse(retryer.isErrorRetryable(null))

        assertTrue(retryer.isErrorRetryable(ServiceException(401)))
        assertTrue(retryer.isErrorRetryable(ServiceException(408)))

        assertFalse(retryer.isErrorRetryable(ServiceException(400)))

        assertTrue(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400,
                    errorFields = mapOf<String, String>("Code" to "BadRequest")
                )
            )
        )

        assertFalse(
            retryer.isErrorRetryable(
                ServiceException(
                    statusCode = 400
                )
            )
        )

        // dealy test
        for (i in 0..127) {
            val value = retryer.retryDelay(i, null)
            assertEquals(3.toDuration(DurationUnit.SECONDS), value)
        }
    }

    @Test
    fun testNopRetryer_default() {
        val retryer: Retryer = NopRetryer()
        assertEquals(1, retryer.maxAttempts())
        assertFalse(retryer.isErrorRetryable(RequestException("")))
        assertEquals(Duration.ZERO, retryer.retryDelay(1, null))
    }

    internal class TestErrorRetryable : ErrorRetryable {
        override fun isErrorRetryable(error: Throwable?): Boolean {
            return false
        }

        override fun toString(): String {
            return "<TestErrorRetryable>"
        }
    }
}
