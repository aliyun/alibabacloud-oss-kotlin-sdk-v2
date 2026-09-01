package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.agentic.BucketSpaceClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar CreateBucketSpace --region `region` --account-id `accountId` --bucket `bucket` --agentic-bucket `agenticBucket`
internal class CreateBucketSpace :
    SampleSubcommand("CreateBucketSpace", "Creates a bucket space under an agentic bucket.") {
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
        description = "The short name of the bucket space"
    ).required()
    val argAgenticBucket by option(
        ArgType.String,
        fullName = "agentic-bucket",
        description = "The short name of the agentic bucket that the bucket space belongs to"
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        // The bucket space client resolves the short bucket name to its physical form
        // and reuses the standard bucket and object operations.
        BucketSpaceClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            this.accountId = argAccountId
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            // The bucket space must be created under an agentic bucket, identified by its
            // full name {bucket}-{accountId}-{region}-ab-apsr.
            val res = client.putBucket(PutBucketRequest {
                this.bucket = argBucket
                agenticBucket = "$argAgenticBucket-$argAccountId-$argRegion-ab-apsr"
            })
            println("Status Code: ${res.statusCode}")
        }
    }
}
