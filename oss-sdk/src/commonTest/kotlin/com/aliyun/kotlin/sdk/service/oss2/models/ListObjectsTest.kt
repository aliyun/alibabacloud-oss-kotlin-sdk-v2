package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketResult
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListMultipartUploads.Companion.invoke
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListObjectsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListObjectsRequest {}
        assertNull(request.bucket)
        assertNull(request.delimiter)
        assertNull(request.maxKeys)
        assertNull(request.marker)
        assertNull(request.prefix)
        assertNull(request.encodingType)

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
        val request = ListObjectsRequest {
            bucket = "my-bucket"
            delimiter = "/"
            maxKeys = 100
            marker = "key100"
            prefix = "prefix/"
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("key100", request.marker)
        assertEquals("prefix/", request.prefix)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListObjectsRequest.Builder()
        builder.bucket = "my-bucket"
        builder.delimiter = "/"
        builder.maxKeys = 100
        builder.marker = "key100"
        builder.prefix = "prefix/"
        builder.encodingType = "url"

        val request = ListObjectsRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("key100", request.marker)
        assertEquals("prefix/", request.prefix)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("delimiter")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListObjectsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.marker)
        assertNull(result.nextMarker)
        assertNull(result.maxKeys)
        assertNull(result.keyCount)
        assertNull(result.isTruncated)
        assertNull(result.encodingType)
        assertNull(result.prefix)
        assertNull(result.delimiter)
        assertNull(result.contents)
        assertNull(result.commonPrefixes)
        assertNull(result.name)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListObjectsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListBucketResult {
                marker = "key"
                nextMarker = "key1"
                maxKeys = 100
                keyCount = 50
                isTruncated = true
                name = "bucket-name"
                encodingType = "url"
                prefix = "prefix/"
                delimiter = "/"
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
        assertEquals("key", result.marker)
        assertEquals("key1", result.nextMarker)
        assertEquals(100, result.maxKeys)
        assertEquals(50, result.keyCount)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
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
        val builder = ListObjectsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListBucketResult {
            marker = "key"
            nextMarker = "key1"
            maxKeys = 100
            keyCount = 50
            isTruncated = true
            name = "bucket-name"
            encodingType = "url"
            prefix = "prefix/"
            delimiter = "/"
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

        val result = ListObjectsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.marker)
        assertEquals("key1", result.nextMarker)
        assertEquals(100, result.maxKeys)
        assertEquals(50, result.keyCount)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
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
