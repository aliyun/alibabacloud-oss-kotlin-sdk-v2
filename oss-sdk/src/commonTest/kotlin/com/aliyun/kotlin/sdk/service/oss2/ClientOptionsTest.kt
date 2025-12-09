package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.retry.NopRetryer
import com.aliyun.kotlin.sdk.service.oss2.signer.NopSigner
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestOptions
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import com.aliyun.kotlin.sdk.service.oss2.types.AddressStyleType
import com.aliyun.kotlin.sdk.service.oss2.types.AuthMethodType
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import kotlin.test.*

class ClientOptionsTest {
    internal class MockHttpClient : HttpTransport {
        override suspend fun execute(request: RequestMessage, options: RequestOptions): ResponseMessage {
            return ResponseMessage()
        }
    }

    @Test
    fun testConstructorMissRequiredArguments() {
        try {
            ClientOptions.build {}
            assertFails { "should not here" }
        } catch (e: Exception) {
            assertContains(e.toString(), "signer is null")
        }
    }

    @Test
    fun testConstructorOnlyRequiredArguments() {
        val options = ClientOptions.build {
            signer = NopSigner()
            credentialsProvider = AnonymousCredentialsProvider()
            retryer = NopRetryer()
            httpClient = MockHttpClient()
        }

        assertEquals("oss", options.product)
        assertEquals("", options.region)
        assertEquals("", options.endpoint)
        assertEquals(AddressStyleType.VirtualHosted, options.addressStyle)
        assertEquals(AuthMethodType.Header, options.authMethod)
        assertEquals(Defaults.FEATURE_FLAGS, options.featureFlags)

        assertNotNull(options.signer)
        assertNotNull(options.credentialsProvider)
        assertNotNull(options.retryer)
        assertNotNull(options.httpClient)
        assertTrue(options.additionalHeaders.isEmpty())
    }

    @Test
    fun testConstructorAll() {
        val options = ClientOptions.build {
            product = "oss-product"
            region = "cn-hangzhou"
            endpoint = "https://oss-cn-hangzhou.aliyuncs.com"
            addressStyle = AddressStyleType.Path
            authMethod = AuthMethodType.Query
            signer = NopSigner()
            credentialsProvider = AnonymousCredentialsProvider()
            retryer = NopRetryer()
            httpClient = MockHttpClient()
            additionalHeaders = listOf("Date")
            featureFlags = FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD
        }

        assertEquals("oss-product", options.product)
        assertEquals("cn-hangzhou", options.region)
        assertEquals("https://oss-cn-hangzhou.aliyuncs.com", options.endpoint.toString())
        assertEquals(AddressStyleType.Path, options.addressStyle)
        assertEquals(AuthMethodType.Query, options.authMethod)
        assertEquals(FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD, options.featureFlags)

        assertNotNull(options.signer)
        assertNotNull(options.credentialsProvider)
        assertNotNull(options.retryer)
        assertNotNull(options.httpClient)

        assertEquals(1, options.additionalHeaders.size)
        assertEquals("Date", options.additionalHeaders[0])
    }
}
