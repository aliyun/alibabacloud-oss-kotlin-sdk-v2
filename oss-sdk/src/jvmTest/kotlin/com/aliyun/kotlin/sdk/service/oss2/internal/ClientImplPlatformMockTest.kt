package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.OperationMetadataKey
import com.aliyun.kotlin.sdk.service.oss2.OperationOptions
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.hash.CRC64Observer
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressObserver
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.utils.MapUtils
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

class ClientImplPlatformMockTest {
    var mockWebServer_: MockWebServer? = null

    @BeforeTest
    fun startMockWebServer() {
        if (mockWebServer_ == null) {
            mockWebServer_ = MockWebServer()
            mockWebServer_?.start()
        }
    }

    @AfterTest
    fun closeMockWebServer() {
        mockWebServer_?.close()
        mockWebServer_ = null
    }

    @Test
    fun testUploadObserverNormal_useCRCObserver() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)
        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // default
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )

            var input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            var output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])

            // empty observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )

            input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf()

            output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )
            input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val observer = CRC64Observer()
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])
            assertEquals(hashCrc64ecma, observer.checksum.digestValue.toULong().toString())
        }
    }

    @Test
    fun testUploadObserverRetryable_useCRCObserver() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(500)
                    .build()
            )
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val observer = CRC64Observer()
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])
            assertEquals(hashCrc64ecma, observer.checksum.digestValue.toULong().toString())
            assertEquals(2, mockWebServer.requestCount)
        }
    }

    @Test
    fun testUploadObserverNormal_useProgObserver() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val length = 512 * 1024 + 123
            val data = "1".repeat(length)

            // set progress observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val progressListener = object : ProgressListener {
                var totalBytesTransferred: Long = 0
                var totalBytesExpected: Long = 0

                override fun onProgress(
                    bytesIncrement: Long,
                    totalBytesTransferred: Long,
                    totalBytesExpected: Long
                ) {
                    // println("Transferred: ${totalBytesTransferred}，Expected: ${totalBytesExpected}")
                    this.totalBytesTransferred = totalBytesTransferred
                    this.totalBytesExpected = totalBytesExpected
                }
            }

            val observer = ProgressObserver(progressListener, length.toLong())
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)

            assertEquals(length.toLong(), progressListener.totalBytesExpected)
            assertEquals(length.toLong(), progressListener.totalBytesTransferred)
        }
        mockWebServer.close()
    }

    @Test
    fun testUploadObserverRetryable_useProgObserver() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val length = 512 * 1024 + 123
            val data = "1".repeat(length)

            // set progress observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(500)
                    .build()
            )
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val progressListener = object : ProgressListener {
                var totalBytesTransferred: Long = 0
                var totalBytesExpected: Long = 0

                override fun onProgress(
                    bytesIncrement: Long,
                    totalBytesTransferred: Long,
                    totalBytesExpected: Long
                ) {
                    // println("Transferred: ${totalBytesTransferred}，Expected: ${totalBytesExpected}")
                    this.totalBytesTransferred = totalBytesTransferred
                    this.totalBytesExpected = totalBytesExpected
                }
            }

            val observer = ProgressObserver(progressListener, length.toLong())
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)

            assertEquals(length.toLong(), progressListener.totalBytesExpected)
            assertEquals(length.toLong(), progressListener.totalBytesTransferred)
            assertEquals(2, mockWebServer.requestCount)
        }
    }

    @Test
    fun testUploadDataAndCheckResponseCrc() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val observer = CRC64Observer()
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val handler = ChecksumUploadResponseHandler(observer.checksum)
            input.opMetadata[OperationMetadataKey.RESPONSE_HANDLE] = listOf(handler)

            val output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])
            assertEquals(hashCrc64ecma, observer.checksum.digestValue.toULong().toString())
        }
    }

    @Test
    fun testUploadDataAndCheckResponseCrc_throwInconsistentException() {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            retryMaxAttempts = 1
        }

        ClientImpl(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", "invalid")
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val observer = CRC64Observer()
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val handler = ChecksumUploadResponseHandler(observer.checksum)
            input.opMetadata[OperationMetadataKey.RESPONSE_HANDLE] = listOf(handler)

            runBlocking {
                try {
                    val output = client.execute(input, OperationOptions.Default)
                    assertFails { "Should not here" }
                } catch (e: Exception) {
                    assertIs<InconsistentException>(e.cause)
                    val ie = e.cause as InconsistentException
                    assertEquals(hashCrc64ecma, ie.clientCrc)
                    assertEquals("invalid", ie.serverCrc)
                }
            }
        }
    }

    @Test
    fun testUploadDataAndCheckResponseCrc_retryInconsistentException() = runBlocking {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        }

        ClientImpl(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", "invalid")
                    .build()
            )
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", hashCrc64ecma)
                    .build()
            )

            val input = OperationInput.build {
                opName = "PutObject"
                method = "PUT"
                bucket = "bucket"
                key = "key"
                headers = MapUtils.headersMap().apply {
                    put("Content-Type", "text/plain")
                }
                body = ByteStream.fromString(data)
            }

            val observer = CRC64Observer()
            input.opMetadata[OperationMetadataKey.UPLOAD_OBSERVER] = listOf(observer)

            val handler = ChecksumUploadResponseHandler(observer.checksum)
            input.opMetadata[OperationMetadataKey.RESPONSE_HANDLE] = listOf(handler)

            val output = client.execute(input, OperationOptions.Default)
            assertEquals(200, output.statusCode)
            assertEquals(hashCrc64ecma, output.headers["x-oss-hash-crc64ecma"])
            assertEquals(hashCrc64ecma, observer.checksum.digestValue.toULong().toString())
            assertEquals(2, mockWebServer.requestCount)
        }
    }
}
