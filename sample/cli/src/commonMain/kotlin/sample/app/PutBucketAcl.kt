package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketAclRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar PutBucketAcl --region `region` --bucket `bucket` --acl `acl`
class PutBucketAcl : SampleSubcommand(
    "PutBucketAcl",
    "Configures or modifies the access control list (ACL) for a bucket."
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
    val argAcl by option(
        ArgType.String,
        fullName = "acl",
        description = "The ACL that you want to configure or modify for the bucket."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.putBucketAcl(PutBucketAclRequest {
                this.bucket = argBucket
                acl = argAcl
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