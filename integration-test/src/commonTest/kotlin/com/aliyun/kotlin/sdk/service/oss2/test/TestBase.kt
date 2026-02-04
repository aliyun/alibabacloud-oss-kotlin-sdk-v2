@file:OptIn(ExperimentalTime::class)

package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.Credentials
import com.aliyun.kotlin.sdk.service.oss2.credentials.CredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.CredentialsException
import com.aliyun.kotlin.sdk.service.oss2.hash.hmacSha1
import com.aliyun.kotlin.sdk.service.oss2.hash.md5
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.paginator.listBucketsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listMultipartUploadsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectVersionsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import io.ktor.http.encodeURLParameter
import io.ktor.util.encodeBase64
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlinx.datetime.format.char
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


open class TestBase {
    companion object {
        const val BUCKET_NAME_PREFIX: String = "kotlin-sdk-test-bucket-"
        const val OBJECT_NAME_PREFIX: String = "kotlin-sdk-test-object-"

        // OSS test configuration
        val OSS_TEST_REGION: String = System.getenv("OSS_TEST_REGION")?: ""
        val OSS_TEST_ACCESS_KEY_ID: String = System.getenv("OSS_TEST_ACCESS_KEY_ID")?: ""
        val OSS_TEST_ACCESS_KEY_SECRET: String = System.getenv("OSS_TEST_ACCESS_KEY_SECRET")?: ""
        val OSS_TEST_ENDPOINT: String? = System.getenv("OSS_TEST_ENDPOINT")
        val OSS_TEST_RAM_ROLE_ARN: String = System.getenv("OSS_TEST_RAM_ROLE_ARN")?: ""
        val OSS_TEST_RAM_UID: String = System.getenv("OSS_TEST_RAM_UID")?: ""

        val TEST_FILE_TEMP_DIR = "$SystemTemporaryDirectory/kotlin-sdk-test"
    }

    fun randomBucketName(): String {
        val ticks = Clock.System.now().toEpochMilliseconds()
        val value = Random.nextInt(500).toLong()
        return "$BUCKET_NAME_PREFIX$ticks-$value"
    }

    fun randomObjectKey(): String {
        val ticks = Clock.System.now().epochSeconds
        val value = Random.nextInt(500).toLong()
        return "$OBJECT_NAME_PREFIX$ticks-$value"
    }

    fun waitForCacheExpiration(durationSeconds: Int) {
    }

    suspend fun cleanBuckets(prefix: String) {
        defaultClient.listBucketsPaginator(ListBucketsRequest{
            this.prefix = prefix
        }).collect {
           it.buckets?.forEach { it0 ->
                cleanBucket(it0.name!!, it0.region!!)
           }
        }
    }

    fun genBucketName(): String {
        val ticks = Clock.System.now().epochSeconds
        val value = Random.nextInt(500).toLong()
        return "$BUCKET_NAME_PREFIX$ticks-$value"
    }

    fun createBucket(bucket: String) {
        waitForCacheExpiration(1)
    }

    suspend fun cleanBucket(bucket: String, region: String) {
        cleanObjects(defaultClient, bucket)
        cleanParts(defaultClient, bucket)
    }

    private suspend fun cleanObjects(client: OSSClient, bucketName: String) {
        client.listObjectsV2Paginator(ListObjectsV2Request {
            bucket = bucketName
        }).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    bucket = bucketName
                    key = obj.key
                })
            }
        }
        client.listObjectVersionsPaginator(ListObjectVersionsRequest {
            bucket = bucketName
        }).collect {
            it.versions?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucketName
                    this.key = obj.key
                    this.versionId = obj.versionId
                })
            }
        }
    }

    private suspend fun cleanParts(client: OSSClient, bucketName: String) {
        client.listMultipartUploadsPaginator(ListMultipartUploadsRequest {
            bucket = bucketName
        }).collect {
            it.uploads?.forEach { obj ->
                defaultClient.abortMultipartUpload(AbortMultipartUploadRequest {
                    bucket = bucketName
                    key = obj.key
                    uploadId = obj.uploadId
                })
            }
        }
    }

    fun createTestFile(fileName: String, content: ByteArray): Path {
        val fs = SystemFileSystem
        Path(TEST_FILE_TEMP_DIR).also {
            if (!fs.exists(it)) {
                fs.createDirectories(it)
            }
        }
        val path = Path("$TEST_FILE_TEMP_DIR/$fileName")
        if (fs.exists(path)) {
            fs.delete(path)
        }
        fs.sink(path).use { it0 ->
            it0.write(Buffer().also { it.write(content) }, content.size.toLong())
        }
        return path
    }

    fun createTestFile(fileName: String, size: Int): Path {
        val bytes = Random.nextBytes(size)
        return createTestFile(fileName, bytes)
    }

    fun removeTestFile(fileName: String) {
        val fs = SystemFileSystem
        val path = Path("$TEST_FILE_TEMP_DIR/$fileName")
        if (fs.exists(path)) {
            fs.delete(path)
        }
    }

    val defaultClient: OSSClient by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OSSClient.create(ClientConfiguration.loadDefault().apply{
            region = OSS_TEST_REGION
            endpoint = OSS_TEST_ENDPOINT
            credentialsProvider = StaticCredentialsProvider(OSS_TEST_ACCESS_KEY_ID, OSS_TEST_ACCESS_KEY_SECRET)
        })
    }

    val invalidClient: OSSClient by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OSSClient.create(ClientConfiguration.loadDefault().apply{
            region = OSS_TEST_REGION
            endpoint = OSS_TEST_ENDPOINT
            credentialsProvider = StaticCredentialsProvider("ak", "sk")
        })
    }
}

