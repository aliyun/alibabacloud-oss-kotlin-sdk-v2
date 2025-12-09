package com.aliyun.kotlin.sdk.service.oss2.signer

import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.transport.RequestMessage
import com.aliyun.kotlin.sdk.service.oss2.utils.HttpUtils
import kotlinx.coroutines.runBlocking
import kotlin.apply
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.ExperimentalTime

class SignerV1Test {

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthHeader1() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val cred: Credentials = Credentials("ak", "sk")

        val request: RequestMessage = RequestMessage().apply {
            method = "PUT"
            url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com"
        }

        request.headers["Content-MD5"] = "eB5eJF1ptWaXm4bijSPyxw=="
        request.headers["Content-Type"] = "text/html"
        request.headers["x-oss-meta-author"] = "alice"
        request.headers["x-oss-meta-magic"] = "abracadabra"
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"

        val context = SigningContext().apply {
            bucket = "examplebucket"
            key = "nelson"
            this.request = request
            credentials = cred
            signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            assertEquals(
                "PUT\neB5eJF1ptWaXm4bijSPyxw==\ntext/html\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\nx-oss-meta-author:alice\nx-oss-meta-magic:abracadabra\n/examplebucket/nelson",
                context.stringToSign
            )
            assertEquals(
                "OSS ak:kSHKmLxlyEAKtZPkJhG9bZb5k7M=",
                request.headers["Authorization"]
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthHeader2() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val cred: Credentials = Credentials("ak", "sk")

        val request: RequestMessage = RequestMessage().apply {
            method = "PUT"
            url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?acl"
        }
        request.headers["Content-MD5"] = "eB5eJF1ptWaXm4bijSPyxw=="
        request.headers["Content-Type"] = "text/html"
        request.headers["x-oss-meta-author"] = "alice"
        request.headers["x-oss-meta-magic"] = "abracadabra"
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"

        val context = SigningContext().apply {
            bucket = "examplebucket"
            key = "nelson"
            this.request = request
            credentials = cred
            signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            assertEquals(
                "PUT\neB5eJF1ptWaXm4bijSPyxw==\ntext/html\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\nx-oss-meta-author:alice\nx-oss-meta-magic:abracadabra\n/examplebucket/nelson?acl",
                context.stringToSign
            )
            assertEquals(
                "OSS ak:/afkugFbmWDQ967j1vr6zygBLQk=",
                request.headers["Authorization"]
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthHeader3() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val cred: Credentials = Credentials("ak", "sk")

        val request: RequestMessage = RequestMessage().apply {
            method = "GET"
            url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?resourceGroup&non-resousce=null"
        }
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"

        val context = SigningContext().apply {
            bucket = "examplebucket"
            this.request = request
            credentials = cred
            signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            assertEquals(
                "GET\n\n\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\n/examplebucket/?resourceGroup",
                context.stringToSign
            )
            assertEquals(
                "OSS ak:vkQmfuUDyi1uDi3bKt67oemssIs=",
                request.headers["Authorization"]
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthHeader4() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val cred: Credentials = Credentials("ak", "sk")

        val request: RequestMessage = RequestMessage().apply {
            method = "GET"
            url = "http://examplebucket.oss-cn-hangzhou.aliyuncs.com/?resourceGroup&acl"
        }
        request.headers["x-oss-date"] = "Wed, 28 Dec 2022 10:27:41 GMT"

        val context = SigningContext().apply {
            bucket = "examplebucket"
            this.request = request
            credentials = cred
            signTimeInEpoch = 1702743657L // Instant.fromEpochSeconds(1702743657)
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)
            assertEquals(
                "GET\n\n\nWed, 28 Dec 2022 10:27:41 GMT\nx-oss-date:Wed, 28 Dec 2022 10:27:41 GMT\n/examplebucket/?acl&resourceGroup",
                context.stringToSign
            )
            assertEquals(
                "OSS ak:x3E5TgOvl/i7PN618s5mEvpJDYk=",
                request.headers["Authorization"]
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthQuery() {
        val provider = StaticCredentialsProvider("ak", "sk")
        val cred: Credentials = Credentials("ak", "sk")

        val request: RequestMessage? = RequestMessage().apply {
            method = "GET"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com/key?versionId=versionId"
        }

        val context = SigningContext().apply {
            bucket = "bucket"
            key = "key"
            this.request = request
            credentials = cred
            expirationInEpoch = 1699807420L // Instant.fromEpochSeconds(1699807420)
            isAuthMethodQuery = true
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            val queries = HttpUtils.extractParamsWithoutDecode(context.request!!.url)

            assertNotNull(queries["Expires"])
            assertEquals("versionId", queries["versionId"])
            assertEquals("ak", queries["OSSAccessKeyId"])
            assertEquals("dcLTea%2BYh9ApirQ8o8dOPqtvJXQ%3D", queries["Signature"])
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthQueryWithToken() {
        val provider =
            StaticCredentialsProvider("ak", "sk", "attachment; /file/name==example.txt++")
        val cred: Credentials = Credentials("ak", "sk", "attachment; /file/name==example.txt++")

        val request: RequestMessage? = RequestMessage().apply {
            method = "GET"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com/key+123?versionId=versionId"
        }

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "key+123"
        context.request = request
        context.credentials = cred
        context.expirationInEpoch = 1699808204L // Instant.fromEpochSeconds(1699808204)
        context.isAuthMethodQuery = true

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            val queries = HttpUtils.extractParamsWithoutDecode(context.request!!.url)

            assertNotNull(queries["Expires"])
            assertEquals("ak", queries["OSSAccessKeyId"])
            assertEquals(
                "attachment%3B%20%2Ffile%2Fname%3D%3Dexample.txt%2B%2B",
                queries["security-token"]
            )
            assertEquals("su58IVk06Q73DHwcMsXft%2FRTZ98%3D", queries["Signature"])
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testAuthQueryParamWithToken() {
        val provider = StaticCredentialsProvider("ak", "sk", "token")
        val cred: Credentials = Credentials("ak", "sk", "token")

        val request: RequestMessage = RequestMessage().apply {
            method = "GET"
            url = "http://bucket.oss-cn-hangzhou.aliyuncs.com/key?versionId=versionId"
        }
        request.headers["x-oss-head1"] = "value"
        request.headers["abc"] = "value"
        request.headers["ZAbc"] = "value"
        request.headers["XYZ"] = "value"
        request.headers["content-type"] = "application/octet-stream"

        val context = SigningContext()
        context.bucket = "bucket"
        context.key = "key"
        context.request = request
        context.credentials = cred
        context.expirationInEpoch = 1699808204L // Instant.fromEpochSeconds(1699808204)
        context.isAuthMethodQuery = true

        val parameters = mutableMapOf<String, String>()
        parameters.put("param1", "value1")
        parameters.put("+param1", "value3")
        parameters.put("|param1", "value4")
        parameters.put("+param2", "")
        parameters.put("|param2", "")
        parameters.put("param2", "")
        parameters.put("response-content-disposition", "attachment; filename=example.txt")

        val query = HttpUtils.encodeQueryParameters(parameters)
        val url = StringBuilder()
        url.append(request.url)
        query.let {
            url.append("&").append(it)
        }

        context.request = RequestMessage().apply {
            method = request.method
            headers = request.headers
            this.url = url.toString()
        }

        runBlocking {
            val signer = SignerV1()
            signer.sign(context)

            val queries = HttpUtils.extractParamsWithoutDecode(context.request!!.url)

            assertNotNull(queries["Expires"])
            assertEquals("ak", queries["OSSAccessKeyId"])
            assertEquals(
                "attachment%3B%20filename%3Dexample.txt",
                queries["response-content-disposition"]
            )
            assertEquals("token", queries["security-token"])
            assertEquals("VmWfLWfxbR3MSFvUx5%2BnyQhCa3g%3D", queries["Signature"])
        }
    }
}
