package com.aliyun.kotlin.sdk.service.oss2.transport

import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

public class RequestMessage {
    public var url: String = ""
    public var method: String = ""
    public var headers: MutableMap<String, String> = mutableMapOf()
    public var body: ByteStream? = null
}
