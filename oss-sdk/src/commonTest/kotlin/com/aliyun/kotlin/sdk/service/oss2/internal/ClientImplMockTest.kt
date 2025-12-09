@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.Defaults
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.CredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.OperationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.PresignExpirationException
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.retry.FixedDelayBackoff
import com.aliyun.kotlin.sdk.service.oss2.retry.NopRetryer
import com.aliyun.kotlin.sdk.service.oss2.retry.StandardRetryer
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestOptions
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.FeatureFlagsType
import com.aliyun.kotlin.sdk.service.oss2.utils.Base64Utils
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.parse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toDuration

/**
 * Convert this [String] to a [ByteStream]
 */
private fun String.asByteStream(): ByteStream = ByteStream.fromString(this)

/**
 * Convert this [ByteArray] to a [ByteStream]
 */
private fun ByteArray.asByteStream(): ByteStream = ByteStream.fromBytes(this)

class ClientImplMockTest {

    internal class MockHttpClient : HttpTransport {
        var lastRequest: RequestMessage? = null
        var responses: MutableList<ResponseMessage>? = null
        var requests: MutableList<RequestMessage>? = null
        var requestDates: MutableList<String>? = null

        override suspend fun execute(request: RequestMessage, options: RequestOptions): ResponseMessage {
            saveRequest(request)
            return popResponse()
        }

        private fun saveRequest(request: RequestMessage) {
            lastRequest = request

            if (requests == null) {
                requests = mutableListOf()
            }
            requests!!.add(request)

            if (requestDates == null) {
                requestDates = mutableListOf()
            }
            // requestDates!!.add(request.headers.get("Date"))
        }

        private fun popResponse(): ResponseMessage {
            return responses!!.removeAt(0)
        }

        fun clear() {
            lastRequest = null
            responses = null
            requests = null
            requestDates = null
        }
    }

    internal class MockHttpClientFixTime : HttpTransport {

        var lastRequest: RequestMessage? = null
        var responses: MutableList<ResponseMessage>? = null
        var requests: MutableList<RequestMessage>? = null

        override suspend fun execute(
            request: RequestMessage,
            options: RequestOptions
        ): ResponseMessage {
            saveRequest(request)
            return popResponse(request)
        }

        private fun saveRequest(request: RequestMessage) {
            lastRequest = request

            if (requests == null) {
                requests = mutableListOf()
            }
            requests?.add(request)
        }

        private suspend fun popResponse(request: RequestMessage): ResponseMessage {
            val response = requireNotNull(responses?.removeAt(0))
            response.headers["request-date"] = request.headers["Date"]!!
            return response
        }

        public fun clear() {
            lastRequest = null
            responses = null
            requests = null
        }
    }

    companion object {
        val ErrorXml: String = """
            <Error>
                <Code>InvalidAccessKeyId</Code>
                <Message>The OSS Access Key Id you provided does not exist in our records.</Message>
                <RequestId>id-1234</RequestId>
                <HostId>oss-cn-hangzhou.aliyuncs.com</HostId>
                <OSSAccessKeyId>ak</OSSAccessKeyId>
                <EC>0002-00000902</EC>
                <RecommendDoc>https://api.aliyun.com/troubleshoot?q=0002-00000902</RecommendDoc>
             </Error>
        """.trimIndent()
    }

