package com.aliyun.kotlin.sdk.service.oss2

import kotlin.test.*

class ClientConfigurationTest {
    @Test
    fun testConstructor() {
        var config = ClientConfiguration().apply {
            region = "cn-shenzhen"
        }
        assertEquals("cn-shenzhen", config.region)
    }

    @Test
    fun testLoadDefault() {
        val config = ClientConfiguration.loadDefault()
        assertNull(config.region)

        with(config) {
            region = "cn-hangzhou"
            endpoint = "oss-cn-shenzhen.aliyuncs.com"
        }

        assertEquals("cn-hangzhou", config.region)
    }
}
