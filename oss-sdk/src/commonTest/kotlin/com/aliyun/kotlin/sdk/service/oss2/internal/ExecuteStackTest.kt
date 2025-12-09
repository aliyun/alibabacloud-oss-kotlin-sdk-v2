package com.aliyun.kotlin.sdk.service.oss2.internal

import com.aliyun.kotlin.sdk.service.oss2.credentials.AnonymousCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.retry.NopRetryer
import com.aliyun.kotlin.sdk.service.oss2.signer.NopSigner
import com.aliyun.kotlin.sdk.service.oss2.transport.HttpTransport
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestOptions
import com.aliyun.kotlin.sdk.service.oss2.transport.ResponseMessage
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class ExecuteStackTest {
    internal class MockHttpClient : HttpTransport {
        override suspend fun execute(request: RequestMessage, options: RequestOptions): ResponseMessage {
            return ResponseMessage()
        }
    }

    @Test
    fun testConstructor() {
        // build execute stack
        val transport = TransportExecuteMiddleware(MockHttpClient())
        val stack = ExecuteStack(transport)

        stack.push({ x -> RetryerExecuteMiddleware(x, NopRetryer()) }, "Retryer")
        stack.push({ x -> SignerExecuteMiddleware(x, AnonymousCredentialsProvider(), NopSigner()) }, "Signer")
        stack.push({ x -> ResponseCheckerExecuteMiddleware(x) }, "ResponseChecker")

        runBlocking {
            stack.execute(RequestMessage(), ExecuteContext())
        }
    }
}
