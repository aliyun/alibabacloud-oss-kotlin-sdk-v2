package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.DeleteResultXml
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeleteMultipleObjectsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = DeleteMultipleObjectsRequest {}
        assertNull(request.bucket)
        assertNull(request.delete)
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
        val request = DeleteMultipleObjectsRequest {
            bucket = "my-bucket"
            delete = Delete()
            encodingType = "url"
        }

        assertEquals("my-bucket", request.bucket)
        assertNotNull(request.delete)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("Encoding-type")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = DeleteMultipleObjectsRequest.Builder()
        builder.bucket = "my-bucket"
        builder.delete = Delete()
        builder.encodingType = "url"

        val request = DeleteMultipleObjectsRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertNotNull(request.delete)
        assertEquals("url", request.encodingType)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("Encoding-type")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.isEmpty()
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = DeleteMultipleObjectsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.deletedObjects)
        assertNull(result.encodingType)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = DeleteMultipleObjectsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = DeleteResultXml {
                encodingType = "url"
                deletedObjects = listOf(
                    DeletedInfo {
                        key = "key"
                        versionId = "versionId"
                        deleteMarker = true
                        deleteMarkerVersionId = "deleteMarkerVersionId"
                    }
                )
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("url", result.encodingType)
        assertEquals(1, result.deletedObjects?.size)
        assertEquals("key", result.deletedObjects?.first()?.key)
        assertEquals("versionId", result.deletedObjects?.first()?.versionId)
        assertEquals(true, result.deletedObjects?.first()?.deleteMarker)
        assertEquals("deleteMarkerVersionId", result.deletedObjects?.first()?.deleteMarkerVersionId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = DeleteMultipleObjectsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = DeleteResultXml {
            encodingType = "url"
            deletedObjects = listOf(
                DeletedInfo {
                    key = "key"
                    versionId = "versionId"
                    deleteMarker = true
                    deleteMarkerVersionId = "deleteMarkerVersionId"
                }
            )
        }

        val result = DeleteMultipleObjectsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("url", result.encodingType)
        assertEquals(1, result.deletedObjects?.size)
        assertEquals("key", result.deletedObjects?.first()?.key)
        assertEquals("versionId", result.deletedObjects?.first()?.versionId)
        assertEquals(true, result.deletedObjects?.first()?.deleteMarker)
        assertEquals("deleteMarkerVersionId", result.deletedObjects?.first()?.deleteMarkerVersionId)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
