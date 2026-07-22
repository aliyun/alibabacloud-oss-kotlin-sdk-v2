@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlinx.datetime.format
import kotlinx.datetime.format.*
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal object DateUtils {
    val RFC_822: DateTimeFormat<DateTimeComponents> = Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        chars(", ")
        day()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
        chars(" GMT")
    }

    fun formatRfc822Date(instant: Instant): String = instant.format(RFC_822)
}
