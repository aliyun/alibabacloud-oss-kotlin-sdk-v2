package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.ListAllMyBucketsResultXml
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ListBucketsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = ListBucketsRequest {}
        assertNull(request.resourceGroupId)
        assertNull(request.prefix)
        assertNull(request.marker)
        assertNull(request.maxKeys)
        assertNull(request.tagKey)
        assertNull(request.tagValue)
        assertNull(request.tagging)

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
        val request = ListBucketsRequest {
            resourceGroupId = "rg-aek27tc********"
            prefix = "my"
            marker = "mybucket10"
            maxKeys = 10
            tagKey = "key"
            tagValue = "value"
            tagging = "k=v"
        }

        assertEquals("rg-aek27tc********", request.resourceGroupId)
        assertEquals("my", request.prefix)
        assertEquals("mybucket10", request.marker)
        assertEquals(10, request.maxKeys)
        assertEquals("key", request.tagKey)
        assertEquals("value", request.tagValue)
        assertEquals("k=v", request.tagging)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-resource-group-id")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("marker")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("tag-key")
            request.parameters.containsKey("tag-value")
            request.parameters.containsKey("tagging")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = ListBucketsRequest.Builder()
        builder.resourceGroupId = "rg-aek27tc********"
        builder.prefix = "my"
        builder.marker = "mybucket10"
        builder.maxKeys = 10
        builder.tagKey = "key"
        builder.tagValue = "value"
        builder.tagging = "k=v"

        val request = ListBucketsRequest(builder)
        assertEquals("rg-aek27tc********", request.resourceGroupId)
        assertEquals("my", request.prefix)
        assertEquals("mybucket10", request.marker)
        assertEquals(10, request.maxKeys)
        assertEquals("key", request.tagKey)
        assertEquals("value", request.tagValue)
        assertEquals("k=v", request.tagging)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-resource-group-id")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("uploadId")
            request.parameters.containsKey("prefix")
            request.parameters.containsKey("marker")
            request.parameters.containsKey("max-keys")
            request.parameters.containsKey("tag-key")
            request.parameters.containsKey("tag-value")
            request.parameters.containsKey("tagging")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = ListBucketsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = ListBucketsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = ListAllMyBucketsResultXml {
                prefix = "prefix/"
                marker = "key10"
                maxKeys = 100
                isTruncated = true
                nextMarker = "key110"
                buckets = listOf(
                    BucketSummary {
                        extranetEndpoint = "oss-cn-shanghai.aliyuncs.com"
                        resourceGroupId = "resourceGroupId"
                        comment = "comment"
                        location = "oss-cn-shanghai"
                        name = "app-base-oss"
                        storageClass = "IA"
                        region = "cn-shanghai"
                        creationDate = "2014-02-17T18:12:43.000Z"
                        intranetEndpoint = "oss-cn-shanghai-internal.aliyuncs.com"
                    }
                )
                owner = Owner {
                    id = "512**"
                    displayName = "ut_test_put_bucket"
                }
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("prefix/", result.prefix)
        assertEquals("key10", result.marker)
        assertEquals(100, result.maxKeys)
        assertEquals(true, result.isTruncated)
        assertEquals("key110", result.nextMarker)
        assertEquals(1, result.buckets?.size)
        assertEquals("oss-cn-shanghai.aliyuncs.com", result.buckets?.first()?.extranetEndpoint)
        assertEquals("resourceGroupId", result.buckets?.first()?.resourceGroupId)
        assertEquals("comment", result.buckets?.first()?.comment)
        assertEquals("oss-cn-shanghai", result.buckets?.first()?.location)
        assertEquals("app-base-oss", result.buckets?.first()?.name)
        assertEquals("IA", result.buckets?.first()?.storageClass)
        assertEquals("cn-shanghai", result.buckets?.first()?.region)
        assertEquals("2014-02-17T18:12:43.000Z", result.buckets?.first()?.creationDate)
        assertEquals("oss-cn-shanghai-internal.aliyuncs.com", result.buckets?.first()?.intranetEndpoint)
        assertEquals("512**", result.owner?.id)
        assertEquals("ut_test_put_bucket", result.owner?.displayName)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = ListBucketsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = ListAllMyBucketsResultXml {
            prefix = "prefix/"
            marker = "key10"
            maxKeys = 100
            isTruncated = true
            nextMarker = "key110"
            buckets = listOf(
                BucketSummary {
                    extranetEndpoint = "oss-cn-shanghai.aliyuncs.com"
                    resourceGroupId = "resourceGroupId"
                    comment = "comment"
                    location = "oss-cn-shanghai"
                    name = "app-base-oss"
                    storageClass = "IA"
                    region = "cn-shanghai"
                    creationDate = "2014-02-17T18:12:43.000Z"
                    intranetEndpoint = "oss-cn-shanghai-internal.aliyuncs.com"
                }
            )
            owner = Owner {
                id = "512**"
                displayName = "ut_test_put_bucket"
            }
        }

        val result = ListBucketsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("prefix/", result.prefix)
        assertEquals("key10", result.marker)
        assertEquals(100, result.maxKeys)
        assertEquals(true, result.isTruncated)
        assertEquals("key110", result.nextMarker)
        assertEquals(1, result.buckets?.size)
        assertEquals("oss-cn-shanghai.aliyuncs.com", result.buckets?.first()?.extranetEndpoint)
        assertEquals("resourceGroupId", result.buckets?.first()?.resourceGroupId)
        assertEquals("comment", result.buckets?.first()?.comment)
        assertEquals("oss-cn-shanghai", result.buckets?.first()?.location)
        assertEquals("app-base-oss", result.buckets?.first()?.name)
        assertEquals("IA", result.buckets?.first()?.storageClass)
        assertEquals("cn-shanghai", result.buckets?.first()?.region)
        assertEquals("2014-02-17T18:12:43.000Z", result.buckets?.first()?.creationDate)
        assertEquals("oss-cn-shanghai-internal.aliyuncs.com", result.buckets?.first()?.intranetEndpoint)
        assertEquals("512**", result.owner?.id)
        assertEquals("ut_test_put_bucket", result.owner?.displayName)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
