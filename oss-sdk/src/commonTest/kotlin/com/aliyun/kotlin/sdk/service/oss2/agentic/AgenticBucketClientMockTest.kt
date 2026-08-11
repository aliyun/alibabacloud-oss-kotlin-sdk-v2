package com.aliyun.kotlin.sdk.service.oss2.agentic

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OperationInput
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.CreateAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListBucketSpacesRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestOptions
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AgenticBucketClientMockTest {

    private class UrlCaptureTransport(private val body: String = "") : HttpTransport {
        var requestUrl: String = ""
        var requestMethod: String = ""
        var userAgent: String = ""

        override suspend fun execute(request: RequestMessage, options: RequestOptions): ResponseMessage {
            requestUrl = request.url
            requestMethod = request.method
            userAgent = request.headers["User-Agent"] ?: ""
            return ResponseMessage(statusCode = 200, body = ByteStream.fromString(body))
        }
    }

    private fun agenticConfig(
        region: String,
        accountId: String,
        transport: HttpTransport,
    ) = ClientConfiguration().apply {
        this.region = region
        this.accountId = accountId
        credentialsProvider = StaticCredentialsProvider("ak", "sk")
        httpTransport = transport
    }

    private fun assertBaseAndParams(url: String, expectBase: String, params: List<String>) {
        val parts = url.split("?", limit = 2)
        assertEquals(expectBase, parts[0])
        val query = parts.getOrNull(1) ?: ""
        val keys = query.split("&").filter { it.isNotEmpty() }.map { it.substringBefore("=") }
        params.forEach { assertContains(keys, it) }
        assertEquals(params.size, keys.size)
    }

    @Test
    fun createAgenticBucket() {
        val transport = UrlCaptureTransport()
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.createAgenticBucket(CreateAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("PUT", transport.requestMethod)
        assertEquals(
            "https://my-agentic-123456-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun deleteAgenticBucket() {
        val transport = UrlCaptureTransport()
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.deleteAgenticBucket(DeleteAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("DELETE", transport.requestMethod)
        assertEquals(
            "https://my-agentic-123456-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun getAgenticBucket() {
        val transport = UrlCaptureTransport("<AgenticBucketInfo></AgenticBucketInfo>")
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("GET", transport.requestMethod)
        assertEquals(
            "https://my-agentic-123456-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun listAgenticBuckets() {
        val transport = UrlCaptureTransport("<ListAgenticBucketsResult></ListAgenticBucketsResult>")
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.listAgenticBuckets(ListAgenticBucketsRequest {})
            }
        }
        assertEquals("GET", transport.requestMethod)
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun putAgenticBucketStatus() {
        val transport = UrlCaptureTransport()
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.putAgenticBucketStatus(
                    PutAgenticBucketStatusRequest {
                        bucket = "my-agentic"
                        agenticBucketStatus = AgenticBucketStatus { status = "enabled" }
                    },
                )
            }
        }
        assertEquals("PUT", transport.requestMethod)
        assertBaseAndParams(
            transport.requestUrl,
            "https://my-agentic-123456-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/",
            listOf("agenticBucket", "status"),
        )
    }

    @Test
    fun listBucketSpaces() {
        val transport = UrlCaptureTransport("<ListBucketSpacesResult></ListBucketSpacesResult>")
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.listBucketSpaces(ListBucketSpacesRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("GET", transport.requestMethod)
        assertBaseAndParams(
            transport.requestUrl,
            "https://my-agentic-123456-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/",
            listOf("agenticBucket", "bucketSpace"),
        )
    }

    @Test
    fun regionInUrl() {
        val transport = UrlCaptureTransport("<AgenticBucketInfo></AgenticBucketInfo>")
        AgenticBucketClient(agenticConfig("cn-shanghai", "999888", transport)).use { client ->
            runBlocking {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = "test-bucket" })
            }
        }
        assertEquals(
            "https://test-bucket-999888-cn-shanghai-ab-apsr.oss-cn-shanghai.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun bucketSpaceClientPutObject() {
        val transport = UrlCaptureTransport()
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            accountId = "123456"
            endpoint = "user-cname.test.com"
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = transport
        }
        BucketSpaceClient.create(config).use { client ->
            runBlocking {
                client.invokeOperation(
                    OperationInput {
                        opName = "PutObject"
                        method = "PUT"
                        bucket = "my-space"
                        key = "test.txt"
                    },
                )
            }
        }
        assertEquals("PUT", transport.requestMethod)
        assertEquals(
            "https://my-space-123456-cn-hangzhou-bs-apsr.user-cname.test.com/test.txt",
            transport.requestUrl,
        )
    }

    @Test
    fun bucketSpaceClientPutObjectInternal() {
        val transport = UrlCaptureTransport()
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            accountId = "123456"
            useInternalEndpoint = true
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = transport
        }
        BucketSpaceClient.create(config).use { client ->
            runBlocking {
                client.invokeOperation(
                    OperationInput {
                        opName = "PutObject"
                        method = "PUT"
                        bucket = "my-space"
                        key = "test.txt"
                    },
                )
            }
        }
        assertEquals(
            "https://my-space-123456-cn-hangzhou-bs-apsr.oss-cn-hangzhou-internal.aliyuncs.com/test.txt",
            transport.requestUrl,
        )
    }

    @Test
    fun getAgenticBucketPathStyle() {
        val transport = UrlCaptureTransport("<AgenticBucketInfo></AgenticBucketInfo>")
        val config = agenticConfig("cn-hangzhou", "123456", transport).apply { usePathStyle = true }
        AgenticBucketClient(config).use { client ->
            runBlocking {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("GET", transport.requestMethod)
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/my-agentic-123456-cn-hangzhou-ab-apsr/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun listAgenticBucketsPathStyle() {
        val transport = UrlCaptureTransport("<ListAgenticBucketsResult></ListAgenticBucketsResult>")
        val config = agenticConfig("cn-hangzhou", "123456", transport).apply { usePathStyle = true }
        AgenticBucketClient(config).use { client ->
            runBlocking {
                client.listAgenticBuckets(ListAgenticBucketsRequest {})
            }
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun bucketSpaceClientPutObjectPathStyle() {
        val transport = UrlCaptureTransport()
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            accountId = "123456"
            usePathStyle = true
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = transport
        }
        BucketSpaceClient.create(config).use { client ->
            runBlocking {
                client.invokeOperation(
                    OperationInput {
                        opName = "PutObject"
                        method = "PUT"
                        bucket = "my-space"
                        key = "test.txt"
                    },
                )
            }
        }
        assertEquals("PUT", transport.requestMethod)
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/my-space-123456-cn-hangzhou-bs-apsr/test.txt",
            transport.requestUrl,
        )
    }

    @Test
    fun getAgenticBucketVirtualHostedAlias() {
        val transport = UrlCaptureTransport("<AgenticBucketInfo></AgenticBucketInfo>")
        val config = agenticConfig("cn-hangzhou", "123456", transport).apply { useVirtualHostedAlias = true }
        AgenticBucketClient(config).use { client ->
            runBlocking {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertEquals("GET", transport.requestMethod)
        assertEquals(
            "https://my-agentic-alias-ab-apsr.oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun listAgenticBucketsVirtualHostedAlias() {
        val transport = UrlCaptureTransport("<ListAgenticBucketsResult></ListAgenticBucketsResult>")
        val config = agenticConfig("cn-hangzhou", "123456", transport).apply { useVirtualHostedAlias = true }
        AgenticBucketClient(config).use { client ->
            runBlocking {
                client.listAgenticBuckets(ListAgenticBucketsRequest {})
            }
        }
        assertEquals(
            "https://oss-cn-hangzhou.aliyuncs.com/?agenticBucket=",
            transport.requestUrl,
        )
    }

    @Test
    fun bucketSpaceClientPutObjectVirtualHostedAlias() {
        val transport = UrlCaptureTransport()
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            accountId = "123456"
            useVirtualHostedAlias = true
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = transport
        }
        BucketSpaceClient.create(config).use { client ->
            runBlocking {
                client.invokeOperation(
                    OperationInput {
                        opName = "PutObject"
                        method = "PUT"
                        bucket = "my-space"
                        key = "test.txt"
                    },
                )
            }
        }
        assertEquals("PUT", transport.requestMethod)
        assertEquals(
            "https://my-space-alias-bs-apsr.oss-cn-hangzhou.aliyuncs.com/test.txt",
            transport.requestUrl,
        )
    }

    @Test
    fun agenticClientUserAgent() {
        val transport = UrlCaptureTransport("<AgenticBucketInfo></AgenticBucketInfo>")
        AgenticBucketClient(agenticConfig("cn-hangzhou", "123456", transport)).use { client ->
            runBlocking {
                client.getAgenticBucket(GetAgenticBucketRequest { bucket = "my-agentic" })
            }
        }
        assertContains(transport.userAgent, "agentic-client")
    }

    @Test
    fun bucketSpaceClientUserAgent() {
        val transport = UrlCaptureTransport()
        val config = ClientConfiguration().apply {
            region = "cn-hangzhou"
            accountId = "123456"
            useInternalEndpoint = true
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
            httpTransport = transport
        }
        BucketSpaceClient.create(config).use { client ->
            runBlocking {
                client.invokeOperation(
                    OperationInput {
                        opName = "PutObject"
                        method = "PUT"
                        bucket = "my-space"
                        key = "test.txt"
                    },
                )
            }
        }
        assertContains(transport.userAgent, "agentic-client")
    }
}
