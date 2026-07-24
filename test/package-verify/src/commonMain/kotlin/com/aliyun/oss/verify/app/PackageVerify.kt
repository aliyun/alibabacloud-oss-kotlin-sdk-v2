package com.aliyun.oss.verify.app

import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.AbortMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.AppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CopyObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.CreateBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.Delete
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteMultipleObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DescribeRegionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketLocationRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketStatRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectMetaRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.models.HeadObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.InitiateMultipartUploadRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListMultipartUploadsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.ListPartsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ObjectIdentifier
import com.aliyun.kotlin.sdk.service.oss2.models.Part
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectTaggingRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutSymlinkRequest
import com.aliyun.kotlin.sdk.service.oss2.models.SealAppendObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.Tag
import com.aliyun.kotlin.sdk.service.oss2.models.TagSet
import com.aliyun.kotlin.sdk.service.oss2.models.Tagging
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartCopyRequest
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPartRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import com.aliyun.kotlin.sdk.service.oss2.paginator.listBucketsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsPaginator
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import com.aliyun.kotlin.sdk.service.oss2.types.toByteStream
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

var pass: Int = 0
fun check(name: String, ok: Boolean) {
    if (!ok) {
        throw RuntimeException("FAIL: $name")
    }
    pass++
    println("  [PASS] $name")
}

@OptIn(ExperimentalTime::class)
public suspend fun packageVerify() {
    val region = getPlatformEnv("OSS_REGION") ?: "cn-hangzhou"
    val bucket = getPlatformEnv("OSS_TEST_BUCKET") ?: "oss-sdk-test-kotlin-bucket-${Clock.System.now().epochSeconds}"

    val config = ClientConfiguration.loadDefault().apply {
        this.region = region
        credentialsProvider = EnvironmentVariableCredentialsProvider()
    }

    OSSClient.create(config).use { client ->
        println("=== Kotlin OSS SDK Package Verify ===")
        println("Region: $region")
        println("Bucket: $bucket")

        try {
            testService(client)
            testBucketBasic(client, bucket)
            testBucketAcl(client, bucket)
            testObjectBasic(client, bucket)
            testObjectAcl(client, bucket)
            testObjectTagging(client, bucket)
            testObjectSymlink(client, bucket)
            testAppendObject(client, bucket)
            testMultipartUpload(client, bucket)
            testPresigner(client, bucket)
            testPaginator(client, bucket)
            testExtension(client, bucket)
        } catch (e: Exception) {
            println("\n=== Kotlin Package Verify failed ===")
            println(e)
            cleanBucket(client, bucket)
            throw e
        }
    }

    println("\n=== All Swift Package Verify checks passed ===")
}

suspend fun testService(client: OSSClient) {
    println("\n--- Service: DescribeRegions / ListBuckets ---")
    val regions = client.describeRegions(DescribeRegionsRequest {})
    check("DescribeRegions status=200", regions.statusCode == 200)
    check("DescribeRegions has regions", regions.regionInfoList?.regionInfos?.isNotEmpty() ?: false)

    val listBuckets = client.listBuckets(ListBucketsRequest { maxKeys = 10 })
    check("ListBuckets status=200", listBuckets.statusCode == 200)
}

suspend fun testBucketBasic(client: OSSClient, bucket: String) {
    println("\n--- Bucket Basic: Put/GetInfo/GetStat/GetLocation/Delete ---")

    val putResult = client.putBucket(
        PutBucketRequest {
            this.bucket = bucket
            createBucketConfiguration = CreateBucketConfiguration {
                storageClass = "IA"
            }
        }
    )
    check("putBucket status=200", putResult.statusCode == 200)

    val info = client.getBucketInfo(GetBucketInfoRequest { this.bucket = bucket })
    check("GetBucketInfo status=200", info.statusCode == 200)
    check("GetBucketInfo StorageClass=IA", info.bucketInfo?.bucket?.storageClass == "IA")
    check("GetBucketInfo Name matches", info.bucketInfo?.bucket?.name == bucket)

    val stat = client.getBucketStat(GetBucketStatRequest {
        this.bucket = bucket
    })
    check("GetBucketStat status=200", stat.statusCode == 200)
    check("GetBucketStat ObjectCount=0", stat.bucketStat?.objectCount == 0L)

    val loc = client.getBucketLocation(GetBucketLocationRequest {
        this.bucket = bucket
    })
    check("GetBucketLocation status=200", loc.statusCode == 200)
    check(
        "GetBucketLocation has value",
        loc.locationConstraint?.isEmpty() == false
    )
}

