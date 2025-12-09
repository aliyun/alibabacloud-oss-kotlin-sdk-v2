package com.aliyun.kotlin.sdk.service.oss2.utils

internal object VersionInfoUtils {
    private const val VERSION_INFO_FILE = "versioninfo.properties"
    private const val USER_AGENT_PREFIX = "alibabacloud-kotlin-sdk-v2"

    val defaultUserAgent: String
        get() {
            return "alibabacloud-kotlin-sdk-v2/0.1.0-dev"
        }

    val version: String
        get() {
            return "0.1.0-dev"
        }
}
