package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.retry.NopRetryer
import com.aliyun.kotlin.sdk.service.oss2.retry.StandardRetryer
import com.aliyun.kotlin.sdk.service.oss2.signer.RemoteSignerV1
import com.aliyun.kotlin.sdk.service.oss2.signer.RemoteSignerV4
import com.aliyun.kotlin.sdk.service.oss2.signer.SignatureDelegate
import com.aliyun.kotlin.sdk.service.oss2.signer.SignerV1
import com.aliyun.kotlin.sdk.service.oss2.signer.SignerV4
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClientImplTest {
    @Test
    fun defaultConfiguration() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        val client = ClientImpl(config)

        // default
        assertEquals("oss", client.options.product)
        assertEquals("cn-hangzhou", client.options.region)
        assertNotNull(client.options.endpoint)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", client.innerOptions.host)
        assertEquals("https", client.innerOptions.scheme)

        assertEquals(AddressStyleType.VirtualHosted, client.options.addressStyle)
        assertEquals(AuthMethodType.Header, client.options.authMethod)

        assertTrue { client.options.retryer is StandardRetryer }
        assertEquals(Defaults.MAX_ATTEMPTS, client.options.retryer.maxAttempts())

        assertTrue { client.options.signer is SignerV4 }
        assertTrue { client.options.credentialsProvider is AnonymousCredentialsProvider }

        assertNotNull(client.options.httpClient)
        assertNotNull(client.options.additionalHeaders)
        assertEquals(0, client.options.additionalHeaders.size)

        client.close()
    }

    @Test
    fun configSignatureVersion() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is SignerV4 }
        }

        // set to v1
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            signatureVersion = "v1"
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is SignerV1 }
        }

        // set to v4
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            signatureVersion = "v4"
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is SignerV4 }
        }

        // set to any string
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            signatureVersion = "any"
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is SignerV4 }
        }
    }

    @Test
    fun configEndpoint() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertEquals("cn-hangzhou", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("oss-cn-hangzhou.aliyuncs.com", client.innerOptions.host)
            assertEquals("https", client.innerOptions.scheme)
        }

        // internal
        config = ClientConfiguration().apply {
            region = "cn-shanghai"
            credentialsProvider = AnonymousCredentialsProvider()
            useInternalEndpoint = true
        }
        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertEquals("cn-shanghai", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("oss-cn-shanghai-internal.aliyuncs.com", client.innerOptions.host)
            assertEquals("https", client.innerOptions.scheme)
        }

        // accelerate
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            useAccelerateEndpoint = true
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertEquals("cn-hangzhou", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("oss-accelerate.aliyuncs.com", client.innerOptions.host)
            assertEquals("https", client.innerOptions.scheme)
        }

        // dual stack
        config = ClientConfiguration().apply {
            region = "cn-shenzhen"
            credentialsProvider = AnonymousCredentialsProvider()
            useDualStackEndpoint = true
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertEquals("cn-shenzhen", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("cn-shenzhen.oss.aliyuncs.com", client.innerOptions.host)
            assertEquals("https", client.innerOptions.scheme)
        }

        // set endpoint
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            endpoint = "http://oss-cn-shenzhen.aliyuncs.com"
        }

        ClientImpl(config).use { client ->
            assertNotNull(config.endpoint)
            assertEquals("cn-hangzhou", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("oss-cn-shenzhen.aliyuncs.com", client.innerOptions.host)
            assertEquals("http", client.innerOptions.scheme)
        }

        // disable ssl
        config = ClientConfiguration().apply {
            region = "cn-shanghai"
            credentialsProvider = AnonymousCredentialsProvider()
            disableSsl = true
            useInternalEndpoint = true
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertEquals("cn-shanghai", client.options.region)
            assertNotNull(client.options.endpoint)
            assertEquals("oss-cn-shanghai-internal.aliyuncs.com", client.innerOptions.host)
            assertEquals("http", client.innerOptions.scheme)
        }
    }

    @Test
    fun configAddressStyle() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AddressStyleType.VirtualHosted, client.options.addressStyle)
        }

        // cname
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            useCName = true
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AddressStyleType.CName, client.options.addressStyle)
        }

        // path-style
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            usePathStyle = true
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AddressStyleType.Path, client.options.addressStyle)
        }

        // ip endpoint
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            endpoint = "http://127.0.0.1"
        }

        ClientImpl(config).use { client ->
            assertNotNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AddressStyleType.VirtualHosted, client.options.addressStyle)
            assertEquals(AddressStyleType.Path, client.innerOptions.addressStyle)
            assertEquals("127.0.0.1", client.innerOptions.host)
            assertEquals("http", client.innerOptions.scheme)
        }
    }

    @Test
    fun configAuthMethod() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AuthMethodType.Header, client.options.authMethod)
        }

        // auth query
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }
        ClientImpl(
            config,
            listOf { x -> x.toBuilder().also { it.authMethod = AuthMethodType.Query }.build() }
        ).use { client ->
            assertNull(config.endpoint)
            assertNotNull(client.options.endpoint)
            assertEquals(AuthMethodType.Query, client.options.authMethod)
        }
    }

    @Test
    fun configProduct() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }
        ClientImpl(config).use { client ->
            assertEquals("oss", client.options.product)
        }

        // product
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }
        ClientImpl(
            config,
            listOf { x -> x.toBuilder().also { it.product = "oss-cloudbox" }.build() }
        ).use { client ->
            assertEquals("oss-cloudbox", client.options.product)
        }
    }

    @Test
    fun configRetryer() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.retryer is StandardRetryer }
            assertEquals(Defaults.MAX_ATTEMPTS, client.options.retryer.maxAttempts())
        }

        // set retryer
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            retryer = NopRetryer()
        }
        ClientImpl(config).use { client ->
            assertTrue { client.options.retryer is NopRetryer }
            assertEquals(1, client.options.retryer.maxAttempts())
        }

        // set MaxAttempts in retryer
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            retryer = StandardRetryer.newBuilder().setMaxAttempts(5).build()
        }
        ClientImpl(config).use { client ->
            assertTrue { client.options.retryer is StandardRetryer }
            assertEquals(5, client.options.retryer.maxAttempts())
        }

        // set MaxAttempts in configuration
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            retryMaxAttempts = 6
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.retryer is StandardRetryer }
            assertEquals(6, client.options.retryer.maxAttempts())
        }
    }

    @Test
    fun configTimeout() {
    }

    @Test
    fun configHttpClient() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertNotNull(client.options.httpClient)
            assertTrue {
                client.options.httpClient is AutoCloseable
            }
        }
    }

    @Test
    fun configProxyHost() {
    }

    @Test
    fun configUserAgent() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertContains(client.innerOptions.userAgent, "alibabacloud-kotlin-sdk-v2/0.1")
        }

        // set MaxAttempts in configuration
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            userAgent = "my-agent"
        }

        ClientImpl(config).use { client ->
            assertContains(client.innerOptions.userAgent, "alibabacloud-kotlin-sdk-v2/0.1")
            assertContains(client.innerOptions.userAgent, "/my-agent")
        }
    }

    @Test
    fun configCrcCheck() {
    }

    @Test
    fun configAdditionalHeaders() {
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
        }

        ClientImpl(config).use { client ->
            assertTrue { client.options.additionalHeaders.isEmpty() }
        }

        // set values
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            additionalHeaders = listOf("host", "content-length")
        }

        ClientImpl(config).use { client ->
            assertEquals(2, client.options.additionalHeaders.size)
            assertEquals("host", client.options.additionalHeaders[0])
            assertEquals("content-length", client.options.additionalHeaders[1])
        }
    }

    @Test
    fun testConfigSigner() {
        class SignatureDelegateImp : SignatureDelegate {
            override suspend fun signature(info: Map<String, String>): Map<String, String> {
                TODO("Not yet implemented")
            }
        }
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            signer = RemoteSignerV1(SignatureDelegateImp())
        }
        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is RemoteSignerV1 }
        }

        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            signer = RemoteSignerV4(SignatureDelegateImp())
        }
        ClientImpl(config).use { client ->
            assertTrue { client.options.signer is RemoteSignerV4 }
        }
    }
}
