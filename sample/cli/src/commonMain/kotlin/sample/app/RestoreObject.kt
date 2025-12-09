package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.RestoreRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar RestoreObject --region `region` --bucket`bucket` --key `key`
class RestoreObject : SampleSubcommand(
    "RestoreObject",
    "You can call this operation to restore objects of the Archive and Cold Archive storage classes."
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
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.restoreObject(RestoreObjectRequest {
                this.bucket = argBucket
                key = argKey
                restoreRequest = RestoreRequest {
                    days = 1
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