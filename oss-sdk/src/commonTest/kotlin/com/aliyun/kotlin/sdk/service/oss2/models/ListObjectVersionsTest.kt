package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListBucketResult.Companion.invoke
import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListVersionsResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListObjectVersionsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListObjectVersionsRequest {}
        assertNull(request.bucket)
        assertNull(request.delimiter)
        assertNull(request.maxKeys)
        assertNull(request.keyMarker)
        assertNull(request.versionIdMarker)
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
        val request = ListObjectVersionsRequest {
            bucket = "my-bucket"
            delimiter = "/"
            maxKeys = 100
            keyMarker = "key100"
            versionIdMarker = "versionIdMarker1"
            prefix = "prefix/"
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("key100", request.keyMarker)
        assertEquals("versionIdMarker1", request.versionIdMarker)
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
            request.parameters.containsKey("key-marker")
            request.parameters.containsKey("version-id-marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListObjectVersionsRequest.Builder()
        builder.bucket = "my-bucket"
        builder.delimiter = "/"
        builder.maxKeys = 100
        builder.keyMarker = "key100"
        builder.versionIdMarker = "versionIdMarker1"
        builder.prefix = "prefix/"
        builder.encodingType = "url"

        val request = ListObjectVersionsRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("/", request.delimiter)
        assertEquals(100, request.maxKeys)
        assertEquals("key100", request.keyMarker)
        assertEquals("versionIdMarker1", request.versionIdMarker)
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
            request.parameters.containsKey("key-marker")
            request.parameters.containsKey("version-id-marker")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("encoding-type")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListObjectVersionsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.keyMarker)
        assertNull(result.nextKeyMarker)
        assertNull(result.versionIdMarker)
        assertNull(result.nextVersionIdMarker)
        assertNull(result.maxKeys)
        assertNull(result.isTruncated)
        assertNull(result.encodingType)
        assertNull(result.prefix)
        assertNull(result.delimiter)
        assertNull(result.versions)
        assertNull(result.commonPrefixes)
        assertNull(result.name)
        assertNull(result.deleteMarkers)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListObjectVersionsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListVersionsResult {
                keyMarker = "key"
                nextKeyMarker = "key1"
                maxKeys = 100
                versionIdMarker = "versionIdMarker1"
                nextVersionIdMarker = "versionIdMarker2"
                isTruncated = true
                name = "bucket-name"
                encodingType = "url"
                prefix = "prefix/"
                delimiter = "/"
                versions = listOf(
                    ObjectVersion {
                        key = "key0"
                        versionId = "versionId1"
                        storageClass = "IA"
                        restoreInfo = "restore"
                        lastModified = "2012-02-24T08:42:32.000Z"
                        isLatest = true
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
                deleteMarkers = listOf(
                    DeleteMarkerEntry {
                        key = "key0"
                        versionId = "versionId1"
                        lastModified = "2012-02-24T08:42:32.000Z"
                        owner = Owner {
                            id = "0022012****"
                            displayName = "user_example"
                        }
                        isLatest = true
                    }
                )
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.keyMarker)
        assertEquals("key1", result.nextKeyMarker)
        assertEquals(100, result.maxKeys)
        assertEquals("versionIdMarker1", result.versionIdMarker)
        assertEquals("versionIdMarker2", result.nextVersionIdMarker)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals(1, result.versions?.size)
        assertEquals("key0", result.versions?.first()?.key)
        assertEquals("versionId1", result.versions?.first()?.versionId)
        assertEquals(true, result.versions?.first()?.isLatest)
        assertEquals("IA", result.versions?.first()?.storageClass)
        assertEquals("restore", result.versions?.first()?.restoreInfo)
        assertEquals("2012-02-24T08:42:32.000Z", result.versions?.first()?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.versions?.first()?.eTag)
        assertEquals(100, result.versions?.first()?.size)
        assertEquals("0022012****", result.versions?.first()?.owner?.id)
        assertEquals("user_example", result.versions?.first()?.owner?.displayName)
        assertEquals(1, result.deleteMarkers?.size)
        assertEquals("key0", result.deleteMarkers?.first()?.key)
        assertEquals("versionId1", result.deleteMarkers?.first()?.versionId)
        assertEquals(true, result.deleteMarkers?.first()?.isLatest)
        assertEquals("2012-02-24T08:42:32.000Z", result.deleteMarkers?.first()?.lastModified)
        assertEquals("0022012****", result.deleteMarkers?.first()?.owner?.id)
        assertEquals("user_example", result.deleteMarkers?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("prefix1/", result.commonPrefixes?.first()?.prefix)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = ListObjectVersionsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListVersionsResult {
            keyMarker = "key"
            nextKeyMarker = "key1"
            maxKeys = 100
            versionIdMarker = "versionIdMarker1"
            nextVersionIdMarker = "versionIdMarker2"
            isTruncated = true
            name = "bucket-name"
            encodingType = "url"
            prefix = "prefix/"
            delimiter = "/"
            versions = listOf(
                ObjectVersion {
                    key = "key0"
                    versionId = "versionId1"
                    storageClass = "IA"
                    restoreInfo = "restore"
                    lastModified = "2012-02-24T08:42:32.000Z"
                    isLatest = true
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
            deleteMarkers = listOf(
                DeleteMarkerEntry {
                    key = "key0"
                    versionId = "versionId1"
                    lastModified = "2012-02-24T08:42:32.000Z"
                    owner = Owner {
                        id = "0022012****"
                        displayName = "user_example"
                    }
                    isLatest = true
                }
            )
        }

        val result = ListObjectVersionsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("key", result.keyMarker)
        assertEquals("key1", result.nextKeyMarker)
        assertEquals(100, result.maxKeys)
        assertEquals("versionIdMarker1", result.versionIdMarker)
        assertEquals("versionIdMarker2", result.nextVersionIdMarker)
        assertEquals(true, result.isTruncated)
        assertEquals("bucket-name", result.name)
        assertEquals("url", result.encodingType)
        assertEquals("prefix/", result.prefix)
        assertEquals("/", result.delimiter)
        assertEquals(1, result.versions?.size)
        assertEquals("key0", result.versions?.first()?.key)
        assertEquals("versionId1", result.versions?.first()?.versionId)
        assertEquals(true, result.versions?.first()?.isLatest)
        assertEquals("IA", result.versions?.first()?.storageClass)
        assertEquals("restore", result.versions?.first()?.restoreInfo)
        assertEquals("2012-02-24T08:42:32.000Z", result.versions?.first()?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.versions?.first()?.eTag)
        assertEquals(100, result.versions?.first()?.size)
        assertEquals("0022012****", result.versions?.first()?.owner?.id)
        assertEquals("user_example", result.versions?.first()?.owner?.displayName)
        assertEquals(1, result.deleteMarkers?.size)
        assertEquals("key0", result.deleteMarkers?.first()?.key)
        assertEquals("versionId1", result.deleteMarkers?.first()?.versionId)
        assertEquals(true, result.deleteMarkers?.first()?.isLatest)
        assertEquals("2012-02-24T08:42:32.000Z", result.deleteMarkers?.first()?.lastModified)
        assertEquals("0022012****", result.deleteMarkers?.first()?.owner?.id)
        assertEquals("user_example", result.deleteMarkers?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("prefix1/", result.commonPrefixes?.first()?.prefix)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
