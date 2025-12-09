package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.internal.ExecuteContext
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.parse
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class FixTimeRetryHandler : RetryHandler {

    @OptIn(ExperimentalTime::class)
    override fun retrying(
        request: RequestMessage,
        context: ExecuteContext,
        error: Throwable,
    ) {
        when (error) {
            is ServiceException -> {
                if ("RequestTimeTooSkewed" == error.errorCode ||
                    (
                        "InvalidArgument" == error.errorCode &&
                            error.errorMessage == "Invalid signing date in Authorization header."
                        )
                ) {
                    val date = error.errorFields["ServerTime"]?.let {
                        Instant.parse(it, DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET)
                    } ?: error.headers["Date"]?.let {
                        Instant.parse(it, DateTimeComponents.Formats.RFC_1123)
                    }
                    date?.let {
                        context.signingContext?.clockOffset = it - Clock.System.now()
                    }
                }
            }
        }
    }
}
