@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import kotlinx.coroutines.runBlocking
import java.net.URL
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.ExperimentalTime

class SignerV4Test {

    @Test
    fun testAuthHeader() {
        val provider = StaticCredentialsProvider("ak", "sk")

        val cred = Credentials("ak", "sk")
        val request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        val headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "text/plain")
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD")
        request.headers.putAll(headers)

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()

        runBlocking {
            signer.sign(context)
            val expectedAuth =
                "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,Signature=e21d18daa82167720f9b1047ae7e7f1ce7cb77a31e8203a7d5f4624fa0284afe"
            assertEquals(expectedAuth, context.request!!.headers.get("Authorization"))
        }
    }

    @Test
    fun testAuthHeaderWithToken() {
        val cred = Credentials("ak", "sk", "token")
        val request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        val headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "text/plain")
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD")
        request.headers.putAll(headers)

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702784856L // Instant.fromEpochSeconds(1702784856)

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()

        runBlocking {
            signer.sign(context)
            val expectedAuth =
                "OSS4-HMAC-SHA256 Credential=ak/20231217/cn-hangzhou/oss/aliyun_v4_request,Signature=b94a3f999cf85bcdc00d332fbd3734ba03e48382c36fa4d5af5df817395bd9ea"
            assertEquals(expectedAuth, context.request!!.headers["Authorization"])
        }
    }

    @Test
    fun testAuthHeaderWithAdditionalHeaders() {
        val cred = Credentials("ak", "sk")
        var request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        var headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "text/plain")
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD")
        request.headers.putAll(headers)

        var context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702747512L // Instant.fromEpochSeconds(1702747512)
        context.additionalHeaders = mutableListOf<String>("ZAbc", "abc")

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()
        val expectedAuth =
            "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,AdditionalHeaders=abc;zabc,Signature=4a4183c187c07c8947db7620deb0a6b38d9fbdd34187b6dbaccb316fa251212f"

        runBlocking {
            signer.sign(context)
            assertEquals(expectedAuth, context.request!!.headers["Authorization"])
        }

        // With default signed header
        request = RequestMessage().apply {
            this.method = "PUT"
            this.url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        headers = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "text/plain")
        headers.put("x-oss-content-sha256", "UNSIGNED-PAYLOAD")
        request.headers.putAll(headers)

        context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702747512L // Instant.fromEpochSeconds(1702747512)
        context.additionalHeaders = mutableListOf<String>("x-oss-no-exist", "ZAbc", "x-oss-head1", "abc")

        val sb2 = StringBuilder()
        sb2.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb2.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb2.toString()
        }

        runBlocking {
            signer.sign(context)
            assertEquals(expectedAuth, context.request!!.headers["Authorization"])
        }
    }

    @Test
    fun testAuthQuery() {
        val cred = Credentials("ak", "sk")
        val request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        val headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "application/octet-stream")
        request.headers.putAll(headers)

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702781677L // Instant.fromEpochSeconds(1702781677)
        context.expirationInEpoch = 1702782276L // Instant.fromEpochSeconds(1702782276)
        context.isAuthMethodQuery = true

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()
        runBlocking {
            signer.sign(context)
        }

        assertNotNull(context.request)
        assertContains(context.request!!.url, "x-oss-signature-version=OSS4-HMAC-SHA256")
        assertContains(context.request!!.url, "x-oss-expires=599")
        assertContains(context.request!!.url, "x-oss-credential=ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request")
        assertContains(
            context.request!!.url,
            "x-oss-signature=a39966c61718be0d5b14e668088b3fa07601033f6518ac7b523100014269c0fe"
        )
    }

    @Test
    fun testAuthQueryWithToken() {
        val cred = Credentials("ak", "sk", "token")
        val request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        val headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "application/octet-stream")
        request.headers.putAll(headers)

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702785388L // Instant.fromEpochSeconds(1702785388)
        context.expirationInEpoch = 1702785987L // Instant.fromEpochSeconds(1702785987)
        context.isAuthMethodQuery = true

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", null)

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()
        runBlocking {
            signer.sign(context)
        }

        assertNotNull(context.request)
        assertContains(context.request!!.url, "x-oss-signature-version=OSS4-HMAC-SHA256")
        assertContains(context.request!!.url, "x-oss-expires=599")
        assertContains(context.request!!.url, "x-oss-date=20231217T035628Z")
        assertContains(context.request!!.url, "x-oss-credential=ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request")
        assertContains(
            context.request!!.url,
            "x-oss-signature=3817ac9d206cd6dfc90f1c09c00be45005602e55898f26f5ddb06d7892e1f8b5"
        )
        assertContains(context.request!!.url, "x-oss-security-token=token")
    }

    @Test
    fun testAuthQueryWithAdditionalHeaders() {
        val cred = Credentials("ak", "sk")
        var request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        var headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "application/octet-stream")
        request.headers.putAll(headers)

        var context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702783809L // Instant.fromEpochSeconds(1702783809)
        context.expirationInEpoch = 1702784408L // Instant.fromEpochSeconds(1702784408)
        context.isAuthMethodQuery = true
        context.additionalHeaders = mutableListOf("ZAbc", "abc")

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()
        runBlocking {
            signer.sign(context)
        }

        assertNotNull(context.request)
        assertContains(context.request!!.url, "x-oss-signature-version=OSS4-HMAC-SHA256")
        assertContains(context.request!!.url, "x-oss-expires=599")
        assertContains(context.request!!.url, "x-oss-date=20231217T033009Z")
        assertContains(context.request!!.url, "x-oss-credential=ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request")
        assertContains(
            context.request!!.url,
            "x-oss-signature=6bd984bfe531afb6db1f7550983a741b103a8c58e5e14f83ea474c2322dfa2b7"
        )
        assertContains(context.request!!.url, "x-oss-additional-headers=abc%3Bzabc")

        // With default signed header
        request = RequestMessage().apply {
            this.method = "PUT"
            this.url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        headers = mutableMapOf()
        headers.put("x-oss-head1", "value")
        headers.put("abc", "value")
        headers.put("ZAbc", "value")
        headers.put("XYZ", "value")
        headers.put("content-type", "application/octet-stream")
        request.headers.putAll(headers)

        context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702783809L // Instant.fromEpochSeconds(1702783809)
        context.expirationInEpoch = 1702784408L // Instant.fromEpochSeconds(1702784408)
        context.isAuthMethodQuery = true
        context.additionalHeaders = mutableListOf("x-oss-no-exist", "ZAbc", "x-oss-head1", "abc")

        val sb2 = StringBuilder()
        sb2.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb2.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb2.toString()
        }

        runBlocking {
            signer.sign(context)
        }
        assertNotNull(context.request)
        assertContains(context.request!!.url, "x-oss-signature-version=OSS4-HMAC-SHA256")
        assertContains(context.request!!.url, "x-oss-expires=599")
        assertContains(context.request!!.url, "x-oss-date=20231217T033009Z")
        assertContains(context.request!!.url, "x-oss-credential=ak%2F20231217%2Fcn-hangzhou%2Foss%2Faliyun_v4_request")
        assertContains(
            context.request!!.url,
            "x-oss-signature=6bd984bfe531afb6db1f7550983a741b103a8c58e5e14f83ea474c2322dfa2b7"
        )
        assertContains(context.request!!.url, "x-oss-additional-headers=abc%3Bzabc")
    }

    @Test
    fun testAuthHeaderCanonicalHeadersSorting() {
        val cred = Credentials("ak", "sk")
        val request = RequestMessage().apply {
            method = "PUT"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com"
        }

        val headers: MutableMap<String, String> = mutableMapOf()
        headers.put("x-oss-meta-zzz", "value1")
        headers.put("x-oss-meta-aaa", "value2")
        headers.put("x-oss-meta-123", "value3")
        headers.put("x-oss-meta-abc123", "value4")
        headers.put("x-oss-meta-abc-123", "value5")
        headers.put("x-oss-meta-abc_123", "value6")
        headers.put("x-oss-meta-ABC", "value7"); // uppercase
        headers.put("content-type", "application/json")
        headers.put("x-oss-date", "20250814T080624Z")
        headers.put("content-md5", "md5hash")
        request.headers.putAll(headers)

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "1234+-/123/1.txt"
        context.request = request
        context.credentials = cred
        context.product = "oss"
        context.region = "cn-hangzhou"
        context.signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)

        val parameters: MutableMap<String, String?> = mutableMapOf()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = URL(request.url)
        assertNotNull(url)

        val sb = StringBuilder()
        sb.append(url.protocol)
            .append("://")
            .append(url.authority)
            .append(url.path)

        if (query.isNotEmpty()) {
            sb.append("?").append(query)
        }

        context.request = RequestMessage().apply {
            this.method = request.method
            this.headers = request.headers
            this.url = sb.toString()
        }

        val signer = SignerV4()

        runBlocking {
            signer.sign(context)
            val expectedAuth =
                "OSS4-HMAC-SHA256 Credential=ak/20231216/cn-hangzhou/oss/aliyun_v4_request,Signature=3e9a6ebd7789767059589cc62116d9e4ebc4787e11b937f1683d0f344cf2693e"
            assertEquals(expectedAuth, context.request!!.headers.get("Authorization"))
        }
    }
}
