package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketV2Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListObjectsV2Test {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListObjectsV2Request {}
        assertNull(request.bucket)
        assertNull(request.delimiter)
        assertNull(request.maxKeys)
        assertNull(request.continuationToken)
        assertNull(request.prefix)
        assertNull(request.encodingType)
        assertNull(request.fetchOwner)
        assertNull(request.startAfter)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestWithFullValuesFromDsl() {
        val request = ListObjectsV2Request {
            bucket = "my-bucket"
            delimiter = "/"
            maxKeys = 100
            continuationToken = "continuationToken1"
            prefix = "prefix/"
            encodingType = "url"
            fetchOwner = true
            startAfter = "startAfter"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("continuationToken1", request.continuationToken)
        assertEquals("prefix/", request.prefix)
        assertEquals("url", request.encodingType)
        assertEquals(true, request.fetchOwner)
        assertEquals("startAfter", request.startAfter)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("continuation-token")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
            request.parameters.containsKey("fetch-owner")
            request.parameters.containsKey("start-after")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListObjectsV2Request.Builder()
        builder.bucket = "my-bucket"
        builder.delimiter = "/"
        builder.maxKeys = 100
        builder.continuationToken = "continuationToken1"
        builder.prefix = "prefix/"
        builder.encodingType = "url"
        builder.fetchOwner = true
        builder.startAfter = "startAfter"

        val request = ListObjectsV2Request(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("continuationToken1", request.continuationToken)
        assertEquals("prefix/", request.prefix)
        assertEquals("url", request.encodingType)
        assertEquals(true, request.fetchOwner)
        assertEquals("startAfter", request.startAfter)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("continuation-token")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
            request.parameters.containsKey("fetch-owner")
            request.parameters.containsKey("start-after")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListObjectsV2Result {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.continuationToken)
        assertNull(result.nextContinuationToken)
        assertNull(result.maxKeys)
        assertNull(result.keyCount)
        assertNull(result.isTruncated)
        assertNull(result.encodingType)
        assertNull(result.prefix)
        assertNull(result.delimiter)
        assertNull(result.contents)
        assertNull(result.commonPrefixes)
        assertNull(result.name)
        assertNull(result.startAfter)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListObjectsV2Result {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListBucketV2Result {
                continuationToken = "continuationToken"
                nextContinuationToken = "continuationToken1"
                maxKeys = 100
                keyCount = 50
                isTruncated = true
                name = "bucket-name"
                encodingType = "url"
                prefix = "prefix/"
                delimiter = "/"
                startAfter = "startAfter"
                contents = listOf(
                    ObjectSummary {
                        key = "key0"
                        type = "Normal"
                        storageClass = "IA"
                        restoreInfo = "restore"
                        lastModified = "2012-02-24T08:42:32.000Z"
                        eTag = "5B3C1A2E053D763E1B002CC607C5A0FE1****"
                        size = 100
                        owner = Owner {
                            id = "0022012****"
                            displayName = "user_example"
                        }
                        transitionTime = "2013-02-24T08:42:32.000Z"
                    }
                )
                commonPrefixes = listOf(
                    CommonPrefix {
                        prefix = "prefix1/"
                    }
                )
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("continuationToken", result.continuationToken)
        assertEquals("continuationToken1", result.nextContinuationToken)
        assertEquals(100, result.maxKeys)
        assertEquals(50, result.keyCount)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals("startAfter", result.startAfter)
        assertEquals(1, result.contents?.size)
        assertEquals("key0", result.contents?.first()?.key)
        assertEquals("Normal", result.contents?.first()?.type)
        assertEquals("IA", result.contents?.first()?.storageClass)
        assertEquals("restore", result.contents?.first()?.restoreInfo)
        assertEquals("2012-02-24T08:42:32.000Z", result.contents?.first()?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.contents?.first()?.eTag)
        assertEquals(100, result.contents?.first()?.size)
        assertEquals("0022012****", result.contents?.first()?.owner?.id)
        assertEquals("user_example", result.contents?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("prefix1/", result.commonPrefixes?.first()?.prefix)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = ListObjectsV2Result.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListBucketV2Result {
            continuationToken = "continuationToken"
            nextContinuationToken = "continuationToken1"
            maxKeys = 100
            keyCount = 50
            isTruncated = true
            name = "bucket-name"
            encodingType = "url"
            prefix = "prefix/"
            delimiter = "/"
            startAfter = "startAfter"
            contents = listOf(
                ObjectSummary {
                    key = "key0"
                    type = "Normal"
                    storageClass = "IA"
                    restoreInfo = "restore"
                    lastModified = "2012-02-24T08:42:32.000Z"
                    eTag = "5B3C1A2E053D763E1B002CC607C5A0FE1****"
                    size = 100
                    owner = Owner {
                        id = "0022012****"
                        displayName = "user_example"
                    }
                    transitionTime = "2013-02-24T08:42:32.000Z"
                }
            )
            commonPrefixes = listOf(
                CommonPrefix {
                    prefix = "prefix1/"
                }
            )
        }

        val result = ListObjectsV2Result(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("continuationToken", result.continuationToken)
        assertEquals("continuationToken1", result.nextContinuationToken)
        assertEquals(100, result.maxKeys)
        assertEquals(50, result.keyCount)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals("startAfter", result.startAfter)
        assertEquals(1, result.contents?.size)
        assertEquals("key0", result.contents?.first()?.key)
        assertEquals("Normal", result.contents?.first()?.type)
        assertEquals("IA", result.contents?.first()?.storageClass)
        assertEquals("restore", result.contents?.first()?.restoreInfo)
        assertEquals("2012-02-24T08:42:32.000Z", result.contents?.first()?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.contents?.first()?.eTag)
        assertEquals(100, result.contents?.first()?.size)
        assertEquals("0022012****", result.contents?.first()?.owner?.id)
        assertEquals("user_example", result.contents?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("prefix1/", result.commonPrefixes?.first()?.prefix)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
