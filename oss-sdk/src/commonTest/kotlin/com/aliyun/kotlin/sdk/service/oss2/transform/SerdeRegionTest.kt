package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeRegionTest {

    @Test
    fun testFromXmlRegionInfoList() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlRegionInfoList(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlRegionInfoList("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <RegionInfoList>
            <RegionInfo>
            <Region>oss-cn-hangzhou</Region>
            <InternetEndpoint>oss-cn-hangzhou.aliyuncs.com</InternetEndpoint>
            <InternalEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</InternalEndpoint>
            <AccelerateEndpoint>oss-accelerate.aliyuncs.com</AccelerateEndpoint>
            </RegionInfo>
            <RegionInfo>
            <Region>oss-cn-shanghai</Region>
            <InternetEndpoint>oss-cn-shanghai.aliyuncs.com</InternetEndpoint>
            <InternalEndpoint>oss-cn-shanghai-internal.aliyuncs.com</InternalEndpoint>
            <AccelerateEndpoint>oss-accelerate.aliyuncs.com</AccelerateEndpoint>
            </RegionInfo>
            </RegionInfoList>
        """.trimIndent()
        val result = fromXmlRegionInfoList(xml.toByteArray())
        assertEquals("oss-cn-hangzhou", result.regionInfos?.first()?.region)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result.regionInfos?.first()?.internetEndpoint)
        assertEquals("oss-cn-hangzhou-internal.aliyuncs.com", result.regionInfos?.first()?.internalEndpoint)
        assertEquals("oss-accelerate.aliyuncs.com", result.regionInfos?.first()?.accelerateEndpoint)
        assertEquals("oss-cn-shanghai", result.regionInfos?.last()?.region)
        assertEquals("oss-cn-shanghai.aliyuncs.com", result.regionInfos?.last()?.internetEndpoint)
        assertEquals("oss-cn-shanghai-internal.aliyuncs.com", result.regionInfos?.last()?.internalEndpoint)
        assertEquals("oss-accelerate.aliyuncs.com", result.regionInfos?.last()?.accelerateEndpoint)
    }
}
