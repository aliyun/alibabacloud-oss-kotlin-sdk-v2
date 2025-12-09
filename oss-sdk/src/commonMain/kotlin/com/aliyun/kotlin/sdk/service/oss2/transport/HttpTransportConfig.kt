package com.aliyun.kotlin.sdk.service.oss2.transport

import kotlin.time.Duration

public class HttpTransportConfig(
    /**
     * connect time period in which a client should establish a connection with a server
     */
    public val connectTimeout: Duration? = null,

    /**
     * socket maximum time of inactivity between two data packets when exchanging data with a server
     */
    public val readWriteTimeout: Duration? = null,

    /**
     * HTTP proxy url, for example http://192.168.1.1:8080
     */
    public val proxy: String? = null,

    /**
     * Enable http redirect or not. Default is disable
     */
    public val enabledRedirect: Boolean? = null,

    /**
     *  Skip server certificate verification Default is disable
     */
    public val insecureSkipVerify: Boolean? = null,

    /*
    /**
     * ktor additional custom client configuration
     */
    // public val httpClientConfig: io.ktor.client.HttpClientConfig<*>.() -> Unit = {},

    /**
     * ktor engine for http requests.
     */
    // public val engine: io.ktor.client.engine.HttpClientEngine? = null,
     */
)
