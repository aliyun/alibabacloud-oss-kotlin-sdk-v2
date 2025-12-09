package com.aliyun.kotlin.sdk.service.oss2.transport

internal actual fun createHttpTransport(config: HttpTransportConfig): HttpTransport {
    return OkHttpTransportImpl(config, null)
}

public fun customHttpTransport(
    config: HttpTransportConfig,
    builder: okhttp3.OkHttpClient.Builder? = null
): HttpTransport {
    return OkHttpTransportImpl(config, builder)
}
