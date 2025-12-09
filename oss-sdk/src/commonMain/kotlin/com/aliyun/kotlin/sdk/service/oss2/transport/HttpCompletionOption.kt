package com.aliyun.kotlin.sdk.service.oss2.transport

public enum class HttpCompletionOption {
    /**
     * The operation should complete after reading the entire response including the content
     */
    ResponseContentRead,

    /**
     * The operation should complete as soon as a response is available and headers are read. The content is not read yet.
     */
    ResponseHeadersRead
}
