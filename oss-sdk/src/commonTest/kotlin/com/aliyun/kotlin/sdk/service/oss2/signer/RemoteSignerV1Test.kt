package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.hash.hmacSha1
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import kotlinx.coroutines.test.runTest
import kotlin.io.encoding.Base64
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

private class SignatureImpl : SignatureDelegate {
    override suspend fun signature(info: Map<String, String>): Map<String, String> {
        val stringToSign = info["stringToSign"] ?: ""

        val accessKeySecret = "sk"
        val signature = calcSignature(accessKeySecret, stringToSign)

        return mapOf(
            "signature" to signature
        )
    }

    private fun calcSignature(
        accessKeySecret: String,
        stringToSign: String
    ): String {
        val signatureBytes = stringToSign.toByteArray().hmacSha1(accessKeySecret.toByteArray())
        return Base64.encode(signatureBytes)
    }
}

class RemoteSignerV1Test {

    @Test
    @OptIn(ExperimentalTime::class)
    fun testAuthHeader() = runTest {
        val ak = "ak"
        val sk = "sk"
        val signTime = 1_672_223_261L // Instant.fromEpochSeconds(1_672_223_261)

        val credentials = Credentials(ak, sk)
        var signingContext = SigningContext().apply {
            bucket = "examplebucket"
            key = "nelson"
            this.signTimeInEpoch = signTime
            this.credentials = credentials
        }
        val signer = RemoteSignerV1(SignatureImpl())

        // case 1
        var url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com"
        var request = RequestMessage().apply {
            method = "PUT"
            this.url = url
        }
        request.headers["Content-MD5"] = "eB5eJF1ptWaXm4bijSPyxw=="
        request.headers["Content-Type"] = "text/html"
        request.headers["x-oss-meta-author"] = "alice"
        request.headers["x-oss-meta-magic"] = "abracadabra"
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"
        signingContext.request = request
        signer.sign(signingContext)
        request = signingContext.request!!
        assertEquals(request.headers["Authorization"], "OSS ak:kSHKmLxlyEAKtZPkJhG9bZb5k7M=")

        // With Signed Parameter
        url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com?acl"
        request = RequestMessage().apply {
            method = "PUT"
            this.url = url
        }
        request.headers["Content-MD5"] = "eB5eJF1ptWaXm4bijSPyxw=="
        request.headers["Content-Type"] = "text/html"
        request.headers["x-oss-meta-author"] = "alice"
        request.headers["x-oss-meta-magic"] = "abracadabra"
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"
        signingContext.request = request
        signer.sign(signingContext)
        request = signingContext.request!!
        assertEquals(request.headers["Authorization"], "OSS ak:/afkugFbmWDQ967j1vr6zygBLQk=")

        // With signed & non-signed Parameter & non-signed headers
        url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com?acl&non-resousce=123"
        request = RequestMessage().apply {
            method = "PUT"
            this.url = url
        }
        request.headers["Content-MD5"] = "eB5eJF1ptWaXm4bijSPyxw=="
        request.headers["Content-Type"] = "text/html"
        request.headers["x-oss-meta-author"] = "alice"
        request.headers["x-oss-meta-magic"] = "abracadabra"
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"
        request.headers["User-Agent"] = "test"
        signingContext.request = request
        signer.sign(signingContext)
        request = signingContext.request!!
        assertEquals(request.headers["Authorization"], "OSS ak:/afkugFbmWDQ967j1vr6zygBLQk=")

        // With sub-resource
        url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com?resourceGroup&non-resousce=null"
        signingContext = SigningContext().apply {
            bucket = "examplebucket"
            this.signTimeInEpoch = signTime
            this.credentials = credentials
            subResource = listOf("resourceGroup")
        }
        request = RequestMessage().apply {
            method = "GET"
            this.url = url
        }
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"
        signingContext.request = request
        signer.sign(signingContext)
        request = signingContext.request!!
        assertEquals(request.headers["Authorization"], "OSS ak:vkQmfuUDyi1uDi3bKt67oemssIs=")
    }

    @Test
    @OptIn(ExperimentalTime::class)
    fun testAuthQuery() = runTest {
        val ak = "ak"
        val sk = "sk"
        val signTime = 1_699_807_420L // Instant.fromEpochSeconds(1_699_807_420)

        val credentials = Credentials(ak, sk)
        val signingContext = SigningContext().apply {
            bucket = "bucket"
            key = "key"
            this.signTimeInEpoch = signTime
            this.credentials = credentials
            expirationInEpoch = signTime
        }
        signingContext.isAuthMethodQuery = true
        val signer = RemoteSignerV1(SignatureImpl())

        // case 1
        val url = "http://bucket.oss-cn-hangzhou.aliyuncs.com/key?versionId=versionId"
        var request = RequestMessage().apply {
            method = "GET"
            this.url = url
        }
        signingContext.request = request
        signer.sign(signingContext)
        request = signingContext.request!!
        val queryItems = extractParamsWithoutDecode(request.url)
        assertEquals(queryItems["OSSAccessKeyId"], "ak")
        assertEquals(queryItems["Expires"], "1699807420")
        assertEquals(queryItems["Signature"], "dcLTea%2BYh9ApirQ8o8dOPqtvJXQ%3D")
        assertEquals(queryItems["versionId"], "versionId")
    }

    fun extractParamsWithoutDecode(uri: String): MutableMap<String, String> {
        val start = uri.indexOf("?")

        // no query string
        if (start < 0) {
            return mutableMapOf()
        }

        val rawQuery = uri.substring(start + 1)

        if (rawQuery.isNotEmpty()) {
            return rawQuery
                .split("&").associate { segment ->
                    val parts = segment.split("=")
                    val key = parts[0]
                    val value = when (parts.size) {
                        1 -> ""
                        2 -> parts[1]
                        else -> throw IllegalArgumentException("invalid query string segment $segment")
                    }
                    key to value
                }
                .toMutableMap()
        }

        return mutableMapOf()
    }
}
