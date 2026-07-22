@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class DateUtilsTest {
    @Test
    fun formatRfc822Date_twoDigitDayOfMonth() {
        val instant = Instant.parse("2014-05-16T23:56:46Z")
        assertEquals("Fri, 16 May 2014 23:56:46 GMT", DateUtils.formatRfc822Date(instant))
    }

    @Test
    fun formatRfc822Date_singleDigitDayOfMonth_zeroPadded() {
        val instant = Instant.parse("2014-05-07T17:43:26Z")
        assertEquals("Wed, 07 May 2014 17:43:26 GMT", DateUtils.formatRfc822Date(instant))
    }

    @Test
    fun formatRfc822Date_allSingleDigitDays_zeroPadded() {
        // Verify all single-digit days (1-9) produce a zero-padded day-of-month
        val testCases = listOf(
            "2026-07-01T12:00:00Z" to "Wed, 01 Jul 2026 12:00:00 GMT",
            "2026-07-02T12:00:00Z" to "Thu, 02 Jul 2026 12:00:00 GMT",
            "2026-07-03T12:00:00Z" to "Fri, 03 Jul 2026 12:00:00 GMT",
            "2026-07-04T12:00:00Z" to "Sat, 04 Jul 2026 12:00:00 GMT",
            "2026-07-05T12:00:00Z" to "Sun, 05 Jul 2026 12:00:00 GMT",
            "2026-07-06T12:00:00Z" to "Mon, 06 Jul 2026 12:00:00 GMT",
            "2026-07-07T12:00:00Z" to "Tue, 07 Jul 2026 12:00:00 GMT",
            "2026-07-08T12:00:00Z" to "Wed, 08 Jul 2026 12:00:00 GMT",
            "2026-07-09T12:00:00Z" to "Thu, 09 Jul 2026 12:00:00 GMT",
        )

        for ((input, expected) in testCases) {
            assertEquals(expected, DateUtils.formatRfc822Date(Instant.parse(input)))
        }
    }

    @Test
    fun formatRfc822Date_subSecondPrecision_truncatedToWholeSecond() {
        val instant = Instant.parse("2014-05-16T23:56:46.789Z")
        assertEquals("Fri, 16 May 2014 23:56:46 GMT", DateUtils.formatRfc822Date(instant))
    }
}
