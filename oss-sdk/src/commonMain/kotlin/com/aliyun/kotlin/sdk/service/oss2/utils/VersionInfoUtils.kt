package com.aliyun.kotlin.sdk.service.oss2.utils

import com.aliyun.kotlin.sdk.service.oss2.SDK_VERSION

internal object VersionInfoUtils {
    private const val VERSION_INFO_FILE = "versioninfo.properties"
    private const val USER_AGENT_PREFIX = "alibabacloud-kotlin-sdk-v2"

    val defaultUserAgent: String
        get() {
            return "$USER_AGENT_PREFIX/$version"
        }

    val version: String
        get() {
            return SDK_VERSION
        }
}
