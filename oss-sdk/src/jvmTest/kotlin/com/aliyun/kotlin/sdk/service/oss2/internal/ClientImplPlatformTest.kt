package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransportConfig
import com.aliyun.kotlin.sdk.service.oss2.transport.customHttpTransport
import kotlin.test.Test
import kotlin.test.assertContains

class ClientImplPlatformTest {
    @Test
    fun configHttpClient() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            // assertTrue {  client.options.httpClient is KtorHttpTransportImpl}
        }
    }

    @Test
    fun configUserAgent() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertContains(client.innerOptions.userAgent, "alibabacloud-kotlin-sdk-v2/0.1")
            assertContains(client.innerOptions.userAgent, "/okhttp3-client")
        }
    }

    @Test
    fun customHttpTransportDefault() {
        val httpTransportConfig = HttpTransportConfig()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            httpTransport = customHttpTransport(httpTransportConfig)
        }

        ClientImpl(config).use { client ->
            assertContains(client.innerOptions.userAgent, "alibabacloud-kotlin-sdk-v2/0.1")
            assertContains(client.innerOptions.userAgent, "/okhttp3-client")
        }
    }

    @Test
    fun customHttpTransportWithBuilder() {
        val httpTransportConfig = HttpTransportConfig()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            httpTransport = customHttpTransport(
                httpTransportConfig,
                okhttp3.OkHttpClient.Builder(),
            )
        }

        ClientImpl(config).use { client ->
            assertContains(client.innerOptions.userAgent, "alibabacloud-kotlin-sdk-v2/0.1")
            assertContains(client.innerOptions.userAgent, "/okhttp3-client")
        }
    }
}
