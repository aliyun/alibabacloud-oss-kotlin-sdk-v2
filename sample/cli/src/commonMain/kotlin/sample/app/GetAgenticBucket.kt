package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.GetAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar GetAgenticBucket --region `region` --account-id `accountId` --bucket `bucket`
internal class GetAgenticBucket :
    SampleSubcommand("GetAgenticBucket", "Queries the information about an agentic bucket.") {
    val argRegion by option(
        ArgType.String,
        shortName = "r",
        fullName = "region",
        description = "Region"
    ).required()
    val argAccountId by option(
        ArgType.String,
        fullName = "account-id",
        description = "The ID of the Alibaba Cloud account"
    ).required()
    val argBucket by option(
        ArgType.String,
        shortName = "b",
        fullName = "bucket",
        description = "The short name of the agentic bucket"
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        AgenticBucketClient(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            this.accountId = argAccountId
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.getAgenticBucket(GetAgenticBucketRequest {
                this.bucket = argBucket
            })
            val info = res.agenticBucketInfo
            println(
                buildString {
                    append("Status Code: ${res.statusCode}\n")
                    append("Name: ${info?.name}\n")
                    append("Region: ${info?.region}\n")
                    append("Status: ${info?.status}\n")
                    append("StorageClass: ${info?.storageClass}\n")
                    append("CreateTime: ${info?.createTime}")
                }
            )
        }
    }
}
