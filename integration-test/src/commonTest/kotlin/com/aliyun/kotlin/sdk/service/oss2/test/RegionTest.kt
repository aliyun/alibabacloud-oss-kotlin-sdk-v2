package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.models.DescribeRegionsRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RegionTest: TestBase() {

    @Test
    fun testDescribeRegions() = runTest {
        val result = defaultClient.describeRegions(DescribeRegionsRequest{})
        assertEquals(result.statusCode, 200)
        assertNotNull(result.regionInfoList)
    }

    @Test
    fun testDescribeRegionsWithRegions() =runTest {
        val result = defaultClient.describeRegions(DescribeRegionsRequest{
            regions = "oss-cn-hangzhou"
        })
        assertEquals(result.statusCode, 200)
        assertEquals(result.regionInfoList?.regionInfos?.size, 1)
        assertEquals(result.regionInfoList?.regionInfos?.first()?.region, "oss-cn-hangzhou")
        assertEquals(result.regionInfoList?.regionInfos?.first()?.internalEndpoint, "oss-cn-hangzhou-internal.aliyuncs.com")
        assertEquals(result.regionInfoList?.regionInfos?.first()?.internetEndpoint, "oss-cn-hangzhou.aliyuncs.com")
        assertEquals(result.regionInfoList?.regionInfos?.first()?.accelerateEndpoint, "oss-accelerate.aliyuncs.com")
    }

    @Test
    fun testDescribeRegionsWithException() = runTest {
        val exception = assertFails { invalidClient.describeRegions(DescribeRegionsRequest{}) }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}