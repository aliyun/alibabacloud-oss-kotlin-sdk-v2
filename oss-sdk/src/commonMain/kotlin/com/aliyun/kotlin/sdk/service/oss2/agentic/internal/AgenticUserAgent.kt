package com.aliyun.kotlin.sdk.service.oss2.agentic.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration

/**
 * Prefixes the configuration's user agent with `agentic-client`, matching the Go/Java SDKs.
 * The existing user agent, when present, is appended as `agentic-client/{userAgent}`.
 */
internal fun updateAgenticUserAgent(config: ClientConfiguration) {
    val existing = config.userAgent
    config.userAgent = if (existing.isNullOrEmpty()) "agentic-client" else "agentic-client/$existing"
}