    @Test
    fun invokeOperationSuccess() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
            }
        }
    }

    @Test
    fun invokeOperationFail() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 403, body = ErrorXml.asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    val output = client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(403, se.statusCode)
                    assertEquals("InvalidAccessKeyId", se.errorCode)
                    assertEquals("The OSS Access Key Id you provided does not exist in our records.", se.errorMessage)
                    assertEquals("id-1234", se.requestId)
                    assertEquals("0002-00000902", se.ec)
                    assertEquals("oss-cn-hangzhou.aliyuncs.com", se.errorFields["HostId"])
                    assertEquals("https://api.aliyun.com/troubleshoot?q=0002-00000902", se.errorFields["RecommendDoc"])
                    assertEquals("ak", se.errorFields["OSSAccessKeyId"])
                }
            }
        }
    }

    @Test
    fun verifyExecuteArgsInvalidEndpoint() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            endpoint = "#!@"
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 403, body = ErrorXml.asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: Exception) {
                    assertContains(e.toString(), "endpoint or region is invalid")
                }
            }
        }
    }

    @Test
    fun verifyExecuteArgsInvalidMethod() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: Exception) {
                    assertContains(e.toString(), "input.method is empty")
                }
            }
        }
    }

    @Test
    fun verifyExecuteArgsInvalidBucketName() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "12"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: Exception) {
                    assertContains(e.toString(), "input.bucket is invalid, got 12.")
                }
            }
        }
    }

    @Test
    fun verifyExecuteArgsInvalidObjectName() {
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "1".repeat(1024)
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: Exception) {
                    assertContains(e.toString(), "input.key is invalid, got")
                }
            }
        }
    }

    @Test
    fun configRetryMaxAttemptsFromClientOptions() {
        val mockHandler = MockHttpClient()

        // default max retry attempts is 3
        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(Defaults.MAX_ATTEMPTS, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(500, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }

        //  max retry attempts is 4
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            retryMaxAttempts = 4
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(4, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(500, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }
    }

    @Test
    fun configRetryMaxAttemptsFromOperationOptions() {
        val mockHandler = MockHttpClient()

        // default max retry attempts is 3
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: Exception) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(Defaults.MAX_ATTEMPTS, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(500, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }

        //  set Operation retry attempts to 2
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.build { retryMaxAttempts = 2 })
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(2, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(500, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }
    }

    @Test
    fun configRetryMaxAttemptsNopRetryer() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            retryer = NopRetryer()
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(500, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }
    }

    @Test
    fun configNoRetryError() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 403, body = ErrorXml.asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 500, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(403, se.statusCode)
                    assertEquals("InvalidAccessKeyId", se.errorCode)
                }
            }
        }
    }

    @Test
    fun configSeekableStream() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 501, body = ErrorXml.asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 502, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 503, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 504, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                body = ByteStream.fromString("")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(3, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(503, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                }
            }
        }
    }

    @Test
    fun configNoSeekableStream() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 501, body = ErrorXml.asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 502, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 503, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 504, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                object : ByteStream.Buffer() {
                    override val isOneShot: Boolean = true
                    override fun bytes(): ByteArray {
                        return "".toByteArray()
                    }
                    override val contentLength: Long = 0
                }.also { body = it }
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(501, se.statusCode)
                    assertEquals("InvalidAccessKeyId", se.errorCode)
                }
            }
        }
    }

    @Test
    fun checkBackoffSleepTime() {
        val mockHandler = MockHttpClient()

        // max retry attempts is 3, delay backoff is 2s
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            retryer = StandardRetryer.newBuilder()
                .setBackoffDelayer(FixedDelayBackoff(2.toDuration(DurationUnit.SECONDS)))
                .build()
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 501, body = ErrorXml.asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 502, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 503, body = "".asByteStream()))
        mockHandler.responses!!.add(ResponseMessage(statusCode = 504, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                body = object : ByteStream.Buffer() {
                    override val isOneShot: Boolean = true
                    override fun bytes(): ByteArray {
                        return byteArrayOf()
                    }
                    override val contentLength: Long = 0
                }
            }

            runBlocking {
                val start = Clock.System.now()

                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(501, se.statusCode)
                    assertEquals("InvalidAccessKeyId", se.errorCode)
                }

                val diff = Clock.System.now() - start

                assertTrue {
                    diff >= 4.toDuration(DurationUnit.SECONDS)
                    diff < 5.toDuration(DurationUnit.SECONDS)
                }
            }
        }
    }

    @Test
    fun configSignerV4() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertEquals(
                    "UNSIGNED-PAYLOAD",
                    mockHandler.lastRequest!!.headers["x-oss-content-sha256"]
                )
                assertContains(
                    mockHandler.lastRequest!!.headers["Authorization"]!!,
                    "OSS4-HMAC-SHA256 Credential=ak/",
                )
                assertContains(
                    mockHandler.lastRequest!!.headers["x-oss-date"]!!,
                    "202",
                )
            }
        }
    }

    @Test
    fun configSignerV1() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v1"
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val headers = MapUtils.headersMap()
            headers.put("Content-Type", "application/xml")

            val parameters = MapUtils.parametersMap()
            parameters.put("acl", "")

            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                this.headers = headers
                this.parameters = parameters
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.headers["Authorization"]!!,
                    "OSS ak:",
                )
            }
        }
    }

    @Test
    fun sendAnonymousRequest() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = AnonymousCredentialsProvider()
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                body = object : ByteStream.Buffer() {
                    override val isOneShot: Boolean = true
                    override fun bytes(): ByteArray {
                        return "".toByteArray()
                    }
                    override val contentLength: Long = 0
                }
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertFalse(mockHandler.lastRequest!!.headers.containsKey("Authorization"))
            }
        }
    }

    @Test
    fun returnsEmptyCredentials() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("", "")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                body = ByteStream.fromString("")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    // assertNotNull(mockHandler.requests)
                    // assertEquals(1, mockHandler.requests!!.size)
                    assertContains(e.toString(), "Credentials is null or empty",)
                }
            }
        }
    }

    @Test
    fun returnsFetchCredentialsThrowException() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = object : CredentialsProvider {
                override suspend fun getCredentials(): Credentials {
                    throw RuntimeException("fetch and throw exception")
                }
            }
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
                body = ByteStream.fromString("")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertContains(e.toString(), "Fetch Credentials raised an exception")
                    assertContains(e.toString(), "fetch and throw exception")
                }
            }
        }
    }

    @Test
    fun useVirtualHostAddressingMode() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        // no bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "https://oss-cn-hangzhou.aliyuncs.com/?key=value")
            }
        }

        // bucket
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.url,
                    "https://my-bucket.oss-cn-hangzhou.aliyuncs.com/?key=value"
                )
            }
        }

        // bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                key = "my-key"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.url,
                    "https://my-bucket.oss-cn-hangzhou.aliyuncs.com/my-key?key=value"
                )
            }
        }
    }

    @Test
    fun usePathAddressingMode() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            usePathStyle = true
        }

        // no bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "https://oss-cn-hangzhou.aliyuncs.com/?key=value")
            }
        }

        // bucket
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.url,
                    "https://oss-cn-hangzhou.aliyuncs.com/my-bucket/?key=value"
                )
            }
        }

        // bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                key = "my-key"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.url,
                    "https://oss-cn-hangzhou.aliyuncs.com/my-bucket/my-key?key=value"
                )
            }
        }
    }

    @Test
    fun useCNameAddressingMode() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            useCName = true
            endpoint = "http://www.cname.com"
        }

        // no bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://www.cname.com/?key=value")
            }
        }

        // bucket
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://www.cname.com/?key=value")
            }
        }

        // bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                key = "my-key+123"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://www.cname.com/my-key%2B123?key=value")
            }
        }
    }

    @Test
    fun useIpEndpoint() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            endpoint = "http://192.168.1.1:8080"
        }

        // no bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://192.168.1.1:8080/?key=value")
            }
        }

        // bucket
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://192.168.1.1:8080/my-bucket/?key=value")
            }
        }

        // bucket & key
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                key = "my-key+123"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key" to "value")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(
                    mockHandler.lastRequest!!.url,
                    "http://192.168.1.1:8080/my-bucket/my-key%2B123?key=value"
                )
            }
        }
    }

    @Test
    fun useNornalEndpointWithQuery() {
        val mockHandler = MockHttpClient()

        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            endpoint = "http://www.test.com/123?key=value"
        }

        // bucket
        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key1" to "value1")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://my-bucket.www.test.com/?key1=value1")
            }
        }

        // path
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            endpoint = "http://www.test.com/123?key=value"
            usePathStyle = true
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key1" to "value1")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://www.test.com/my-bucket/?key1=value1")
            }
        }

        // cname
        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            endpoint = "http://www.test.com/123?key=value"
            useCName = true
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(ResponseMessage(statusCode = 200, body = "".asByteStream()))

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "InvokeOperation"
                method = "PUT"
                bucket = "my-bucket"
                key = "my-key+123/1.txt"
                body = ByteStream.fromString("")
                parameters = mutableMapOf("key1" to "value1")
            }

            runBlocking {
                val output = client.execute(input, OperationOptions.Default)
                assertNotNull(mockHandler.requests)
                assertEquals(1, mockHandler.requests!!.size)
                assertEquals(200, output.statusCode)
                assertContains(mockHandler.lastRequest!!.url, "http://www.test.com/my-key%2B123/1.txt?key1=value1")
            }
        }
    }

    @Test
    fun returnsServiceExceptionNormal() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = """
            <Error>
              <Code>NoSuchBucket</Code>
              <Message>The specified bucket does not exist.</Message>
              <RequestId>5C3D9175B6FC201293AD****</RequestId>
              <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>
              <BucketName>test</BucketName>
              <EC>0015-00000101</EC>
             </Error>
        """.trimIndent()

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = xmlBody.asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "5C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("NoSuchBucket", se.errorCode)
                    assertEquals("The specified bucket does not exist.", se.errorMessage)
                    assertEquals("5C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("0015-00000101", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionInHeader() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = """
            <Error>
              <Code>NoSuchBucket</Code>
              <Message>The specified bucket does not exist.</Message>
              <RequestId>5C3D9175B6FC201293AD****</RequestId>
              <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>
              <BucketName>test</BucketName>
              <EC>0015-00000101</EC>
             </Error>
        """.trimIndent()

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = "".asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "5C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                    "x-oss-err" to Base64Utils.encodeToString(xmlBody.toByteArray())
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("NoSuchBucket", se.errorCode)
                    assertEquals("The specified bucket does not exist.", se.errorMessage)
                    assertEquals("5C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("0015-00000101", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionEmptyBody() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = ""

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = xmlBody.asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "5C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                    "x-oss-ec" to "0015-00000101"
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                    assertEquals("Empty body", se.errorMessage)
                    assertEquals("5C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("0015-00000101", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionNotErrorFormat() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = """
            <NotError>
              <Code>NoSuchBucket</Code>
              <Message>The specified bucket does not exist.</Message>
              <RequestId>5C3D9175B6FC201293AD****</RequestId>
              <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>
              <BucketName>test</BucketName>
              <EC>0015-00000101</EC>
             </NotError>
        """.trimIndent()

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = xmlBody.asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "6C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                    assertContains(se.errorMessage, "Not found tag <Error>, part response body ")
                    assertEquals("6C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionNotXmlFormat() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = """
            NotError
              <Code>NoSuchBucket</Code>
              <Message>The specified bucket does not exist.</Message>
              <RequestId>5C3D9175B6FC201293AD****</RequestId>
              <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>
              <BucketName>test</BucketName>
              <EC>0015-00000101</EC>
             </NotError>
        """.trimIndent()

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = xmlBody.asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "6C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                    assertContains(se.errorMessage, "Failed to parse xml from response body, part response body")
                    assertEquals("6C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionComplexErrorFormat() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        val xmlBody = """
            <Error>
              <Code>NoSuchBucket</Code>
              <Message>The specified bucket does not exist.</Message>
              <RequestId>5C3D9175B6FC201293AD****</RequestId>
              <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>
              <BucketName>test</BucketName>
              <EC>0015-00000101</EC>
              <InnerError>
                <Field1>filed-1-value</Field1>
                <Field2>filed-2-value</Field2>
                <Field3><Field3-1>filed-3-1-value</Field3-1></Field3>
              </InnerError>
             </Error>
        """.trimIndent()

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                body = xmlBody.asByteStream(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "6C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("NoSuchBucket", se.errorCode)
                    assertContains(se.errorMessage, "The specified bucket does not exist.")
                    assertEquals("5C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("0015-00000101", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                    assertEquals("", se.errorFields["InnerError"])
                }
            }
        }
    }

    @Test
    fun returnsServiceExceptionNullBody() {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        mockHandler.clear()
        mockHandler.responses = mutableListOf()
        mockHandler.responses!!.add(
            ResponseMessage(
                statusCode = 404,
                // body = xmlBody.toByteArray(),
                headers = mutableMapOf(
                    "Content-Type" to "application/xml",
                    "x-oss-request-id" to "6C3D9175B6FC201293AD****",
                    "Date" to "Fri, 24 Feb 2017 03:15:40 GMT",
                )
            ),
        )

        ClientImpl(config).use { client ->
            val input = OperationInput.build {
                opName = "PutBucketAcl"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = mutableMapOf("Content-Type" to "application/xml")
                parameters = mutableMapOf("acl" to "")
            }

            runBlocking {
                try {
                    client.execute(input, OperationOptions.Default)
                    assertFails { "should not here" }
                } catch (e: OperationException) {
                    assertNotNull(mockHandler.requests)
                    assertEquals(1, mockHandler.requests!!.size)
                    val se = ServiceException.asCause(e)
                    assertNotNull(se)
                    assertEquals(404, se.statusCode)
                    assertEquals("BadErrorResponse", se.errorCode)
                    assertContains(se.errorMessage, "Empty body")
                    assertEquals("6C3D9175B6FC201293AD****", se.requestId)
                    assertEquals("", se.ec)
                    assertEquals("Fri, 24 Feb 2017 03:15:40 GMT", se.timestamp)
                }
            }
        }
    }

    @Test
    fun presignInnerV4() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            var input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            var expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            var result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            var url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key?")
            assertContains(url, "x-oss-date=")
            assertTrue {
                url.contains("x-oss-expires=3599") || url.contains("x-oss-expires=3600")
            }
            assertContains(url, "x-oss-signature=")
            assertContains(url, "x-oss-credential=ak%2F")
            assertContains(url, "x-oss-signature-version=OSS4-HMAC-SHA256")
            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(0, result.signedHeaders?.size)

            // default signed headers
            input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "x-oss-date=")
            assertTrue {
                url.contains("x-oss-expires=3599") || url.contains("x-oss-expires=3600")
            }
            assertContains(url, "x-oss-signature=")
            assertContains(url, "x-oss-credential=ak%2F")
            assertContains(url, "x-oss-signature-version=OSS4-HMAC-SHA256")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(3, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
        }
    }

    @Test
    fun presignInnerV4AdditionalHeaders() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
            additionalHeaders = listOf("Abc")
        }

        ClientImpl(config).use { client ->

            // default signed headers
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            val expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "x-oss-date=")
            assertTrue {
                url.contains("x-oss-expires=3599") || url.contains("x-oss-expires=3600")
            }
            assertContains(url, "x-oss-signature=")
            assertContains(url, "x-oss-credential=ak%2F")
            assertContains(url, "x-oss-signature-version=OSS4-HMAC-SHA256")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(4, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
            assertEquals("abc-value1", result.signedHeaders!!["abc"])
        }
    }

    @Test
    fun presignInnerV4AdditionalHeadersToken() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk", "token+1")
            httpTransport = mockHandler
            additionalHeaders = listOf("Abc")
        }

        ClientImpl(config).use { client ->

            // default signed headers
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            val expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "x-oss-date=")
            assertTrue {
                url.contains("x-oss-expires=3599") || url.contains("x-oss-expires=3600")
            }
            assertContains(url, "x-oss-signature=")
            assertContains(url, "x-oss-credential=ak%2F")
            assertContains(url, "x-oss-signature-version=OSS4-HMAC-SHA256")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")
            assertContains(url, "x-oss-security-token=token%2B1")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(4, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
            assertEquals("abc-value1", result.signedHeaders!!["abc"])
        }
    }

    @Test
    fun presignInnerV4_defaultExpiration() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key?")
            assertContains(url, "x-oss-date=")
            assertTrue {
                url.contains("x-oss-expires=900") || url.contains("x-oss-expires=899")
            }
            assertContains(url, "x-oss-signature=")
            assertContains(url, "x-oss-credential=ak%2F")
            assertContains(url, "x-oss-signature-version=OSS4-HMAC-SHA256")
            assertEquals(0, result.signedHeaders?.size)
        }
    }

    @Test
    fun presignInnerV4_expirationLargerThan7Days() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            val expiration = Clock.System.now().plus(7.days).plus(1.seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds

            try {
                client.presignInner(input, null)
                assertFails { "should not here" }
            } catch (e: PresignExpirationException) {
            }
        }
    }

    @Test
    fun presignInnerV1() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v1"
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            var input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            var expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            var result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            var url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key?")
            assertContains(url, "OSSAccessKeyId=ak")
            assertContains(url, "Expires=")
            assertContains(url, "Signature=")
            assertEquals(0, result.signedHeaders?.size)
            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)

            // default signed headers

            // default signed headers
            input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "OSSAccessKeyId=ak")
            assertContains(url, "Expires=${expiration.epochSeconds}")
            assertContains(url, "Signature=")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(3, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
        }
    }

    @Test
    fun presignInnerV1_additionalHeaders() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v1"
            httpTransport = mockHandler
            additionalHeaders = listOf("Abc")
        }

        ClientImpl(config).use { client ->

            // default signed headers
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            val expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "OSSAccessKeyId=ak")
            assertContains(url, "Expires=${expiration.epochSeconds}")
            assertContains(url, "Signature=")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(3, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
        }
    }

    @Test
    fun presignInnerV1_additionalHeadersToken() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk", "token+1")
            signatureVersion = "v1"
            httpTransport = mockHandler
            additionalHeaders = listOf("Abc")
        }

        ClientImpl(config).use { client ->

            // default signed headers
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key+123/subdir/1.txt"
            }

            input.headers.put("Content-Type", "text")
            input.headers.put("Content-MD5", "md5-123")
            input.headers.put("x-oss-meta-key1", "value1")
            input.headers.put("abc", "abc-value1")
            input.headers.put("abc-2", "abc-value2")
            input.parameters.put("key#?+", "value#123/+123")
            input.parameters.put("key", "value")

            val expiration = Clock.System.now().plus((60 * 60).seconds)
            input.opMetadata[OperationMetadataKey.EXPIRATION_EPOCH_SEC] = expiration.epochSeconds
            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key%2B123/subdir/1.txt?")
            assertContains(url, "OSSAccessKeyId=ak")
            assertContains(url, "Expires=${expiration.epochSeconds}")
            assertContains(url, "Signature=")
            assertContains(url, "key=value")
            assertContains(url, "key%23%3F%2B=value%23123%2F%2B123")
            assertContains(url, "security-token=token%2B1")

            assertEquals(expiration.epochSeconds, result.expiration!!.epochSeconds)
            assertEquals(3, result.signedHeaders?.size)

            assertEquals("text", result.signedHeaders!!["Content-Type"])
            assertEquals("md5-123", result.signedHeaders!!["Content-MD5"])
            assertEquals("value1", result.signedHeaders!!["x-oss-meta-key1"])
        }
    }

    @Test
    fun presignInnerV1_defaultExpiration() = runTest {
        val mockHandler = MockHttpClient()

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v1"
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            val input = OperationInput.build {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            val result = client.presignInner(input, null)

            assertEquals("GET", result.method)
            val url = requireNotNull(result.url)
            assertContains(url, "bucket.oss-cn-hangzhou.aliyuncs.com/key?")
            assertContains(url, "OSSAccessKeyId=ak")
            assertContains(url, "Expires=")
            assertContains(url, "Signature=")
            assertEquals(0, result.signedHeaders?.size)
            assertTrue {
                result.expiration!!.minus(Clock.System.now()).inWholeSeconds <= 900
            }
        }
    }

    @Test
    fun presignMisc() = runTest {
        val mockHandler = MockHttpClient()

        // empty ak&sk
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("", "")
            signatureVersion = "v1"
            httpTransport = mockHandler
        }

        ClientImpl(config).use { client ->

            // no headers & parameters
            var input = OperationInput {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = "key"
            }

            try {
                client.presignInner(input, null)
            } catch (e: Exception) {
                assertContains(e.toString(), "Credentials is null or empty.")
            }

            // invalid bucket
            input = OperationInput {
                opName = "GetObject"
                method = "GET"
                bucket = ""
                key = "key"
            }

            try {
                client.presignInner(input, null)
            } catch (e: Exception) {
                assertContains(e.toString(), "input.bucket is invalid, ")
            }

            // invalid bucket
            input = OperationInput {
                opName = "GetObject"
                method = "GET"
                bucket = "bucket"
                key = ""
            }

            try {
                client.presignInner(input, null)
            } catch (e: Exception) {
                assertContains(e.toString(), "input.key is invalid, ")
            }
        }
    }

    @Test
    fun fixTimeSkewed() = runTest {
        val mockHandler = MockHttpClientFixTime()

        var config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v1"
            httpTransport = mockHandler
        }
        ClientImpl(config).use { client ->
            val date = Instant.fromEpochSeconds(
                Clock.System.now().plus((60 * 60).toDuration(DurationUnit.SECONDS)).epochSeconds,
                0
            )

            val input = OperationInput {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                body = ByteStream.fromString("Hello oss.")
            }

            // enable fix time
            mockHandler.clear()
            assertNull(mockHandler.requests)
            mockHandler.responses = mutableListOf()
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 403,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                    body = ByteStream.fromString(
                        buildString {
                            append("<Error>")
                            append("<Code>RequestTimeTooSkewed</Code>")
                            append(
                                "<Message>The difference between the request time and the current time is too large.</Message>"
                            )
                            append(
                                "<ServerTime>${date.format(
                                    DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
                                )}</ServerTime>"
                            )
                            append("</Error>")
                        }
                    )
                )
            )
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 200,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET)),
                )
            )

            var output = client.execute(input, null)
            assertEquals(2, mockHandler.requests?.size)
            assertEquals(output.statusCode, 200)
            assertTrue(Instant.parse(output.headers["request-date"]!!, DateTimeComponents.Formats.RFC_1123) >= date)

            // use header date
            mockHandler.clear()
            assertNull(mockHandler.requests)
            mockHandler.responses = mutableListOf()
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 403,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                    body = ByteStream.fromString(
                        buildString {
                            append("<Error>")
                            append("<Code>RequestTimeTooSkewed</Code>")
                            append(
                                "<Message>The difference between the request time and the current time is too large.</Message>"
                            )
                            append("</Error>")
                        }
                    )
                )
            )
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 200,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                )
            )

            output = client.execute(input, null)
            assertEquals(2, mockHandler.requests?.size)
            assertEquals(output.statusCode, 200)
            assertTrue(Instant.parse(output.headers["request-date"]!!, DateTimeComponents.Formats.RFC_1123) >= date)
        }

        config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            signatureVersion = "v4"
            httpTransport = mockHandler
        }
        // disable fix time
        ClientImpl(config).use { client ->
            val date = Instant.fromEpochSeconds(
                Clock.System.now().plus((60 * 60).toDuration(DurationUnit.SECONDS)).epochSeconds,
                0
            )

            val input = OperationInput {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                body = ByteStream.fromString("Hello oss.")
            }

            // fix time for v4
            mockHandler.clear()
            assertNull(mockHandler.requests)
            mockHandler.responses = mutableListOf()
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 400,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                    body = ByteStream.fromString(
                        buildString {
                            append("<Error>")
                            append("<Code>InvalidArgument</Code>")
                            append("<Message>Invalid signing date in Authorization header.</Message>")
                            append("</Error>")
                        }
                    )
                )
            )
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 200,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                )
            )

            val output = client.execute(input, null)
            assertEquals(2, mockHandler.requests?.size)
            assertEquals(output.statusCode, 200)
            assertTrue(Instant.parse(output.headers["request-date"]!!, DateTimeComponents.Formats.RFC_1123) >= date)
        }

        // disable fix time
        ClientImpl(
            config,
            optFns = listOf { opts ->
                opts.copy {
                    featureFlags?.remove(FeatureFlagsType.CORRECT_CLOCK_SKEW)
                }
            }
        ).use { client ->
            val date = Clock.System.now().plus((60 * 60).toDuration(DurationUnit.SECONDS))

            val input = OperationInput {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                body = ByteStream.fromString("Hello oss.")
            }

            mockHandler.clear()
            assertNull(mockHandler.requests)
            mockHandler.responses = mutableListOf()
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 403,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                    body = ByteStream.fromString(
                        buildString {
                            append("<Error>")
                            append("<Code>RequestTimeTooSkewed</Code>")
                            append(
                                "<Message>The difference between the request time and the current time is too large.</Message>"
                            )
                            append(
                                "<ServerTime>${date.format(
                                    DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
                                )}</ServerTime>"
                            )
                            append("</Error>")
                        }
                    )
                )
            )
            mockHandler.responses?.add(
                ResponseMessage(
                    statusCode = 200,
                    headers = mutableMapOf("Date" to date.format(DateTimeComponents.Formats.RFC_1123)),
                )
            )

            val output = client.execute(input, null)
            assertEquals(2, mockHandler.requests?.size)
            assertEquals(output.statusCode, 200)
            assertTrue(Instant.parse(output.headers["request-date"]!!, DateTimeComponents.Formats.RFC_1123) <= date)
        }
    }
}
