package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlinx.io.RawSource
import kotlinx.io.files.Path

/**
 * Opens a read-only [RawSource] over this file positioned at [offset], streaming from there to the
 * end of the file. Positioning is O(1) (a random-access seek), so multiple ranges of the same file
 * can be read concurrently without sharing a read position or re-reading the skipped prefix. The
 * source reads to end-of-file; callers that need a bounded length wrap the result themselves.
 */
internal expect fun Path.sourceAt(offset: Long): RawSource
