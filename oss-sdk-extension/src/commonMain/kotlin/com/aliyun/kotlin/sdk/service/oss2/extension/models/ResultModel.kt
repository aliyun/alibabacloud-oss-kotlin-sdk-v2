package com.aliyun.kotlin.sdk.service.oss2.extension.models

import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public open class ResultModel protected constructor(builder: Builder) {

    /**
     * The response headers from the the server
     */
    public val headers: Map<String, String> = builder.headers ?: MapUtils.headersMap()

    /**
     * The HTTP status description.
     */
    public val status: String = builder.status

    /**
     * The HTTP status code.
     */
    public val statusCode: Int = builder.statusCode

    /**
     * Unique ID identifying the request
     */
    public val requestId: String
        get() = headers["x-oss-request-id"] ?: ""

    internal val innerBody: Any? = builder.innerBody

    public open class Builder {
        public var headers: Map<String, String>? = null
        public var status: String = ""
        public var statusCode: Int = 0
        internal var innerBody: Any? = null
    }
}
