package com.aliyun.kotlin.sdk.service.oss2.logging

internal actual object LogAgentFactory {
    internal actual fun logger(name: String, level: LogAgentLevel): LogAgent {
        return LogAgentJul(name, level)
    }
}
