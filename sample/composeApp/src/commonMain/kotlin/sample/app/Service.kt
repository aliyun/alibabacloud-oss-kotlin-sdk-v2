package sample.app

import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.progress.ProgressListener
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.io.files.Path

class Service(
    var client: OSSClient
) {

    suspend fun putBucket(bucket: String) {
        client.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
    }

    suspend fun deleteBucket(bucket: String) {
        client.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    suspend fun putObject(
        bucket: String,
        key: String,
        filePath: Path,
        progress: (Float) -> Unit
    ) {
        client.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromFile(filePath)
            progressListener = ProgressListener { bytesSent, totalBytesSent, totalBytesExpectedToSend ->
                val p = totalBytesSent.toFloat() / totalBytesExpectedToSend
                progress(p)
            }
        })
    }

    suspend fun getObject(
        bucket: String,
        key: String
    ): Data? {
        val result = client.getObject(GetObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        val bytes = result.body?.toByteArray()
        return if (result.headers["Content-Type"]?.startsWith("image/") == true) {
            Data.Image(bytes)
        } else if (result.headers["Content-Type"]?.startsWith("text/") == true) {
            Data.Text(bytes?.decodeToString())
        } else {
            Data.Other(bytes)
        }
    }

    suspend fun headObject(
        bucket: String,
        key: String
    ): Map<String, String> {
        val result = client.headObject(HeadObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        return result.headers
    }

    suspend fun deleteObject(
        bucket: String,
        key: String
    ) {
        client.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        })
    }

    suspend fun presign(
        bucket: String,
        key: String,
        presignType: PresignType
    ): Map<String, Any> {
        val result = when (presignType) {
            PresignType.PUT -> client.presign(PutObjectRequest {
                this.bucket = bucket
                this.key = key
            })
            PresignType.GET -> client.presign(GetObjectRequest {
                this.bucket = bucket
                this.key = key
            })
            PresignType.HEAD -> client.presign(HeadObjectRequest {
                this.bucket = bucket
                this.key = key
            })
        }
        return mapOf(
            "url" to result.url,
            "method" to result.method,
            "header" to result.signedHeaders
        )
    }

    suspend fun listObjects(bucket: String) {
        return client.listObjectsV2Paginator(ListObjectsV2Request {
            this.bucket = bucket
        }).collect {
            it.contents?.forEach { content ->
                println("key: ${content.key}")
            }
        }
    }
}

sealed class Data() {
    data class Image(val bytes: ByteArray?): Data() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Image
            return bytes.contentEquals(other.bytes)
        }

        override fun hashCode(): Int {
            return bytes.contentHashCode()
        }
    }

    data class Text(val bytes: String?): Data()
    data class Other(val bytes: Any?): Data()
}

enum class PresignType {
    PUT, GET, HEAD
}