package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required
import kotlinx.coroutines.runBlocking

// java -jar cli-jvm.jar CopyObject --region `region` --bucket `bucket` --key `destinationKey` --sourceKey `sourceKey`
class CopyObject : SampleSubcommand(
    "CopyObject",
    "Copies objects within a bucket or between buckets in the same region."
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

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.copyObject(CopyObjectRequest {
                this.bucket = argBucket
                key = argKey
                sourceBucket = argSourceBucket
                sourceKey = argSourceKey
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