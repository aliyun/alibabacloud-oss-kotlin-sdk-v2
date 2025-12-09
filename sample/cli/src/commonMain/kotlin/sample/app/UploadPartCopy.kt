package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar UploadPartCopy --region `region` --bucket `bucket` --key `key` --sourceKey `sourceKey` --sourceRange `sourceRange` --partNumber `partNumber` --uploadId `uploadId`
class UploadPartCopy :
    SampleSubcommand("UploadPartCopy", "Upload a single shard by copying existing files") {
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
    val argSourceBucket by option(
        ArgType.String,
        fullName = "sourceBucket",
        description = "The name of the source bucket."
    )
    val argSourceKey by option(
        ArgType.String,
        fullName = "sourceKey",
        description = "The full path of the source object."
    ).required()
    val argSourceRange by option(
        ArgType.String,
        fullName = "sourceRange",
        description = "The range of bytes to copy data from the source object."
    ).required()
    val argPartNumber by option(
        ArgType.Int,
        fullName = "partNumber",
        description = "The number that identifies a part."
    ).required()
    val argUploadId by option(
        ArgType.String,
        fullName = "uploadId",
        description = "The ID of the multipart upload task."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.uploadPartCopy(UploadPartCopyRequest {
                this.bucket = argBucket
                key = argKey
                sourceBucket = argSourceBucket
                sourceKey = argSourceKey
                copySourceRange = argSourceRange
                uploadId = argUploadId
                partNumber = argPartNumber.toLong()
            })
            println(buildString {
                append("Status Code: ${res.statusCode}\n")
                append(res.headers.map {
                    "${it.key}: ${it.value}"
                }.joinToString("\n"))
            })
        }
    }
}