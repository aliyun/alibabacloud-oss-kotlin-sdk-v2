package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Part
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar CompleteMultipartUpload --region `region` --bucket `bucket` --key `key` --uploadId `uploadId` --parts `partNumber1:eTag1,partNumber2:eTag2`
class CompleteMultipartUpload : SampleSubcommand(
    "CompleteMultipartUpload",
    "You can call this operation to complete the multipart upload task of an object."
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
        description = "The ID of the multipart upload task.By default, this parameter is left empty."
    ).required()
    val argParts by option(
        ArgType.String,
        fullName = "parts",
        description = "PartNumber and eTag of uploaded parts"
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.completeMultipartUpload(CompleteMultipartUploadRequest {
                this.bucket = argBucket
                key = argKey
                uploadId = argUploadId
                completeMultipartUpload = CompleteMultipartUpload {
                    parts = argParts.split(",").map {
                        val part = it.split(":")
                        Part {
                            partNumber = part.first().toLong()
                            eTag = part.last()
                        }
                    }
                }
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