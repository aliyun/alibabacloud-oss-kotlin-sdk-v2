package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal object Defaults {

    const val PRODUCT: String = "oss"

    const val DISABLE_SSL: Boolean = false

    const val HTTP_SCHEME: String = "https"

    /**
     * Default transport 's connect timeout is 10, the unit is second
     */
    val CONNECT_TIMEOUT: Duration = 10.toDuration(DurationUnit.SECONDS)

    /**
     * Default transport 's readwrite timeout is 20, the unit is second
     */
    val READWRITE_TIMEOUT: Duration = 20.toDuration(DurationUnit.SECONDS)

    // defaults for retryer
    const val MAX_ATTEMPTS: Int = 3
    val MAX_BACKOFF: Duration = 20.toDuration(DurationUnit.SECONDS)
    val BASE_DELAY: Duration = 200.toDuration(DurationUnit.MILLISECONDS)

    // feature flags
    val FEATURE_FLAGS = FeatureFlagsType.combine(
        FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD,
        FeatureFlagsType.ENABLE_CRC64_CHECK_DOWNLOAD,
        FeatureFlagsType.AUTO_DETECT_MIMETYPE,
        FeatureFlagsType.CORRECT_CLOCK_SKEW
    )!!
}
