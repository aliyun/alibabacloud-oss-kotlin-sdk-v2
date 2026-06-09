package com.aliyun.kotlin.sdk.service.oss2.transport

import kotlin.test.Test
import kotlin.test.assertFailsWith

class OkHttpTransportImplTest {

    @Test
    fun testProxyWithHttpUrl() {
        val config = HttpTransportConfig(proxy = "http://192.168.1.1:8080")
        OkHttpTransportImpl(config, null)
    }

    @Test
    fun testProxyWithHttpsUrl() {
        val config = HttpTransportConfig(proxy = "https://192.168.1.1:443")
        OkHttpTransportImpl(config, null)
    }

    @Test
    fun testProxyWithDefaultHttpPort() {
        val config = HttpTransportConfig(proxy = "http://192.168.1.1")
        OkHttpTransportImpl(config, null)
    }

    @Test
    fun testProxyWithDefaultHttpsPort() {
        val config = HttpTransportConfig(proxy = "https://192.168.1.1")
        OkHttpTransportImpl(config, null)
    }

    @Test
    fun testProxyWithInvalidUrl() {
        val config = HttpTransportConfig(proxy = "not-a-url")
        assertFailsWith<IllegalArgumentException> {
            OkHttpTransportImpl(config, null)
        }
    }

    @Test
    fun testNoProxy() {
        val config = HttpTransportConfig()
        OkHttpTransportImpl(config, null)
    }
}
