package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listMultipartUploadsPaginator
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar ListMultipartUploads --region `region` --bucket `bucket`
class ListMultipartUploads : SampleSubcommand(
    "ListMultipartUploads",
    "You can call this operation to list all ongoing multipart upload tasks."
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
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            client.listMultipartUploadsPaginator(ListMultipartUploadsRequest {
                this.bucket = argBucket
            }).collect {
                it.uploads?.forEach { upload ->
                    println("key: ${upload.key} upload id: ${upload.uploadId}")
                }
            }
        }
    }
}