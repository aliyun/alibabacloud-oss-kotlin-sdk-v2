# Alibaba Cloud OSS SDK for kotlin v2

[![GitHub version](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-kotlin-sdk-v2.svg)](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-kotlin-sdk-v2)

alibabacloud-oss-kotlin-sdk-v2 is the Developer Preview for the v2 of the OSS SDK for the Kotlin programming language

## [简体中文](README-CN.md)

## About
> - This Kotlin SDK is based on the official APIs of [Alibaba Cloud OSS](http://www.aliyun.com/product/oss/).
> - Alibaba Cloud Object Storage Service (OSS) is a cloud storage service provided by Alibaba Cloud, featuring massive capacity, security, a low cost, and high reliability.
> - The OSS can store any type of files and therefore applies to various websites, development enterprises and developers.
> - With this SDK, you can upload, download and manage data on any app anytime and anywhere conveniently.

## Running Environment
- Applicable to`Kotlin 2.1.0`or above
- Platform: Android, Desktop(JVM)

## Installing
### Install the sdk through Gradle
Add the following dependencies to your project
```kts
implementation("com.aliyun:kotlin-oss-v2:<latest-version>")
// implementation("com.aliyun:kotlin-oss-v2-extension:<latest-version>")
```

> **Note:**
> </br>`kotlin-oss-v2` provides bucket basic api, all object api, and high-level api (such as paginator, presinger).
> </br>`kotlin-oss-v2-extension` provides other bucket control apis, such as BucketCors.

### Install from the source code

You can run the gradle command for installing after cloning the project source code:
```shell
# Clone the project
$ git clone https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git

# Enter the directory
$ cd alibabacloud-oss-kotlin-sdk-v2/

# Publish To MavenLocal
$ ./gradlew clean publishToMavenLocal
```
Add `mavenLocal` to repositories
```kts
repositories {
    ...
    mavenLocal()
}
```
Add the following dependencies to your project
```kts
implementation("com.aliyun:kotlin-oss-v2:<latest-version>")
// implementation("com.aliyun:kotlin-oss-v2-extension:<latest-version>")
```

### Compile the package from the source code
You can run the gradle command for packaging after cloning the project source code:

```shell
# Clone the project
$ git clone https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git

# Enter the directory
$ cd aliyun-oss-kotlin-sdk-v2/

# Run the packaging script.
$ ./gradlew :oss-sdk:assemble
# ./gradlew :oss-sdk-extension:assemble

# Take AAR package as an example
# Enter the directory generated after packaging and the package will be generated in this directory
$ cd oss-sdk/build/outputs/aar && ls
# cd oss-sdk-extension/build/outputs/aar && ls
```

## Getting Started
#### List Buckets
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listBucketsPaginator

suspend fun main() {
    val region = "cn-hangzhou"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        // Create the Paginator for the ListBuckets operation.
        // Iterate through the bucket pages
        client.listBucketsPaginator(ListBucketsRequest {}).collect { it ->
            it.buckets?.forEach { bucket ->
                println("bucket name: ${bucket.name}")
            }
        }
    }
}
```

#### Put Bucket
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        val result = client.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        println("PutBucket done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### Delete Bucket
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        val result = client.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
        println("DeleteBucket done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### List Objects
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        // Create the Paginator for the ListObjectsV2 operation.
        // Lists all objects in a bucket
        client.listObjectsV2Paginator(ListObjectsV2Request {
            this.bucket = bucket
        }).collect { it ->
            it.contents?.forEach { obj ->
                println("object: ${obj.key}")
            }
        }
    }
}
```

#### Put Object
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"
    val key = "your object name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        val result = client.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            this.body = ByteStream.fromString("Hello oss.")
        })
        println("PutObject done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### Get Object
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"
    val key = "your object name"
    val filePath = "download to file path"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        // Get data of object to local file
        val result = client.getObject(GetObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        println("GetObject done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### Get Object to local file
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import kotlinx.io.files.Path

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"
    val key = "your object name"
    val filePath = "download to file path"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        // Get data of object to local file
        val result = client.getObjectToFile(GetObjectRequest {
            this.bucket = bucket
            this.key = key
        }, Path(filePath))
        println("GetObject done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### Delete Object
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"
    val key = "your object name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        val result = client.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        println("DeleteObject done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
    }
}
```

#### Get Bucket Cors
```kotlin
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getBucketCors
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetBucketCorsRequest

suspend fun main() {
    val region = "cn-hangzhou"
    val bucket = "your bucket name"

    // Using the SDK's default configuration
    // loading credentials values from the environment variables
    val config = ClientConfiguration.loadDefault().apply{
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }
    OSSClient.create(config).use { client ->
        val result = client.getBucketCors(GetBucketCorsRequest {
            this.bucket = bucket
        })
        println("GetBucketCors done, StatusCode:${result.statusCode}, RequestId:${result.requestId}.")
        result.corsConfiguration?.corsRules?.forEach {
            println("allowedMethods: ${it.allowedMethods?.joinToString(" ")}")
            println("allowedHeaders: ${it.allowedHeaders?.joinToString(" ")}")
            println("allowedOrigins: ${it.allowedOrigins?.joinToString(" ")}")
            println("exposeHeaders: ${it.exposeHeaders?.joinToString(" ")}")
            println("maxAgeSeconds: ${it.maxAgeSeconds}")
        }
    }
}
```

##  Complete Example
More example projects can be found in the `Sample` folder

### Running Example
##### Running cli example
```shell
# build project
./gradlew :sample:cli:build

# Go to the sample code folder
cd sample/cli/build/libs/

# Configure credentials values from the environment variables
export OSS_ACCESS_KEY_ID="your access key id"
export OSS_ACCESS_KEY_SECRET="your access key secrect"

# Take ListBuckets as an example
java -jar cli-jvm.jar ListBuckets --region cn-hangzhou
```
##### Running UI example
> - Run `sample.composeApp` or `sample[jvm]`
> - Set `AccessKeyId`,`AccessKeySecret` and `Region`
> - Client on `Set Client`, complete client initialization
> - Taking ListObjects as an example, fill in the `Bucket name` and click on `ListObjects`

## License
> - Apache-2.0, see [license file](LICENSE)
