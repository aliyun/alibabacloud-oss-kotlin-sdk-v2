package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.AgenticBucketStatus
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.PutAgenticBucketStatusRequest
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar PutAgenticBucketStatus --region `region` --account-id `accountId` --bucket `bucket` --status `status`
internal class PutAgenticBucketStatus :
    SampleSubcommand("PutAgenticBucketStatus", "Sets the status of an agentic bucket.") {
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
    val argStatus by option(
        ArgType.String,
        shortName = "s",
        fullName = "status",
        description = "The status of the agentic bucket, Enabled or Disabled"
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        AgenticBucketClient(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            this.accountId = argAccountId
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.putAgenticBucketStatus(PutAgenticBucketStatusRequest {
                this.bucket = argBucket
                agenticBucketStatus = AgenticBucketStatus {
                    status = argStatus
                }
            })
            println("Status Code: ${res.statusCode}")
        }
    }
}
