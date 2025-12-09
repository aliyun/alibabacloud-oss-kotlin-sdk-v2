package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

enum class PresignOption {
    PutObject,
    GetObject,
    HeadObject,
    InitiateMultipartUpload,
    UploadPart,
    CompleteMultipartUpload,
    AbortMultipartUpload
}

// java -jar cli-jvm.jar Presign --region `region` --bucket `bucket` --key `key` --presignOption `presignOption`
class Presign : SampleSubcommand("Presign", "Generates the pre-signed URL.") {
    val argRegion by option(
        ArgType.String,
        shortName = "r",
        fullName = "region",
        description = "Region"
    ).required()
    val argBucket by option(
        ArgType.String,
        shortName = "b",
        fullName = "bucket",
        description = "Bucket name"
    ).required()
    val argKey by option(
        ArgType.String,
        shortName = "k",
        fullName = "key",
        description = "The full path of the object."
    ).required()
    val argPresignOption by option(
        ArgType.Choice<PresignOption>(),
        fullName = "presignOption",
        description = "The presign option."
    ).required()
    val argUploadId by option(
        ArgType.String,
        fullName = "uploadId",
        description = "The ID that identifies the object to which the part that you want to upload belongs."
    )
    val argPartNumber by option(
        ArgType.String,
        fullName = "partNumber",
        description = "The number that identifies a part."
    )
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val result = when (argPresignOption) {
                PresignOption.PutObject -> client.presign(PutObjectRequest {
                    this.bucket = argBucket
                    key = argKey
                })

                PresignOption.GetObject -> client.presign(GetObjectRequest {
                    this.bucket = argBucket
                    key = argKey
                })

                PresignOption.HeadObject -> client.presign(HeadObjectRequest {
                    this.bucket = argBucket
                    key = argKey
                })

                PresignOption.InitiateMultipartUpload -> client.presign(
                    InitiateMultipartUploadRequest {
                        this.bucket = argBucket
                        key = argKey
                    })

                PresignOption.UploadPart -> client.presign(UploadPartRequest {
                    this.bucket = argBucket
                    key = argKey
                    uploadId =
                        requireNotNull(argUploadId) { "Value for option --uploadId should be always provided in command line." }
                    partNumber =
                        requireNotNull(argPartNumber?.toLong()) { "Value for option --partNumber should be always provided in command line." }
                })

                PresignOption.CompleteMultipartUpload -> client.presign(
                    CompleteMultipartUploadRequest {
                        this.bucket = argBucket
                        key = argKey
                        uploadId =
                            requireNotNull(argUploadId) { "Value for option --uploadId should be always provided in command line." }
                    })

                PresignOption.AbortMultipartUpload -> client.presign(AbortMultipartUploadRequest {
                    this.bucket = argBucket
                    key = argKey
                    uploadId =
                        requireNotNull(argUploadId) { "Value for option --uploadId should be always provided in command line." }
                })
            }

            println(buildString {
                append("url: ${result.url}\n")
                append("method: ${result.method}\n")
                append(
                    "header: \n${
                        result.signedHeaders.map { "    ${it.key}: ${it.value}" }.joinToString("\n")
                    }\n"
                )
            })
        }
    }
}
