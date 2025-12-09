package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsPaginator
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar ListObjects --region `region` --bucket `bucket`
class ListObjects :
    SampleSubcommand("ListObjects", "Queries the information about all objects in a bucket.") {
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
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            client.listObjectsPaginator(ListObjectsRequest {
                this.bucket = argBucket
            }).collect {
                it.contents?.forEach { obj ->
                    println("key: ${obj.key} size: ${obj.size}")
                }
            }
        }
    }
}