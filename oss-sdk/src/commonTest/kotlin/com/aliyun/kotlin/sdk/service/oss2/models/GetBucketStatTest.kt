package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetBucketStatTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = GetBucketStatRequest {}
        assertNull(request.bucket)

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
        val request = GetBucketStatRequest {
            bucket = "my-bucket"
        }

        assertEquals("my-bucket", request.bucket)

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
    fun buildRequestFromBuilder() {
        val builder = GetBucketStatRequest.Builder()
        builder.bucket = "my-bucket"

        val request = GetBucketStatRequest(builder)
        assertEquals("my-bucket", request.bucket)

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
    fun buildResultWithEmptyValues() {
        val result = GetBucketStatResult {}
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
        val result = GetBucketStatResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = BucketStat {
                deleteMarkerCount = 1
                infrequentAccessObjectCount = 2
                archiveObjectCount = 3
                deepColdArchiveStorage = 4
                deepColdArchiveRealStorage = 5
                deepColdArchiveObjectCount = 6
                multipartPartCount = 7
                archiveStorage = 8
                archiveRealStorage = 9
                storage = 10
                coldArchiveStorage = 11
                coldArchiveRealStorage = 12
                coldArchiveObjectCount = 13
                objectCount = 14
                standardObjectCount = 15
                lastModifiedTime = 16
                infrequentAccessStorage = 17
                infrequentAccessRealStorage = 18
                standardStorage = 19
                multipartUploadCount = 20
                liveChannelCount = 21
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.bucketStat?.deleteMarkerCount)
        assertEquals(2, result.bucketStat?.infrequentAccessObjectCount)
        assertEquals(3, result.bucketStat?.archiveObjectCount)
        assertEquals(4, result.bucketStat?.deepColdArchiveStorage)
        assertEquals(5, result.bucketStat?.deepColdArchiveRealStorage)
        assertEquals(6, result.bucketStat?.deepColdArchiveObjectCount)
        assertEquals(7, result.bucketStat?.multipartPartCount)
        assertEquals(8, result.bucketStat?.archiveStorage)
        assertEquals(9, result.bucketStat?.archiveRealStorage)
        assertEquals(10, result.bucketStat?.storage)
        assertEquals(11, result.bucketStat?.coldArchiveStorage)
        assertEquals(12, result.bucketStat?.coldArchiveRealStorage)
        assertEquals(13, result.bucketStat?.coldArchiveObjectCount)
        assertEquals(14, result.bucketStat?.objectCount)
        assertEquals(15, result.bucketStat?.standardObjectCount)
        assertEquals(16, result.bucketStat?.lastModifiedTime)
        assertEquals(17, result.bucketStat?.infrequentAccessStorage)
        assertEquals(18, result.bucketStat?.infrequentAccessRealStorage)
        assertEquals(19, result.bucketStat?.standardStorage)
        assertEquals(20, result.bucketStat?.multipartUploadCount)
        assertEquals(21, result.bucketStat?.liveChannelCount)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = GetBucketStatResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = BucketStat {
            deleteMarkerCount = 1
            infrequentAccessObjectCount = 2
            archiveObjectCount = 3
            deepColdArchiveStorage = 4
            deepColdArchiveRealStorage = 5
            deepColdArchiveObjectCount = 6
            multipartPartCount = 7
            archiveStorage = 8
            archiveRealStorage = 9
            storage = 10
            coldArchiveStorage = 11
            coldArchiveRealStorage = 12
            coldArchiveObjectCount = 13
            objectCount = 14
            standardObjectCount = 15
            lastModifiedTime = 16
            infrequentAccessStorage = 17
            infrequentAccessRealStorage = 18
            standardStorage = 19
            multipartUploadCount = 20
            liveChannelCount = 21
        }

        val result = GetBucketStatResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.bucketStat?.deleteMarkerCount)
        assertEquals(2, result.bucketStat?.infrequentAccessObjectCount)
        assertEquals(3, result.bucketStat?.archiveObjectCount)
        assertEquals(4, result.bucketStat?.deepColdArchiveStorage)
        assertEquals(5, result.bucketStat?.deepColdArchiveRealStorage)
        assertEquals(6, result.bucketStat?.deepColdArchiveObjectCount)
        assertEquals(7, result.bucketStat?.multipartPartCount)
        assertEquals(8, result.bucketStat?.archiveStorage)
        assertEquals(9, result.bucketStat?.archiveRealStorage)
        assertEquals(10, result.bucketStat?.storage)
        assertEquals(11, result.bucketStat?.coldArchiveStorage)
        assertEquals(12, result.bucketStat?.coldArchiveRealStorage)
        assertEquals(13, result.bucketStat?.coldArchiveObjectCount)
        assertEquals(14, result.bucketStat?.objectCount)
        assertEquals(15, result.bucketStat?.standardObjectCount)
        assertEquals(16, result.bucketStat?.lastModifiedTime)
        assertEquals(17, result.bucketStat?.infrequentAccessStorage)
        assertEquals(18, result.bucketStat?.infrequentAccessRealStorage)
        assertEquals(19, result.bucketStat?.standardStorage)
        assertEquals(20, result.bucketStat?.multipartUploadCount)
        assertEquals(21, result.bucketStat?.liveChannelCount)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
