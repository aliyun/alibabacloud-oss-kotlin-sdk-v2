package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketStatRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar GetBucketStat --region `region` --bucket `bucket`
class GetBucketStat : SampleSubcommand(
    "GetBucketStat",
    "Queries the storage capacity of a bucket and the number of objects that are stored in the bucket."
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
            val res = client.getBucketStat(GetBucketStatRequest {
                this.bucket = argBucket
            })
            println("objectCount: ${res.bucketStat?.objectCount}")
            println("lastModifiedTime ${res.bucketStat?.lastModifiedTime}")
            println("storage: ${res.bucketStat?.storage}")
            println("multipartPartCount: ${res.bucketStat?.multipartPartCount}")
            println("liveChannelCount: ${res.bucketStat?.liveChannelCount}")
            println("multipartUploadCount: ${res.bucketStat?.multipartUploadCount}")
            println("deepColdArchiveStorage: ${res.bucketStat?.deepColdArchiveStorage}")
            println("deepColdArchiveObjectCount: ${res.bucketStat?.deepColdArchiveObjectCount}")
            println("deepColdArchiveRealStorage: ${res.bucketStat?.deepColdArchiveRealStorage}")
            println("archiveStorage: ${res.bucketStat?.archiveStorage}")
            println("archiveRealStorage ${res.bucketStat?.archiveRealStorage}")
            println("deleteMarkerCount ${res.bucketStat?.deleteMarkerCount}")
            println("standardObjectCount ${res.bucketStat?.standardObjectCount}")
            println("standardStorage ${res.bucketStat?.standardStorage}")
            println("coldArchiveStorage ${res.bucketStat?.coldArchiveStorage}")
            println("coldArchiveObjectCount ${res.bucketStat?.coldArchiveObjectCount}")
            println("coldArchiveRealStorage ${res.bucketStat?.coldArchiveRealStorage}")
        }
    }
}