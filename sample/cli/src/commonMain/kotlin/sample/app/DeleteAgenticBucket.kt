package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.DeleteAgenticBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar DeleteAgenticBucket --region `region` --account-id `accountId` --bucket `bucket`
internal class DeleteAgenticBucket :
    SampleSubcommand("DeleteAgenticBucket", "Deletes an agentic bucket.") {
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
            // An agentic bucket can be deleted only after it has been disabled and
            // all of its bucket spaces have been removed.
            val res = client.deleteAgenticBucket(DeleteAgenticBucketRequest {
                this.bucket = argBucket
            })
            println("Status Code: ${res.statusCode}")
        }
    }
}
