package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeServiceTest {

    @Test
    fun testFromXmlListAllMyBucketsResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListAllMyBucketsResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListAllMyBucketsResult("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListAllMyBucketsResult>
            <Prefix>my</Prefix>
            <Marker>mybucket</Marker>
            <MaxKeys>10</MaxKeys>
            <IsTruncated>true</IsTruncated>
            <NextMarker>mybucket10</NextMarker>
            <Owner>
            <ID>512**</ID>
            <DisplayName>51264</DisplayName>
            </Owner>
            <Buckets>
            <Bucket>
            <CreationDate>2014-02-17T18:12:43.000Z</CreationDate>
            <ExtranetEndpoint>oss-cn-shanghai.aliyuncs.com</ExtranetEndpoint>
            <IntranetEndpoint>oss-cn-shanghai-internal.aliyuncs.com</IntranetEndpoint>
            <Location>oss-cn-shanghai</Location>
            <Name>app-base-oss</Name>
            <Region>cn-shanghai</Region>
            <StorageClass>Standard</StorageClass>
            </Bucket>
            <Bucket>
            <CreationDate>2014-02-25T11:21:04.000Z</CreationDate>
            <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>
            <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>
            <Location>oss-cn-hangzhou</Location>
            <Name>mybucket</Name>
            <Region>cn-hangzhou</Region>
            <StorageClass>IA</StorageClass>
            </Bucket>
            </Buckets>
            </ListAllMyBucketsResult>
        """.trimIndent()
        val result = fromXmlListAllMyBucketsResult(xml.toByteArray())
        assertEquals("my", result.prefix)
        assertEquals("mybucket", result.marker)
        assertEquals(10, result.maxKeys)
        assertEquals(true, result.isTruncated)
        assertEquals("mybucket10", result.nextMarker)
        assertEquals("512**", result.owner?.id)
        assertEquals("51264", result.owner?.displayName)
        assertEquals("app-base-oss", result.buckets.first().name)
        assertEquals("2014-02-17T18:12:43.000Z", result.buckets.first().creationDate)
        assertEquals("oss-cn-shanghai.aliyuncs.com", result.buckets.first().extranetEndpoint)
        assertEquals("oss-cn-shanghai-internal.aliyuncs.com", result.buckets.first().intranetEndpoint)
        assertEquals("oss-cn-shanghai", result.buckets.first().location)
        assertEquals("cn-shanghai", result.buckets.first().region)
        assertEquals("Standard", result.buckets.first().storageClass)
        assertEquals("mybucket", result.buckets.last().name)
        assertEquals("2014-02-25T11:21:04.000Z", result.buckets.last().creationDate)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result.buckets.last().extranetEndpoint)
        assertEquals("oss-cn-hangzhou-internal.aliyuncs.com", result.buckets.last().intranetEndpoint)
        assertEquals("oss-cn-hangzhou", result.buckets.last().location)
        assertEquals("cn-hangzhou", result.buckets.last().region)
        assertEquals("IA", result.buckets.last().storageClass)
    }
}