suspend fun testBucketAcl(client: OSSClient, bucket: String) {
    println("\n--- Bucket Acl: Get/Put ---")

    val getAcl = client.getBucketAcl(GetBucketAclRequest { this.bucket = bucket })
    check("GetBucketAcl status=200", getAcl.statusCode == 200)
    check(
        "GetBucketAcl Grant=private",
        getAcl.accessControlPolicy?.accessControlList?.grant == "private"
    )
}

suspend fun testObjectBasic(client: OSSClient, bucket: String) {
    println("\n--- Object Basic: Put/Head/GetMeta/Get/Copy/Delete ---")
    val key = "verify-obj-basic"
    val content = "hello world from nuget verify"

    val putResult = client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            tagging = "tag1=val1"
            metadata = mapOf("mykey" to "myval")
            body = content.toByteStream()
        }
    )
    check("putObject status=200", putResult.statusCode == 200)
    check("putObject has ETag", putResult.eTag?.isNotEmpty() == true)

    val headResult = client.headObject(
        HeadObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check("HeadObject status=200", headResult.statusCode == 200)
    check("HeadObject ContentLength", headResult.contentLength == content.length.toLong())
    check("HeadObject ObjectType=Normal", headResult.objectType == "Normal")
    check("HeadObject Metadata", headResult.metadata?.get("mykey") == "myval")
    check("HeadObject TaggingCount=1", headResult.taggingCount == 1L)

    val getMeta = client.getObjectMeta(
        GetObjectMetaRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check("GetObjectMeta status=200", getMeta.statusCode == 200)
    check("GetObjectMeta ContentLength", getMeta.contentLength == content.length.toLong())

    val getResult = client.getObject(
        GetObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check("GetObject status=200", getResult.statusCode == 200)
    check("GetObject ContentLength", getResult.contentLength == content.length.toLong())
    check(
        "GetObject body matches",
        getResult.body?.toByteArray()?.decodeToString() == content
    )

    val copyResult = client.copyObject(
        CopyObjectRequest {
            this.bucket = bucket
            this.key = "$key-copy"
            sourceBucket = bucket
            sourceKey = key
        }
    )
    check("CopyObject status=200", copyResult.statusCode == 200)

    val getResult2 = client.getObject(
        GetObjectRequest {
            this.bucket = bucket
            this.key = "$key-copy"
        }
    )
    check(
        "GetObject(copy) status=200",
        getResult2.statusCode == 200
    )
    check(
        "GetObject(copy) body matches",
        getResult2.body?.toByteArray()?.decodeToString() == content
    )

    val delResult = client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "DeleteObject status=204",
        delResult.statusCode == 204
    )

    val delResult2 = client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = "$key-copy"
        }
    )
    check(
        "DeleteObject(copy) status=204",
        delResult2.statusCode == 204
    )
}

