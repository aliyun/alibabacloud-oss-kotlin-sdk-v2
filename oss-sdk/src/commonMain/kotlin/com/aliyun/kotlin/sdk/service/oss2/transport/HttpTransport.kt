package com.aliyun.kotlin.sdk.service.oss2.transport

public interface HttpTransport {

    public suspend fun execute(request: RequestMessage, options: RequestOptions): ResponseMessage

    /**
     * The well-formed client name of the client
     *
     * @return String containing the name of the client
     */

    public val name: String
        get() = "UNKNOWN"
}
