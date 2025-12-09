package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.DeleteObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.GetBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListObjectVersionsRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutBucketVersioningRequest
import com.aliyun.kotlin.sdk.service.oss2.models.PutObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import com.aliyun.kotlin.sdk.service.oss2.paginator.listObjectVersionsPaginator
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BucketVersioningTest: TestBase() {

    @Test
    fun testPutAndGetBucketVersioning() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        val result = defaultClient.getBucketVersioning(GetBucketVersioningRequest {
            this.bucket = bucket
        })
        assertEquals("Enabled", result.versioningConfiguration?.status)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testPutBucketVersioningWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.putBucketVersioning(PutBucketVersioningRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { invalidClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
        }) }
        assertEquals(exception.message, "request.versioningConfiguration is required")

        exception = assertFails { invalidClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration{}
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testGetBucketVersioningWithException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.getBucketVersioning(GetBucketVersioningRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { invalidClient.getBucketVersioning(GetBucketVersioningRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testListObjectVersions() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        for (i in 1..10) {
            defaultClient.putObject(PutObjectRequest {
                this.bucket = bucket
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
        }
        val result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
        })
        assertEquals(10, result.versions?.size)
        assertEquals(bucket, result.name)

        result.versions?.forEach { obj ->
            defaultClient.deleteObject(DeleteObjectRequest {
                this.bucket = bucket
                this.key = obj.key
                this.versionId = obj.versionId
            })
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectVersionsWithPrefix() = runTest {
        val bucket = randomBucketName()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
            prefix = "a/"
        })
        assertEquals(bucket, result.name)
        assertEquals("a/", result.prefix)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectVersionsWithMaxKeys() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })

        val result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
            maxKeys = 10
        })
        assertEquals(bucket, result.name)
        assertEquals(10, result.maxKeys)

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectVersionsWithDelimiter() = runTest {
        val bucket = randomBucketName()
        val key = "file"

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        val putResult = defaultClient.putObject(PutObjectRequest {
            this.bucket = bucket
            this.key = key
            body = ByteStream.fromString("Hello oss.")
        })

        val result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
            delimiter = "f"
        })
        assertEquals(bucket, result.name)
        assertEquals("f", result.delimiter)
        assertEquals("f", result.commonPrefixes?.first()?.prefix)

        defaultClient.deleteObject(DeleteObjectRequest {
            this.bucket = bucket
            this.key = key
            this.versionId = putResult.versionId
        })
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectVersionsWithKeyMarkerAndVersionIdMarker() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })
        for (i in 1..10) {
            defaultClient.putObject(PutObjectRequest {
                this.bucket = bucket
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
        }
        var result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
            maxKeys = 5
            keyMarker = key
        })
        assertEquals(5, result.versions?.size)
        assertEquals(bucket, result.name)
        assertEquals(key, result.keyMarker)
        assertNotNull(result.nextVersionIdMarker)
        assertNotNull(result.nextKeyMarker)
        val versions = result.versions?.toMutableList()

        val versionIdMarker = result.nextVersionIdMarker
        result = defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
            maxKeys = 5
            keyMarker = result.nextKeyMarker
            this.versionIdMarker = versionIdMarker
        })
        assertEquals(5, result.versions?.size)
        assertEquals(bucket, result.name)
        assertEquals(versionIdMarker, result.versionIdMarker)
        result.versions?.let { versions?.addAll(it) }

        versions?.forEach { obj ->
            defaultClient.deleteObject(DeleteObjectRequest {
                this.bucket = bucket
                this.key = obj.key
                this.versionId = obj.versionId
            })
        }
        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }

    @Test
    fun testListObjectVersionsException() = runTest {
        val bucket = randomBucketName()

        var exception: Throwable = assertFailsWith<IllegalArgumentException> { invalidClient.listObjectVersions(ListObjectVersionsRequest {}) }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails { defaultClient.listObjectVersions(ListObjectVersionsRequest {
            this.bucket = bucket
        }) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 404)
        assertEquals((exception.cause as ServiceException).errorCode, "NoSuchBucket")
    }

    @Test
    fun testListObjectVersionsPaginator() = runTest {
        val bucket = randomBucketName()
        val key = randomObjectKey()

        defaultClient.putBucket(PutBucketRequest {
            this.bucket = bucket
        })
        defaultClient.putBucketVersioning(PutBucketVersioningRequest {
            this.bucket = bucket
            versioningConfiguration = VersioningConfiguration {
                status = "Enabled"
            }
        })

        for (i in 1..10) {
            defaultClient.putObject(PutObjectRequest {
                this.bucket = bucket
                this.key = "$key-$i"
                body = ByteStream.fromString("Hello oss.")
            })
        }
        defaultClient.listObjectVersionsPaginator(ListObjectVersionsRequest {
            this.bucket = bucket
        }, PaginatorOptions {
            limit = 5
        }).collect {
            assertEquals(5, it.versions?.size)
            assertEquals(bucket, it.name)

            it.versions?.forEach { obj ->
                defaultClient.deleteObject(DeleteObjectRequest {
                    this.bucket = bucket
                    this.key = obj.key
                    this.versionId = obj.versionId
                })
            }
        }

        defaultClient.deleteBucket(DeleteBucketRequest {
            this.bucket = bucket
        })
    }
}