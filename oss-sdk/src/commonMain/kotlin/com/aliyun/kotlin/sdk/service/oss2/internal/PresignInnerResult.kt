@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class PresignInnerResult {
    var url: String? = null
    var method: String? = null
    var expiration: Instant? = null
    var signedHeaders: Map<String, String>? = null
}