internal suspend fun ByteStream.decodeToString(): String = toByteArray().decodeToString()

internal fun ByteStream.Companion.fromSource(source: RawSource, contentLength: Long? = null):  ByteStream =  object: ByteStream.SourceStream() {
    override val contentLength: Long? = contentLength
    override fun readFrom(): RawSource = source
}

internal class RamRoleArnCredentialProvider(
    val accessKeyId: String,
    val accessKeySecret: String,
    val roleArn: String,
    val regionId: String,
    val policy: String? = null,
    val roleSessionName: String = "defaultSessionName",
    val durationSeconds: Int = 3600
): CredentialsProvider {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getCredentials(): Credentials {
        val params: MutableMap<String, String> = mutableMapOf(
            "Action" to "AssumeRole",
            "Format" to "JSON",
            "Version" to "2015-04-01",
            "DurationSeconds" to "$durationSeconds",
            "RoleArn" to roleArn,
            "AccessKeyId" to accessKeyId,
            "RegionId" to regionId,
            "RoleSessionName" to roleSessionName,
            "SignatureVersion" to "1.0",
            "SignatureMethod" to "HMAC-SHA1",
            "Timestamp" to Clock.System.now().format(Format {
                year()
                char('-')
                monthNumber()
                char('-')
                day()
                char('T')
                hour()
                char(':')
                minute()
                char(':')
                second()
                chars("Z")
            }),
            "SignatureNonce" to "${Clock.System.now().epochSeconds}${
                Uuid.random().toHexString()
            }".toByteArray().md5().joinToString { String.format("%02x", it) },
        )
        policy?.let {
            params["Policy"] = it
        }

        val stringToSign = buildString {
            append("GET&%2F&")
            append(params.mapNotNull {
                "${it.key}=${it.value.encodeURLParameter()}".encodeURLParameter()
            }.sorted().joinToString("%26"))
        }

        val signature = stringToSign.toByteArray().hmacSha1("$accessKeySecret&".toByteArray())
        params["Signature"] = signature.encodeBase64()

        val url = buildString {
            append("https://sts.aliyuncs.com?")
            append(params.mapNotNull {
                "${it.key.encodeURLParameter()}=${it.value.encodeURLParameter()}"
            }.joinToString("&"))
        }
        val response = OkHttpClient.Builder()
            .build()
            .newCall(
                Request.Builder()
                    .url(url)
                    .get()
                    .header("host", "sts.aliyuncs.com")
                    .build()
            )
            .execute()
        val result = Json.decodeFromString<Map<String, JsonElement>>(response.body.bytes().decodeToString())
        (result["Credentials"] as? JsonObject)?.let {
            val accessKey = it["AccessKeyId"]?.jsonPrimitive?.content
            val secretKey = it["AccessKeySecret"]?.jsonPrimitive?.content
            val expirationTime = it["Expiration"]?.jsonPrimitive?.content
            val token = it["SecurityToken"]?.jsonPrimitive?.content
            if (accessKey != null && secretKey != null) {
                return Credentials(accessKey, secretKey, token, expirationTime)
            } else {
                throw CredentialsException()
            }
        }
        throw CredentialsException()
    }

}
