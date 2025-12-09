package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

public class ResponseMessage(
    public val status: String = "",
    public val statusCode: Int = 0,
    public val headers: MutableMap<String, String> = mutableMapOf(),
    public val body: ByteStream? = null,
    public val request: RequestMessage? = null
)
