package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar GetBucketInfo --region `region` --bucket `bucket`
class GetBucketInfo : SampleSubcommand("GetBucketInfo", "Queries the information about a bucket.") {
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
            val res = client.getBucketInfo(GetBucketInfoRequest {
                this.bucket = argBucket
            })
            println("bucket: ${res.bucketInfo?.bucket?.name}")
            println("creationDate: ${res.bucketInfo?.bucket?.creationDate}")
            println("location: ${res.bucketInfo?.bucket?.location}")
            println("acl: ${res.bucketInfo?.bucket?.accessControlList?.grant}")
            println("storageClass: ${res.bucketInfo?.bucket?.storageClass}")
            println("extranetEndpoint: ${res.bucketInfo?.bucket?.extranetEndpoint}")
        }
    }
}
