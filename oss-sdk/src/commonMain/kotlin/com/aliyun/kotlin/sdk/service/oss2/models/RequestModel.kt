package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils

public abstract class RequestModel protected constructor(builder: Builder) {

    /**
     * The request headers from the the client
     */
    public val headers: MutableMap<String, String> = builder.headers

    /**
     * The request parameters from the the client
     */
    public val parameters: MutableMap<String, String?> = builder.parameters

    public open class Builder {
        internal val headers: MutableMap<String, String> = MapUtils.headersMap()
        internal val parameters: MutableMap<String, String?> = MapUtils.parametersMap()

        public fun addHeader(key: String, value: String) {
            this.headers.put(key, value)
        }

        public fun addParameter(key: String, value: String?) {
            this.parameters.put(key, value)
        }
    }
}
