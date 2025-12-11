# Alibaba Cloud OSS SDK for Kotlin v2

[![GitHub version](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-kotlin-sdk-v2.svg)](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-kotlin-sdk-v2)

alibabacloud-oss-kotlin-sdk-v2 是OSS在Kotlin编译语言下的第二版SDK

## [English](README.md)

## 关于
> - 此Kotlin SDK基于[阿里云对象存储服务](http://www.aliyun.com/product/oss/)官方API构建。
> - 阿里云对象存储（Object Storage Service，简称OSS），是阿里云对外提供的海量，安全，低成本，高可靠的云存储服务。
> - OSS适合存放任意文件类型，适合各种网站、开发企业及开发者使用。
> - 使用此SDK，用户可以方便地在任何应用、任何时间、任何地点上传，下载和管理数据。

## 运行环境
 - 适用于`Kotlin 2.1.0`及以上版本
 - 支持平台: Android, Desktop(JVM)
 
## 安装方法
### 通过Gradle安装
添加如下依赖项到您的项目中
```kts
implementation("com.aliyun:kotlin-oss-v2:<latest-version>")
// implementation("com.aliyun:kotlin-oss-v2-extension:<latest-version>")
```

> **Note:**
> </br>`kotlin-oss-v2` 提供了bucket基础接口、对象类接口 和 高级接口（例如 分页器、预签名）。
> </br>`kotlin-oss-v2-extension` 包含除bucket基础接口以外的配置类接口, 例如 跨域资源共享接口。

### 通过源码安装

克隆项目源代码后，可以运行gradle命令进行安装:
```shell
# clone工程
$ git clone https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git

# 进入目录
$ cd alibabacloud-oss-kotlin-sdk-v2/

# 发布到 MavenLocal
$ ./gradlew clean publishToMavenLocal
```
添加`mavenLocal`
```kts
repositories {
    ...
    mavenLocal()
}
```
添加依赖到你的项目中
```kts
implementation("com.aliyun:kotlin-oss-v2:<latest-version>")
// implementation("com.aliyun:kotlin-oss-v2-extension:<latest-version>")
```

### 手动引入依赖包
```shell
# clone工程
$ git clone https://github.com/aliyun/alibabacloud-oss-kotlin-sdk-v2.git

# 进入目录
$ cd aliyun-oss-kotlin-sdk-v2/

# 执行打包脚本
$ ./gradlew :oss-sdk:assemble
# ./gradlew :oss-sdk-extension:assemble

# 以aar为例
# 进入打包生成目录，包生成在该目录下
$ cd oss-sdk/build/outputs/aar && ls
# cd oss-sdk-extension/build/outputs/aar && ls
```

## 快速使用
#### 获取存储空间列表（List Buckets）
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

#### 创建存储空间（Put Bucket）
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

#### 删除存储空间（Delete Bucket）
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

#### 获取文件列表（List Objects）
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

#### 上传文件（Put Object）
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

#### 下载到内存（Get Object）
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

#### 下载到本地文件
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

#### 删除文件(Delete Object)
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

#### 获取跨域资源共享规则(Get Bucket Cors)
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

## 更多示例
请参看`Sample`目录

### 运行示例
##### 运行cli示例
```shell
# 编译项目 
./gradlew :sample:cli:build

# 进入示例程序目录 
cd sample/cli/build/libs/

# 通过环境变量，配置访问凭证
export OSS_ACCESS_KEY_ID="your access key id"
export OSS_ACCESS_KEY_SECRET="your access key secrect"

# 以 ListBuckets 为例
java -jar cli-jvm.jar ListBuckets --region cn-hangzhou
```
##### 运行UI示例
> - 运行`sample.composeApp`或`sample[jvm]`
> - 填写`AccessKeyId`,`AccessKeySecret`及`Region`
> - 点击`Set Client`完成client初始化
> - 以 ListObjects 为例，填写bucket名称，点击`ListObjects`

## 许可协议
> - Apache-2.0, 请参阅 [许可文件](LICENSE)
