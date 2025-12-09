package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar AbortMultipartUpload --region `region` --bucket `bucket` --key `key` --uploadId `uploadId`
class AbortMultipartUpload : SampleSubcommand(
    "AbortMultipartUpload",
    "You can call this operation to cancel a multipart upload task and delete the parts that are uploaded by the multipart upload task."
) {
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
            val res = client.abortMultipartUpload(AbortMultipartUploadRequest {
                this.bucket = argBucket
                key = argKey
                uploadId = argUploadId
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