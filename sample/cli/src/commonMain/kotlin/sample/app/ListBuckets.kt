package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listBucketsPaginator
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar ListBuckets --region `region`
class ListBuckets :
    SampleSubcommand("ListBuckets", "Queries all buckets that are owned by a requester.") {

    val argRegion by option(
        ArgType.String,
        shortName = "r",
        fullName = "region",
        description = "Region"
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            client.listBucketsPaginator(ListBucketsRequest { }).collect {
                it.buckets?.forEach { bucket ->
                    println("${bucket.name} ${bucket.region}")
                }
            }
        }
    }
}