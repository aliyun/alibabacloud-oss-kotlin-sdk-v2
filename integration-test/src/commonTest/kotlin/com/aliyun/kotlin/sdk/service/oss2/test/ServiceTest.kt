package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.GetObjectRequest
import com.aliyun.kotlin.sdk.service.oss2.models.ListBucketsRequest
import com.aliyun.kotlin.sdk.service.oss2.paginator.PaginatorOptions
import com.aliyun.kotlin.sdk.service.oss2.paginator.listBucketsPaginator
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ServiceTest: TestBase() {

    @Test
    fun testListBuckets() = runTest {
        val result = defaultClient.listBuckets(ListBucketsRequest{})
        assertEquals(200, result.statusCode)
        assertEquals(null, result.maxKeys)
        assertEquals(null, result.marker)
        assertEquals(true, result.buckets?.isNotEmpty())
    }

    @Test
    fun testListBucketsWithMaxKeys() = runTest {
        val result = defaultClient.listBuckets(
            ListBucketsRequest{
                maxKeys = 1
            })
        assertEquals(200, result.statusCode)
        assertEquals(1, result.maxKeys)
        assertEquals(true, result.isTruncated)
    }

    @Test
    fun testListBucketsWithPrefix() = runTest {
        val prefixStr = genBucketName()

        val result = defaultClient.listBuckets(
            ListBucketsRequest{
                prefix = prefixStr
            })
        assertEquals(200, result.statusCode)
        assertEquals(null, result.isTruncated)
        assertEquals(true, result.buckets?.isEmpty())
        assertEquals(null, result.prefix)
    }

    @Test
    fun testListBucketsWithPaginator() = runTest {
        val prefixStr = genBucketName()
        var enter: Boolean = false
        defaultClient.listBucketsPaginator(
            ListBucketsRequest {
                prefix = prefixStr
            },
            PaginatorOptions {
                limit = 1
            }
        ).collect { it ->
            enter = true
            assertEquals(true, it.buckets?.isEmpty())
        }
        assertTrue(enter)
    }

    @Test
    fun testListBucketsWithException() = runTest {
        try {
            val result = invalidClient.listBuckets(
                ListBucketsRequest{}
            )
            assertFails{"should not here"}
        } catch (e: Exception) {
            val se = ServiceException.asCause(e)
            assertNotNull(se)
            assertEquals(403, se.statusCode)
            assertEquals("InvalidAccessKeyId", se.errorCode)
        }
    }
}