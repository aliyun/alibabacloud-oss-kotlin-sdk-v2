package com.aliyun.kotlin.sdk.service.oss2.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DescribeRegionsTest {

    @Test
    fun buildRequestWithEmptyValues() {
        val request = DescribeRegionsRequest {}
        assertNull(request.regions)

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
        val request = DescribeRegionsRequest {
            regions = "cn-hangzhou"
        }

        assertEquals("cn-hangzhou", request.regions)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("regions")
        }
    }

    @Test
    fun buildRequestFromBuilder() {
        val builder = DescribeRegionsRequest.Builder()
        builder.regions = "cn-hangzhou"

        val request = DescribeRegionsRequest(builder)
        assertEquals("cn-hangzhou", request.regions)

        assertNotNull(request.headers)
        assertTrue {
            request.headers.isEmpty()
        }
        assertNotNull(request.parameters)
        assertTrue {
            request.parameters.containsKey("regions")
        }
    }

    @Test
    fun buildResultWithEmptyValues() {
        val result = DescribeRegionsResult {}
        assertEquals(0, result.statusCode)
        assertEquals("", result.status)
        assertEquals("", result.requestId)
        assertNull(result.regionInfoList)

        assertNotNull(result.headers)
        assertTrue {
            result.headers.isEmpty()
        }
    }

    @Test
    fun buildResultWithFullValuesFromDsl() {
        val result = DescribeRegionsResult {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf("x-oss-request-id" to "id-123")
            innerBody = RegionInfoList {
                regionInfos = listOf(
                    RegionInfo {
                        region = "oss-ch-hangzhou"
                        internetEndpoint = "oss-ch-hangzhou.aliyuncs.com"
                        internalEndpoint = "0ss-ch-hangzhou-internal.aliyuncs.com"
                        accelerateEndpoint = "oss-accelerate.aliyuncs.com"
                    }
                )
            }
        }
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.regionInfoList?.regionInfos?.size)
        assertEquals("oss-ch-hangzhou", result.regionInfoList?.regionInfos?.first()?.region)
        assertEquals("oss-ch-hangzhou.aliyuncs.com", result.regionInfoList?.regionInfos?.first()?.internetEndpoint)
        assertEquals(
            "0ss-ch-hangzhou-internal.aliyuncs.com",
            result.regionInfoList?.regionInfos?.first()?.internalEndpoint
        )
        assertEquals("oss-accelerate.aliyuncs.com", result.regionInfoList?.regionInfos?.first()?.accelerateEndpoint)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }

    @Test
    fun buildResultFromBuilder() {
        val builder = DescribeRegionsResult.Builder()
        builder.status = "OK"
        builder.statusCode = 200
        builder.headers = mutableMapOf("x-oss-request-id" to "id-123")
        builder.innerBody = RegionInfoList {
            regionInfos = listOf(
                RegionInfo {
                    region = "oss-ch-hangzhou"
                    internetEndpoint = "oss-ch-hangzhou.aliyuncs.com"
                    internalEndpoint = "0ss-ch-hangzhou-internal.aliyuncs.com"
                    accelerateEndpoint = "oss-accelerate.aliyuncs.com"
                }
            )
        }

        val result = DescribeRegionsResult(builder)
        assertEquals(200, result.statusCode)
        assertEquals("OK", result.status)
        assertEquals("id-123", result.requestId)
        assertEquals(1, result.regionInfoList?.regionInfos?.size)
        assertEquals("oss-ch-hangzhou", result.regionInfoList?.regionInfos?.first()?.region)
        assertEquals("oss-ch-hangzhou.aliyuncs.com", result.regionInfoList?.regionInfos?.first()?.internetEndpoint)
        assertEquals(
            "0ss-ch-hangzhou-internal.aliyuncs.com",
            result.regionInfoList?.regionInfos?.first()?.internalEndpoint
        )
        assertEquals("oss-accelerate.aliyuncs.com", result.regionInfoList?.regionInfos?.first()?.accelerateEndpoint)

        assertNotNull(result.headers)
        assertEquals(1, result.headers.size)
        assertEquals("id-123", result.headers["x-oss-request-id"])
    }
}
