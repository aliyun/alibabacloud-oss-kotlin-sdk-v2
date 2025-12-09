package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listPartsPaginator
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar ListParts --region `region` --bucket `bucket` --key `key` --uploadId `uploadId`
class ListParts : SampleSubcommand(
    "ListParts",
    "You can call this operation to list all parts that are uploaded by using a specified upload ID."
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
    val argUploadId by option(
        ArgType.String,
        fullName = "uploadId",
        description = "The ID of the multipart upload task.By default, this parameter is left empty."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            client.listPartsPaginator(ListPartsRequest {
                this.bucket = argBucket
                key = argKey
                uploadId = argUploadId
            }).collect {
                it.parts?.forEach { part ->
                    println("partNumber: ${part.partNumber}")
                    println("eTag: ${part.eTag}")
                }
            }
        }
    }
}