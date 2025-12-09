package sample.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Tag
import com.aliyun.kotlin.sdk.service.oss2.models.TagSet
import com.aliyun.kotlin.sdk.service.oss2.models.Tagging
import kotlinx.cli.ArgType
import kotlinx.cli.required

// java -jar cli-jvm.jar PutObjectTagging --region `region` --bucket `bucket` --key `key` --tags `key1:value1&key2:value2...`
class PutObjectTagging : SampleSubcommand(
    "PutObjectTagging",
    "You can call this operation to add tags to or modify the tags of an object."
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
    val argTags by option(ArgType.String, fullName = "tags", description = "The tags.").required()
    val argEndpoint by option(ArgType.String, fullName = "endpoint", description = "Endpoint")

    override suspend fun executeCommand() {
        OSSClient.create(ClientConfiguration.loadDefault().apply {
            this.region = argRegion
            this.endpoint = argEndpoint
            credentialsProvider = EnvironmentVariableCredentialsProvider()
        }).use { client ->
            val res = client.putObjectTagging(PutObjectTaggingRequest {
                this.bucket = argBucket
                key = argKey
                tagging = Tagging {
                    tagSet = TagSet {
                        tags = argTags.split("&").map {
                            val tag = it.split(":")
                            Tag {
                                key = tag.first()
                                value = tag.last()
                            }
                        }
                    }
                }
            })
            println(buildString {
                append("Status Code: ${res.statusCode}\n")
                append(res.headers.map {
                    "${it.key}: ${it.value}"
                }.joinToString("\n"))
            })
        }
    }
}