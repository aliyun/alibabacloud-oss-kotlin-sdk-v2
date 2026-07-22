package com.aliyun.kotlin.sdk.service.oss2.test

/**
 * Reads a test environment variable, returning null when it is not set.
 */
expect fun testEnv(name: String): String?

/**
 * True when running on the JS target, whose fetch-based runtime strips certain response headers
 * (for example Content-Encoding) that native transports preserve.
 */
expect val isJsPlatform: Boolean
