package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar PutBucketVersioning --region `region` --bucket `bucket` --status `status`
class PutBucketVersioning :
    SampleSubcommand("PutBucketVersioning", "Configures the versioning state for a bucket.") {
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
    val argVersioningStatus by option(
        ArgType.String,
        fullName = "status",
        description = "The versioning state of the bucket."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.putBucketVersioning(PutBucketVersioningRequest {
                this.bucket = argBucket
                versioningConfiguration = VersioningConfiguration {
                    status = argVersioningStatus
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