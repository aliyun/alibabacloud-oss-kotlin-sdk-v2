package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.AgenticBucketClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.agentic.paginator.listAgenticBucketsPaginator
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar ListAgenticBuckets --region `region` --account-id `accountId`
internal class ListAgenticBuckets :
    SampleSubcommand("ListAgenticBuckets", "Lists the agentic buckets of the requester.") {
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
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        AgenticBucketClient(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            this.accountId = argAccountId
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            client.listAgenticBucketsPaginator(ListAgenticBucketsRequest { }).collect {
                it.agenticBuckets?.forEach { bucket ->
                    println("${bucket.name} ${bucket.storageClass} ${bucket.createTime}")
                }
            }
        }
    }
}
