package com.aliyun.kotlin.sdk.service.oss2.models

import com.aliyun.kotlin.sdk.service.oss2.models.internal.CopyObjectResultXml
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CopyObjectTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = CopyObjectRequest {}
        assertNull(request.bucket)
        assertNull(request.key)
        assertNull(request.sourceBucket)
        assertNull(request.sourceKey)
        assertNull(request.objectAcl)
        assertNull(request.storageClass)
        assertNull(request.sourceVersionId)
        assertNull(request.copySourceIfMatch)
        assertNull(request.copySourceIfNoneMatch)
        assertNull(request.copySourceIfUnmodifiedSince)
        assertNull(request.copySourceIfModifiedSince)
        assertNull(request.metadataDirective)
        assertNull(request.serverSideEncryption)
        assertNull(request.serverSideDataEncryption)
        assertNull(request.serverSideEncryptionKeyId)
        assertNull(request.tagging)
        assertEquals(true, request.metadata?.isEmpty())

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
        val request = CopyObjectRequest {
            bucket = "my-bucket"
            key = "key"
            sourceBucket = "sourceBucket"
            sourceKey = "sourceKey"
            objectAcl = "private"
            storageClass = "IA"
            sourceVersionId = "sourceVersionId"
            forbidOverwrite = true
            copySourceIfMatch = "5B3C1A2E053D763E1B002CC607C5****"
            copySourceIfNoneMatch = "6B3C1A2E053D763E1B002CC607C5****"
            copySourceIfUnmodifiedSince = "Mon, 11 May 2020 08:16:23 GMT"
            copySourceIfModifiedSince = "Mon, 11 May 2021 08:16:23 GMT"
            metadataDirective = "COPY"
            taggingDirective = "COPY"
            serverSideEncryption = "AES256"
            serverSideDataEncryption = "serverSideDataEncryption"
            serverSideEncryptionKeyId = "9468da86-3509-4f8d-a61e-6eab1eac****"
            tagging = "A=B"
            metadata = mapOf(
                "key" to "value"
            )
        }

        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("sourceBucket", request.sourceBucket)
        assertEquals("sourceKey", request.sourceKey)
        assertEquals("private", request.objectAcl)
        assertEquals("IA", request.storageClass)
        assertEquals("sourceVersionId", request.sourceVersionId)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("5B3C1A2E053D763E1B002CC607C5****", request.copySourceIfMatch)
        assertEquals("6B3C1A2E053D763E1B002CC607C5****", request.copySourceIfNoneMatch)
        assertEquals("Mon, 11 May 2020 08:16:23 GMT", request.copySourceIfUnmodifiedSince)
        assertEquals("Mon, 11 May 2021 08:16:23 GMT", request.copySourceIfModifiedSince)
        assertEquals("COPY", request.metadataDirective)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("serverSideDataEncryption", request.serverSideDataEncryption)
        assertEquals("9468da86-3509-4f8d-a61e-6eab1eac****", request.serverSideEncryptionKeyId)
        assertEquals("A=B", request.tagging)
        assertEquals(true, request.metadata?.containsKey("key"))

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-copy-source-if-match")
            request.headers.containsKey("x-oss-copy-source-if-none-match")
            request.headers.containsKey("x-oss-copy-source-if-unmodified-since")
            request.headers.containsKey("x-oss-copy-source-if-modified-since")
            request.headers.containsKey("x-oss-metadata-directive")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-tagging-directive")
            request.headers.containsKey("x-oss-meta-key")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = CopyObjectRequest.Builder()
        builder.bucket = "my-bucket"
        builder.key = "key"
        builder.sourceBucket = "sourceBucket"
        builder.sourceKey = "sourceKey"
        builder.objectAcl = "private"
        builder.storageClass = "IA"
        builder.sourceVersionId = "sourceVersionId"
        builder.forbidOverwrite = true
        builder.copySourceIfMatch = "5B3C1A2E053D763E1B002CC607C5****"
        builder.copySourceIfNoneMatch = "6B3C1A2E053D763E1B002CC607C5****"
        builder.copySourceIfUnmodifiedSince = "Mon, 11 May 2020 08:16:23 GMT"
        builder.copySourceIfModifiedSince = "Mon, 11 May 2021 08:16:23 GMT"
        builder.metadataDirective = "COPY"
        builder.taggingDirective = "COPY"
        builder.serverSideEncryption = "AES256"
        builder.serverSideDataEncryption = "serverSideDataEncryption"
        builder.serverSideEncryptionKeyId = "9468da86-3509-4f8d-a61e-6eab1eac****"
        builder.tagging = "A=B"
        builder.metadata = mapOf(
            "key" to "value"
        )

        val request = CopyObjectRequest(builder)
        assertEquals("my-bucket", request.bucket)
        assertEquals("key", request.key)
        assertEquals("sourceBucket", request.sourceBucket)
        assertEquals("sourceKey", request.sourceKey)
        assertEquals("private", request.objectAcl)
        assertEquals("IA", request.storageClass)
        assertEquals("sourceVersionId", request.sourceVersionId)
        assertEquals(true, request.forbidOverwrite)
        assertEquals("5B3C1A2E053D763E1B002CC607C5****", request.copySourceIfMatch)
        assertEquals("6B3C1A2E053D763E1B002CC607C5****", request.copySourceIfNoneMatch)
        assertEquals("Mon, 11 May 2020 08:16:23 GMT", request.copySourceIfUnmodifiedSince)
        assertEquals("Mon, 11 May 2021 08:16:23 GMT", request.copySourceIfModifiedSince)
        assertEquals("COPY", request.metadataDirective)
        assertEquals("AES256", request.serverSideEncryption)
        assertEquals("serverSideDataEncryption", request.serverSideDataEncryption)
        assertEquals("9468da86-3509-4f8d-a61e-6eab1eac****", request.serverSideEncryptionKeyId)
        assertEquals("A=B", request.tagging)
        assertEquals(true, request.metadata?.containsKey("key"))

        assertNotNull(request.headers)
        assertTrue {
            request.headers.containsKey("x-oss-forbid-overwrite")
            request.headers.containsKey("x-oss-copy-source-if-match")
            request.headers.containsKey("x-oss-copy-source-if-none-match")
            request.headers.containsKey("x-oss-copy-source-if-unmodified-since")
            request.headers.containsKey("x-oss-copy-source-if-modified-since")
            request.headers.containsKey("x-oss-metadata-directive")
            request.headers.containsKey("x-oss-server-side-encryption")
            request.headers.containsKey("x-oss-server-side-encryption-key-id")
            request.headers.containsKey("x-oss-object-acl")
            request.headers.containsKey("x-oss-storage-class")
            request.headers.containsKey("x-oss-tagging")
            request.headers.containsKey("x-oss-tagging-directive")
            request.headers.containsKey("x-oss-meta-key")
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("versionId")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = CopyObjectResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.versionId)
        assertNull(result.eTag)
        assertNull(result.copySourceVersionId)
        assertNull(result.lastModified)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = CopyObjectResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf(
                "x-oss-request-id" to "id-123",
                "x-oss-version-id" to "CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****",
                "x-oss-copy-source-version-id" to "CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****"
            )
            innerBody = CopyObjectResultXml {
                lastModified = "2019-04-09T03:45:32.000Z"
                eTag = "\"C4CA4238A0B923820DCC509A6F75****\""
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("\"C4CA4238A0B923820DCC509A6F75****\"", result.eTag)
        assertEquals("2019-04-09T03:45:32.000Z", result.lastModified)
        assertEquals("CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****", result.copySourceVersionId)
        assertEquals("CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****", result.versionId)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****",
            result.headers["x-oss-copy-source-version-id"]
        )
        assertEquals(
            "CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****",
            result.headers["x-oss-version-id"]
        )
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = CopyObjectResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf(
            "x-oss-request-id" to "id-123",
            "x-oss-version-id" to "CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****",
            "x-oss-copy-source-version-id" to "CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****"
        )
        builder.innerBody = CopyObjectResultXml {
            eTag = "\"C4CA4238A0B923820DCC509A6F75****\""
            lastModified = "2019-04-09T03:45:32.000Z"
        }

        val result = CopyObjectResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals("\"C4CA4238A0B923820DCC509A6F75****\"", result.eTag)
        assertEquals("2019-04-09T03:45:32.000Z", result.lastModified)
        assertEquals("CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****", result.copySourceVersionId)
        assertEquals("CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****", result.versionId)

        assertNotNull(result.headers)
        assertEquals(3, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
        assertEquals(
            "CAEQNRiBgICv8uaA0BYiIDliZDc3MTc1NjE5MjRkMDI4ZGU4MTZkYjY1ZDgy****",
            result.headers["x-oss-copy-source-version-id"]
        )
        assertEquals(
            "CAEQNxiBgMDP8uaA0BYiIDIyNGNhZDQ1M2M3NzRkZThiNzE0N2I3ZDkxOWY4****",
            result.headers["x-oss-version-id"]
        )
    }
}
