package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver
import kotlin.time.Duration

public data class RequestOptions(
    val timeout: Duration? = null,
    val uploadObservers: List<StreamObserver>? = null,
    var httpCompletionOption: HttpCompletionOption? = null,
)
