package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.InconsistentException
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import kotlinx.coroutines.test.runTest
import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClientPlatformMockTest {

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
    fun testGetObjectToFileAndCheckCrc_throwInconsistentException() = runTest {
        val mockWebServer = requireNotNull(mockWebServer_)

        val mockWebServerUrl = mockWebServer.url("/")

        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            endpoint = "http://${mockWebServerUrl.host}:${mockWebServer.port}"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            retryMaxAttempts = 1
        }

        OSSClient.create(config).use { client ->
            val data = "1234567890abcdefjhijklmnopqrstuvwxyz"
            val hashCrc64ecma = "17769217960454060510"

            // set crc observer
            mockWebServer.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("x-oss-hash-crc64ecma", "invalid")
                    .body(data)
                    .build()
            )

            val request = GetObjectRequest {
                bucket = "bucket"
                key = "key"
            }
            val exception =
                assertFailsWith<InconsistentException> {
                    client.getObjectToFile(request, Path("$SystemTemporaryDirectory/kotlin-sdk-test/file"))
                }
            assertEquals(hashCrc64ecma, exception.clientCrc)
            assertEquals("invalid", exception.serverCrc)
        }
    }
}
