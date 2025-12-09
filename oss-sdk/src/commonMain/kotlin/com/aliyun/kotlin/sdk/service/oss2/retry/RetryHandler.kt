package com.aliyun.kotlin.sdk.service.oss2.retry

import com.aliyun.kotlin.sdk.service.oss2.internal.ExecuteContext
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage

internal interface RetryHandler {
    fun retrying(request: RequestMessage, context: ExecuteContext, error: Throwable)
}