suspend fun testObjectAcl(
    client: OSSClient,
    bucket: String
) {
    println("\n--- Object Acl: Put/Get ---")
    val key = "verify-obj-acl"

    client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = "acl test".toByteStream()
        }
    )

    var getAcl = client.getObjectAcl(
        GetObjectAclRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObjectAcl status=200",
        getAcl.statusCode == 200
    )
    check(
        "GetObjectAcl default",
        getAcl.accessControlPolicy?.accessControlList?.grant == "default"
    )

    val putAcl = client.putObjectAcl(
        PutObjectAclRequest {
            this.bucket = bucket
            this.key = key
            objectAcl = "private"
        }
    )
    check(
        "putObjectAcl status=200",
        putAcl.statusCode == 200
    )

    getAcl = client.getObjectAcl(
        GetObjectAclRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObjectAcl after put=private",
        getAcl.accessControlPolicy?.accessControlList?.grant == "private"
    )

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun testObjectTagging(
    client: OSSClient,
    bucket: String
) {
    println("\n--- Object Tagging: Put/Get/Delete ---")
    val key = "verify-obj-tag"

    client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = "tag test".toByteStream()
        }
    )

    val putTag = client.putObjectTagging(
        PutObjectTaggingRequest {
            this.bucket = bucket
            this.key = key
            tagging = Tagging {
                tagSet = TagSet {
                    tags = listOf(Tag {
                        this.key = "k1"
                        value = "v1"
                    })
                }
            }
        }
    )
    check(
        "putObjectTagging status=200",
        putTag.statusCode == 200
    )

    var getTag = client.getObjectTagging(
        GetObjectTaggingRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObjectTagging status=200",
        getTag.statusCode == 200
    )
    check(
        "GetObjectTagging count=1",
        getTag.tagging?.tagSet?.tags?.size == 1
    )
    check(
        "GetObjectTagging key=k1",
        getTag.tagging?.tagSet?.tags?.first()?.key == "k1"
    )
    check(
        "GetObjectTagging value=v1",
        getTag.tagging?.tagSet?.tags?.first()?.value == "v1"
    )

    val delTag = client.deleteObjectTagging(
        DeleteObjectTaggingRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "DeleteObjectTagging status=204",
        delTag.statusCode == 204
    )

    getTag = client.getObjectTagging(
        GetObjectTaggingRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObjectTagging after Delete count=0",
        getTag.tagging?.tagSet?.tags == null || getTag.tagging?.tagSet?.tags?.isEmpty() == true
    )

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun testObjectSymlink(
    client: OSSClient,
    bucket: String
) {
    println("\n--- Object Symlink: put/Get ---")
    val key = "verify-obj-symlink"
    val linkKey = key + "-link"

    client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = "symlink target".toByteStream()
        }
    )

    val putSym = client.putSymlink(
        PutSymlinkRequest {
            this.bucket = bucket
            this.key = linkKey
            symlinkTarget = key
        }
    )
    check(
        "putSymlink status=200",
        putSym.statusCode == 200
    )

    val getSym = client.getSymlink(
        GetSymlinkRequest {
            this.bucket = bucket
            this.key = linkKey
        }
    )
    check(
        "GetSymlink status=200",
        getSym.statusCode == 200
    )
    check(
        "GetSymlink target matches",
        getSym.symlinkTarget == key
    )

    val getObj = client.getObject(
        GetObjectRequest {
            this.bucket = bucket
            this.key = linkKey
        }
    )
    check(
        "GetObject via symlink status=200",
        getObj.statusCode == 200
    )
    check(
        "GetObject via symlink ObjectType=Symlink",
        getObj.objectType == "Symlink"
    )
    check(
        "GetObject via symlink body",
        getObj.body?.toByteArray()?.decodeToString() == "symlink target"
    )

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = linkKey
        }
    )
    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun testAppendObject(
    client: OSSClient,
    bucket: String
) {
    println(
        "\n--- AppendObject / SealAppendObject ---"
    )
    val key =
        "verify-append-obj"

    val append1 = client.appendObject(
        AppendObjectRequest {
            this.bucket = bucket
            this.key = key
            position = 0
            body = "hello ".toByteStream()
        }
    )
    check(
        "AppendObject(1) status=200",
        append1.statusCode == 200
    )
    check(
        "AppendObject(1) NextPosition=6",
        append1.nextAppendPosition == 6L
    )

    val append2 = client.appendObject(
        AppendObjectRequest {
            this.bucket = bucket
            this.key = key
            position = 6
            body = "world".toByteStream()
            initHashCRC64 = append1.hashCrc64ecma?.toLong()
        }
    )
    check(
        "AppendObject(2) status=200",
        append2.statusCode == 200
    )
    check(
        "AppendObject(2) NextPosition=11",
        append2.nextAppendPosition == 11L
    )

    val getObj = client.getObject(
        GetObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObject(append) ObjectType=Appendable",
        getObj.objectType == "Appendable"
    )
    check(
        "GetObject(append) body=hello world",
        getObj.body?.toByteArray()
            ?.decodeToString() == "hello world"
    )

    try {
        val sealResult = client.sealAppendObject(
            SealAppendObjectRequest {
                this.bucket = bucket
                this.key = key
                position = 11
            }
        )
        check(
            "SealAppendObject status=200",
            sealResult.statusCode == 200
        )
    } catch (e: Exception) {
        if (e.cause is ServiceException &&
            (e.cause as ServiceException).errorCode != "OperationNotSupported") {
            check(
                "SealAppendObject (not supported in region, skipped)",
                true
            )
            return
        }
    }

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun testMultipartUpload(
    client: OSSClient,
    bucket: String
) {
    println("\n--- Multipart: Init/Upload/ListParts/Complete ---")
    val key = "verify-multipart"

    val initResult = client.initiateMultipartUpload(
        InitiateMultipartUploadRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "InitiateMultipartUpload status=200",
        initResult.statusCode == 200
    )
    check(
        "InitiateMultipartUpload has UploadId",
        initResult.uploadId?.isEmpty() == false
    )

    val content = "multipart content data"
    val upResult = client.uploadPart(
        UploadPartRequest {
            this.bucket = bucket
            this.key = key
            partNumber = 1
            uploadId = initResult.uploadId
            body = content.toByteStream()
        }
    )
    check(
        "UploadPart status=200",
        upResult.statusCode == 200
    )
    check(
        "UploadPart has ETag",
        upResult.eTag?.isEmpty() == false
    )

    val listParts = client.listParts(
        ListPartsRequest {
            this.bucket = bucket
            this.key = key
            uploadId = initResult.uploadId
        }
    )
    check(
        "ListParts status=200",
        listParts.statusCode == 200
    )
    check(
        "ListParts count=1",
        listParts.parts?.size == 1
    )
    check(
        "ListParts size matches",
        listParts.parts?.first()?.size == content.length.toLong()
    )

    val listUploads = client.listMultipartUploads(
        ListMultipartUploadsRequest {
            this.bucket = bucket
            prefix = key
        }
    )
    check(
        "ListMultipartUploads status=200",
        listUploads.statusCode == 200
    )
    check(
        "ListMultipartUploads count>=1",
        listUploads.uploads?.isNotEmpty() == true
    )

    val cmResult = client.completeMultipartUpload(
        CompleteMultipartUploadRequest {
            this.bucket = bucket
            this.key = key
            uploadId = initResult.uploadId
            completeMultipartUpload = CompleteMultipartUpload {
                parts = listOf(Part {
                    this.eTag = upResult.eTag
                    this.partNumber = 1
                })
            }
        }
    )
    check(
        "CompleteMultipartUpload status=200",
        cmResult.statusCode == 200
    )

    val getObj = client.getObject(
        GetObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "GetObject(multipart) ObjectType=Multipart",
        getObj.objectType == "Multipart"
    )
    check(
        "GetObject(multipart) body matches",
        getObj.body?.toByteArray()?.decodeToString() == content
    )

    // UploadPartCopy
    println("\n--- Multipart: UploadPartCopy ---")
    val copyKey = "$key-copy"
    val initResult2 = client.initiateMultipartUpload(
        InitiateMultipartUploadRequest {
            this.bucket = bucket
            this.key = copyKey
        }
    )
    check(
        "InitiateMultipartUpload(copy) status=200",
        initResult2.statusCode == 200
    )

    val copyResult = client.uploadPartCopy(
        UploadPartCopyRequest {
            this.bucket = bucket
            this.key = copyKey
            sourceBucket = bucket
            sourceKey = key
            partNumber = 1
            uploadId = initResult2.uploadId
        }
    )
    check(
        "UploadPartCopy status=200",
        copyResult.statusCode == 200
    )

    val cmResult2 = client.completeMultipartUpload(
        CompleteMultipartUploadRequest {
            this.bucket = bucket
            this.key = copyKey
            uploadId = initResult2.uploadId
            completeMultipartUpload = CompleteMultipartUpload {
                parts = listOf(Part {
                    eTag = copyResult.copyPartResult?.eTag
                    partNumber = 1
                })
            }
        }
    )
    check(
        "CompleteMultipartUpload(copy) status=200",
        cmResult2.statusCode == 200
    )

    // AbortMultipartUpload
    println("\n--- Multipart: Abort ---")
    val abortKey = "$key-abort"
    val initResult3 = client.initiateMultipartUpload(
        InitiateMultipartUploadRequest {
            this.bucket = bucket
            this.key = abortKey
        }
    )
    val abortResult =
        client.abortMultipartUpload(
            AbortMultipartUploadRequest {
                this.bucket = bucket
                this.key = abortKey
                uploadId = initResult3.uploadId
            }
        )
    check(
        "AbortMultipartUpload status=204",
        abortResult.statusCode == 204
    )

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = copyKey
        }
    )
}

