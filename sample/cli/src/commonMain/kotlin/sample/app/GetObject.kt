package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar GetObject --region `region` --bucket `bucket` --key `key`
class GetObject :
    SampleSubcommand("GetObject", "You can call this operation to download an object.") {
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
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.getObject(GetObjectRequest {
                this.bucket = argBucket
                key = argKey
            })
            println(res.body?.toByteArray()?.decodeToString())
        }
    }
}