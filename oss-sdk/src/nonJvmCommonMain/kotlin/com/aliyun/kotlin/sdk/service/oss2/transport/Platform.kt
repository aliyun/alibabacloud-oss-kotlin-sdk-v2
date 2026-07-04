package com.aliyun.kotlin.sdk.service.oss2.transport

internal actual fun createHttpTransport(config: HttpTransportConfig): HttpTransport {
    return KtorHttpTransportImpl(config)
}