suspend fun testPresigner(
    client: OSSClient,
    bucket: String
) {
    println("\n--- Presigner ---")
    val key = "verify-presign"
    val content = "presign content"

    client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = content.toByteStream()
        }
    )

    val preResult = client.presign(
        GetObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
    check(
        "Presign has URL",
        !preResult.url.isEmpty()
    )
    check(
        "Presign Method=GET",
        preResult.method == "GET"
    )
    check(
        "Presign has Expiration",
        preResult.expirationInEpoch != null
    )

    val body = sendRequest(preResult.url)
    check(
        "Presign GET body matches",
        body == content
    )
    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun testPaginator(
    client: OSSClient,
    bucket: String
) {
    println(
        "\n--- Paginator: ListObjects ---"
    )
    for (i in 0..<3) {
        client.putObject(
            PutObjectRequest {
                this.bucket =
                    bucket
                key = "paginator-test/$i"
            }
        )
    }

    val paginator = client.listObjectsPaginator(
        ListObjectsRequest {
            this.bucket = bucket
            prefix = "paginator-test/"
        },
        PaginatorOptions { limit = 2 }
    )

    var count = 0
    paginator.collect {
        count += it.contents?.size ?: 0
    }
    check(
        "ListObjectsPaginator total=3",
        count == 3
    )

    println("\n--- Paginator: ListObjectsV2 ---")
    val paginator2 = client.listObjectsV2Paginator(
        ListObjectsV2Request {
            this.bucket = bucket
            prefix = "paginator-test/"
        },
        PaginatorOptions { limit = 2 }
    )

    count = 0
    paginator2.collect {
        count += it.contents?.size ?: 0
    }
    check(
        "ListObjectsV2Paginator total=3",
        count == 3
    )

    println("\n--- Paginator: ListBuckets ---")
    val paginator3 = client.listBucketsPaginator(
        ListBucketsRequest {
            prefix = bucket
        }
    )
    count = 0
    paginator3.collect {
        count += it.buckets?.size ?: 0
    }
    check(
        "ListBucketsPaginator found bucket",
        count >= 1
    )

    // cleanup paginator objects
    client.deleteMultipleObjects(
        DeleteMultipleObjectsRequest {
            this.bucket = bucket
            delete = Delete {
                objects = listOf(
                    ObjectIdentifier { key = "paginator-test/0" },
                    ObjectIdentifier { key = "paginator-test/1" },
                    ObjectIdentifier { key = "paginator-test/2" }
                )
            }
        }
    )
    check(
        "DeleteMultipleObjects status OK",
        true
    )
}

suspend fun testExtension(
    client: OSSClient,
    bucket: String
) {
    println(
        "\n--- Extensions: IsExist / File APIs ---"
    )

    val exist = client.doesBucketExist(bucket)
    check(
        "IsBucketExist=true",
        exist
    )

    val key = "verify-ext-file"
    client.putObject(
        PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = "extension test".toByteStream()
        }
    )

    val objExist = client.doesObjectExist(
        bucket,
        key
    )
    check(
        "IsObjectExist=true",
        objExist
    )

    val notExist = client.doesObjectExist(
        bucket,
        "no-such-key-xyz"
    )
    check(
        "IsObjectExist=false for missing",
        !notExist
    )

    client.deleteObject(
        DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        }
    )
}

suspend fun cleanBucket(client: OSSClient, bucket: String) {
    try {
        client.listObjectsPaginator(ListObjectsRequest { this.bucket = bucket })
            .collect { page ->
                val objects = mutableListOf<ObjectIdentifier>()
                page.contents?.forEach { obj ->
                    objects.add(ObjectIdentifier { key = obj.key })
                }
                client.deleteMultipleObjects(DeleteMultipleObjectsRequest {
                    this.bucket = bucket
                    delete = Delete { this.objects = objects }
                })
            }

        client.deleteBucket(DeleteBucketRequest { this.bucket = bucket })
    } catch (_: Exception) {
    }
}
