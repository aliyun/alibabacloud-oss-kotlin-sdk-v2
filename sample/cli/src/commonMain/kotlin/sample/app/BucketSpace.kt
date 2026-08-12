package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.BucketSpaceClient
import com.aliyun.kotlin.sdk.service.oss2.agentic.BucketSpaceHelper
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar BucketSpace --region `region` --account-id `accountId` --bucket `bucket` --key `key`
internal class BucketSpace :
    SampleSubcommand("BucketSpace", "Reads and writes an object in a bucket space.") {
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
    val argKey by option(
        ArgType.String,
        shortName = "k",
        fullName = "key",
        description = "The full path of the object."
    ).required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        val config = ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            this.accountId = argAccountId
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }

        // Mode 1: a dedicated client that resolves the short bucket name to
        // {bucket}-{accountId}-{region}-bs-apsr on every request.
        BucketSpaceClient.create(config).use { client ->
            val res = client.putObject(PutObjectRequest {
                this.bucket = argBucket
                key = argKey
                body = ByteStream.fromString("Hello agentic.")
            })
            println("[mode1] Status Code: ${res.statusCode}")
        }

        // Mode 2: a plain client plus BucketSpaceHelper, which builds the full bucket
        // name so you keep control over the endpoint and the client itself.
        val fullBucketName = BucketSpaceHelper(config).toBucketName(argBucket)
        println("[mode2] resolved bucket name: $fullBucketName")

        OSSClient.create(config).use { client ->
            val res = client.getObject(GetObjectRequest {
                this.bucket = fullBucketName
                key = argKey
            })
            println("[mode2] content: ${res.body?.toByteArray()?.decodeToString()}")
        }
    }
}
