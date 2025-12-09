package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.cli.ArgType
import kotlinx.cli.required
import kotlin.random.Random

// java -jar cli-jvm.jar UploadPart --region `region` --bucket `bucket` --key `key` --uploadId `uploadId` --partNumber `partNumber`
class UploadPart : SampleSubcommand(
    "UploadPart",
    "You can call this operation to upload an object by part based on the object name and the upload ID that you specify."
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
        description = "The ID that identifies the object to which the part that you want to upload belongs."
    ).required()
    val argPartNumber by option(
        ArgType.Int,
        fullName = "partNumber",
        description = "The number that identifies a part."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.uploadPart(UploadPartRequest {
                this.bucket = argBucket
                key = argKey
                uploadId = argUploadId
                partNumber = argPartNumber.toLong()
                body = ByteStream.fromBytes(Random.nextBytes(256 * 1024))
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