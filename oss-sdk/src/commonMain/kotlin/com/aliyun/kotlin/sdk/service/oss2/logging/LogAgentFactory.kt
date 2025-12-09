package com.aliyun.kotlin.sdk.service.oss2.logging

internal expect object LogAgentFactory {
    internal fun logger(name: String, level: LogAgentLevel): LogAgent
}
