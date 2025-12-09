package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.CreateBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketAclRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketInfoRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketLocationRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketStatRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectsV2Request.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectsV2Paginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BucketBasicTest: TestBase() {

    @Test
    fun testPutBucket() = runTest {
        val bucket = randomBucketName()

        val result = defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        assertEquals(result.statusCode, 200)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutBucketWithCreateBucketConfiguration() = runTest {
        val bucket = randomBucketName()

        val result = defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
            createBucketConfiguration = CreateBucketConfiguration {
                storageClass = "IA"
                dataRedundancyType = "ZRS"
            }
        })
        assertEquals(result.statusCode, 200)

        val bucketInfo = defaultClient.getBucketInfo(GetBucketInfoRequest{
            this.bucket = bucket
        }).bucketInfo
        assertEquals("IA", bucketInfo?.bucket?.storageClass)
        assertEquals("ZRS", bucketInfo?.bucket?.dataRedundancyType)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutBucketWithAcl() = runTest {
        val bucket = randomBucketName()

        val result = defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
            acl = "private"
        })
        assertEquals(result.statusCode, 200)

        val aclResult = defaultClient.getBucketAcl(GetBucketAclRequest {
            this.bucket = bucket
        })
        assertEquals("private", aclResult.accessControlPolicy?.accessControlList?.grant)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutBucketWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putBucket(PutBucketRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { invalidClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteBucket() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
        assertEquals(result.statusCode, 204)
    }

    @Test
    fun testDeleteBucketWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.deleteBucket(DeleteBucketRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testGetBucketStat() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.getBucketStat(GetBucketStatRequest {
            this.bucket = bucket
        })
        assertEquals(200, result.statusCode)
        assertEquals(10, result.bucketStat?.storage)
        assertEquals(1, result.bucketStat?.objectCount)
        assertEquals(0, result.bucketStat?.archiveStorage)
        assertEquals(0, result.bucketStat?.archiveObjectCount)
        assertEquals(0, result.bucketStat?.archiveRealStorage)
        assertEquals(0, result.bucketStat?.deleteMarkerCount)
        assertEquals(0, result.bucketStat?.deepColdArchiveRealStorage)
        assertEquals(0, result.bucketStat?.deepColdArchiveObjectCount)
        assertEquals(0, result.bucketStat?.deepColdArchiveStorage)
        assertEquals(0, result.bucketStat?.coldArchiveObjectCount)
        assertEquals(0, result.bucketStat?.coldArchiveRealStorage)
        assertEquals(0, result.bucketStat?.coldArchiveStorage)
        assertEquals(0, result.bucketStat?.infrequentAccessRealStorage)
        assertEquals(0, result.bucketStat?.infrequentAccessStorage)
        assertEquals(0, result.bucketStat?.infrequentAccessObjectCount)
        assertEquals(10, result.bucketStat?.standardStorage)
        assertEquals(1, result.bucketStat?.standardObjectCount)
        assertNotNull(result.bucketStat?.lastModifiedTime)
        assertEquals(0, result.bucketStat?.multipartUploadCount)
        assertEquals(0, result.bucketStat?.liveChannelCount)
        assertEquals(0, result.bucketStat?.multipartPartCount)

        defaultClient.listObjectsV2Paginator(
            ListObjectsV2Request {
                this.bucket = bucket
            }
        ).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucket
                    this.key = obj.key
                })
            }
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testGetBucketStatException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getBucketStat(GetBucketStatRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { defaultClient.getBucketStat(GetBucketStatRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testListObjects() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
        })
        assertEquals(200, result.statusCode)
        assertEquals(bucket, result.name)
        assertEquals(1, result.contents?.size)
        assertEquals(false, result.isTruncated)
        assertEquals(key, result.contents?.first()?.key)
        assertEquals(10, result.contents?.first()?.size)
        assertEquals("Standard", result.contents?.first()?.storageClass)
        assertNotNull(result.contents?.first()?.eTag)

        defaultClient.listObjectsV2Paginator(
            ListObjectsV2Request {
                this.bucket = bucket
            }
        ).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucket
                    this.key = obj.key
                })
            }
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsWithMaxKeys() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
            maxKeys = 10
        })
        assertEquals(200, result.statusCode)
        assertEquals(10, result.maxKeys)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsWithPrefix() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
            prefix = "a/"
        })
        assertEquals(200, result.statusCode)
        assertEquals("a/", result.prefix)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsWithDelimiter() = runTest {
        val bucket = randomBucketName()
        val key = "file1"

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
            delimiter = "f"
        })
        assertEquals(200, result.statusCode)
        assertEquals("f", result.delimiter)
        assertEquals("f", result.commonPrefixes?.first()?.prefix)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsWithMarker() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        for (i in 0..1) {
            defaultClient.putObject(PutObjectRequest {
                this.bucket = bucket
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
        }

        val result = defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
            maxKeys = 1
            marker = key
        })
        assertEquals(200, result.statusCode)
        assertEquals(key, result.marker)
        assertNotNull(result.nextMarker)

        defaultClient.listObjectsV2Paginator(
            ListObjectsV2Request {
                this.bucket = bucket
            }
        ).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucket
                    this.key = obj.key
                })
            }
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.listObjects(ListObjectsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testListObjectsV2() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
        })
        assertEquals(200, result.statusCode)
        assertEquals(bucket, result.name)
        assertEquals(1, result.contents?.size)
        assertEquals(false, result.isTruncated)
        assertEquals(key, result.contents?.first()?.key)
        assertEquals(10, result.contents?.first()?.size)
        assertEquals("Standard", result.contents?.first()?.storageClass)
        assertNotNull(result.contents?.first()?.eTag)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsV2WithMaxKeys() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
            maxKeys = 10
        })
        assertEquals(200, result.statusCode)
        assertEquals(10, result.maxKeys)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsV2WithPrefix() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
            prefix = "a/"
        })
        assertEquals(200, result.statusCode)
        assertEquals("a/", result.prefix)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsV2WithDelimiter() = runTest {
        val bucket = randomBucketName()
        val key = "file1"

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
            delimiter = "f"
        })
        assertEquals(200, result.statusCode)
        assertEquals("f", result.delimiter)
        assertEquals("f", result.commonPrefixes?.first()?.prefix)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsV2WithContinuationToken() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        for (i in 0..1) {
            defaultClient.putObject(PutObjectRequest {
                this.bucket = bucket
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
        }

        var result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
            maxKeys = 1
        })
        assertEquals(200, result.statusCode)
        assertNotNull(result.nextContinuationToken)

        val continuationToken = result.nextContinuationToken
        result = defaultClient.listObjectsV2(ListObjectsV2Request {
            this.bucket = bucket
            maxKeys = 1
            this.continuationToken = continuationToken
        })
        assertEquals(200, result.statusCode)
        assertEquals(continuationToken, result.continuationToken)

        defaultClient.listObjectsV2Paginator(
            ListObjectsV2Request {
                this.bucket = bucket
            }
        ).collect {
            it.contents?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucket
                    this.key = obj.key
                })
            }
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectsV2Exception() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.listObjects(ListObjectsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { defaultClient.listObjects(ListObjectsRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testGetBucketInfo() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
            acl = "private"
            createBucketConfiguration = CreateBucketConfiguration {
                storageClass = "IA"
                dataRedundancyType = "ZRS"
            }
        })

        val result = defaultClient.getBucketInfo(GetBucketInfoRequest {
            this.bucket = bucket
        })
        assertEquals(result.statusCode, 200)
        assertEquals(bucket, result.bucketInfo?.bucket?.name)
        assertEquals("IA", result.bucketInfo?.bucket?.storageClass)
        assertEquals("ZRS", result.bucketInfo?.bucket?.dataRedundancyType)
        assertNotNull(result.bucketInfo?.bucket?.creationDate)
        assertEquals("private", result.bucketInfo?.bucket?.accessControlList?.grant)
        assertEquals("oss-$OSS_TEST_REGION", result.bucketInfo?.bucket?.location)
        assertEquals(false, result.bucketInfo?.bucket?.blockPublicAccess)
        assertEquals("Disabled", result.bucketInfo?.bucket?.crossRegionReplication)
        assertNotNull(result.bucketInfo?.bucket?.intranetEndpoint)
        assertEquals("Disabled", result.bucketInfo?.bucket?.accessMonitor)
        assertNotNull(result.bucketInfo?.bucket?.resourceGroupId)
        assertNotNull(result.bucketInfo?.bucket?.transferAcceleration)
        assertNotNull(result.bucketInfo?.bucket?.extranetEndpoint)
        assertNotNull(result.bucketInfo?.bucket?.owner?.id)
        assertNotNull(result.bucketInfo?.bucket?.owner?.displayName)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testGetBucketInfoWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getBucketInfo(GetBucketInfoRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { invalidClient.getBucketInfo(GetBucketInfoRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testGetBucketLocation() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
            createBucketConfiguration = CreateBucketConfiguration {
                storageClass = "IA"
                dataRedundancyType = "ZRS"
            }
        })

        val result = defaultClient.getBucketLocation(GetBucketLocationRequest {
            this.bucket = bucket
        })
        assertEquals(result.statusCode, 200)
        assertEquals("oss-$OSS_TEST_REGION", result.locationConstraint)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testGetBucketLocationWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getBucketLocation(GetBucketLocationRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { invalidClient.getBucketLocation(GetBucketLocationRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }
}