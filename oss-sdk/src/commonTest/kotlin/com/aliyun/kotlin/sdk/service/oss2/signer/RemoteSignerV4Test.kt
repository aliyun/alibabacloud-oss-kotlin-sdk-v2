package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.hash.hmacSha256
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.utils.HexUtils
import com.aliyun.kotlin.sdk.service.oss2.utils.urlEncode
import com.aliyun.kotlin.sdk.service.oss2.utils.urlEncodePath
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

private class SignatureV4Impl : SignatureDelegate {
    override suspend fun signature(info: Map<String, String>): Map<String, String> {
        val region = info["region"] ?: ""
        val product = info["product"] ?: ""
        val date = info["date"] ?: ""
        val stringToSign = info["stringToSign"] ?: ""

        val accessKeySecret = "sk"
        val signature = calcSignature(
            accessKeySecret,
            date,
            region,
            product,
            stringToSign
        )

        return mapOf(
            "signature" to signature
        )
    }

    private fun calcSignature(
        accessKeySecret: String,
        date: String,
        region: String,
        product: String,
        stringToSign: String
    ): String {
        val key = "aliyun_v4$accessKeySecret".toByteArray()
        val dateKey = date.toByteArray().hmacSha256(key)
        val regionKey = region.toByteArray().hmacSha256(dateKey)
        val productKey = product.toByteArray().hmacSha256(regionKey)
        val requestKey = "aliyun_v4_request".toByteArray().hmacSha256(productKey)
        val signatureBytes = stringToSign.toByteArray().hmacSha256(requestKey)

        return HexUtils.encodeHex(signatureBytes)
    }
}

class RemoteSignerV4Test {

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthHeader() = runTest {
        val credentials = Credentials("ak", "sk")
        val path = "1234+-/123/1.txt"
        val host = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        val parameters: Map<String, String?> = mapOf(
            "param1" to "value1",
            "+param1" to "value3",
            "|param1" to "value4",
            "+param2" to "",
            "|param2" to "",
            "param2" to "",
        )
        val queries = parameters.map {
            "${it.key.urlEncode()}=${it.value?.urlEncode()}"
        }.joinToString("&")

        var request = RequestMessage().apply {
            method = "PUT"
            this.url = "$host/${path.urlEncodePath()}?$queries"
        }
        request.headers = mutableMapOf<String, String>(
            "x-oss-head1" to "value",
            "abc" to "value",
            "ZAbc" to "value",
            "XYZ" to "value",
            "content-type" to "text/plain",
            "x-oss-content-sha256" to "UNSIGNED-PAYLOAD",
        )

        val signTime = 1_702_743_657L // Instant.fromEpochSeconds(1_702_743_657)

        val signingContext = SigningContext().apply {
            bucket = "bucket"
            key = "1234+-/123/1.txt"
            region = "cn-hangzhou"
            product = "oss"
            this.signTimeInEpoch = signTime
            this.credentials = credentials
            this.request = request
        }
        val signer = RemoteSignerV4(SignatureV4Impl())
        signer.sign(signingContext)
        request = signingContext.request!!
        val url = request.url
        val authPat = "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,Signature=e21d18daa82167720f9b1047ae7e7f1ce7cb77a31e8203a7d5f4624fa0284afe"
        assertEquals(authPat, request.headers["Authorization"])

        assertTrue(url.contains("bucket.oss-cn-hangzhou.aliyuncs.com/1234%2B-/123/1.txt?"))
        assertTrue(url.contains("%2Bparam2="))
        assertTrue(url.contains("%2Bparam1=value3&"))
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthQuery() = runTest {
        val credentials = Credentials("ak", "sk")
        val path = "1234+-/123/1.txt"
        val host = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        val parameters: Map<String, String?> = mapOf(
            "param1" to "value1",
            "+param1" to "value3",
            "|param1" to "value4",
            "+param2" to "",
            "|param2" to "",
            "param2" to "",
        )
        val queries = parameters.map {
            "${it.key.urlEncode()}=${it.value?.urlEncode()}"
        }.joinToString("&")

        var request = RequestMessage().apply {
            method = "PUT"
            url = "$host/${path.urlEncodePath()}?$queries"
        }
        request.headers = mutableMapOf<String, String>(
            "x-oss-head1" to "value",
            "abc" to "value",
            "ZAbc" to "value",
            "XYZ" to "value",
            "content-type" to "application/octet-stream",
        )

        val signTime = 1_702_781_677L // Instant.fromEpochSeconds(1_702_781_677)
        val expirationTime = 1_702_782_276L // Instant.fromEpochSeconds(1_702_782_276)

        val signingContext = SigningContext().apply {
            bucket = "bucket"
            key = "1234+-/123/1.txt"
            region = "cn-hangzhou"
            product = "oss"
            this.signTimeInEpoch = signTime
            this.credentials = credentials
            this.request = request
        }
        signingContext.expirationInEpoch = expirationTime
        signingContext.isAuthMethodQuery = true
        val signer = RemoteSignerV4(SignatureV4Impl())
        signer.sign(signingContext)
        request = signingContext.request!!
        val url = request.url

        assertTrue(url.contains("x-oss-signature-version=OSS4-HMAC-SHA256"))
        assertTrue(url.contains("x-oss-expires=599"))
        assertTrue(url.contains("x-oss-credential=ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request"))
        assertTrue(url.contains("x-oss-signature=a39966c61718be0d5b14e668088b3fa07601033f6518ac7b523100014269c0fe"))
        assertFalse(url.contains("x-oss-additional-headers"))

        assertTrue(url.contains("bucket.oss-cn-hangzhou.aliyuncs.com/1234%2B-/123/1.txt?"))
        assertTrue(url.contains("%2Bparam2&"))
        assertTrue(url.contains("%2Bparam1=value3&"))
    }
}
