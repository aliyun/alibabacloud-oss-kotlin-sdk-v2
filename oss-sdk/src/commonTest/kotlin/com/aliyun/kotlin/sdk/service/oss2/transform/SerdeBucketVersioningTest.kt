package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.models.VersioningConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeBucketVersioningTest {

    @Test
    fun testToXmlVersioningConfiguration() = runTest {
        var version = VersioningConfiguration.Builder().build()
        assertEquals(
            "<VersioningConfiguration></VersioningConfiguration>",
            String(toXmlVersioningConfiguration(version).toByteArray())
        )

        version = VersioningConfiguration.Builder().apply {
            status = "Enabled"
        }.build()
        assertEquals(
            "<VersioningConfiguration><Status>Enabled</Status></VersioningConfiguration>",
            String(toXmlVersioningConfiguration(version).toByteArray())
        )
    }

    @Test
    fun testFromXmlVersioningConfiguration() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlVersioningConfiguration(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlVersioningConfiguration("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <VersioningConfiguration>
            <Status>Enabled</Status>
            </VersioningConfiguration>
        """.trimIndent()
        val result = fromXmlVersioningConfiguration(xml.toByteArray())
        assertEquals(result.status, "Enabled")
    }

    @Test
    fun testFromXmlListVersionsResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListVersionsResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListVersionsResult("<a></a>".toByteArray()) }

        // normal
        // no encoding
        var xml = """
            <ListVersionsResult>
            <Name>examplebucket-1250000000</Name>
            <Prefix>a%2Fprefix</Prefix>
            <KeyMarker>a%2Fexample</KeyMarker>
            <VersionIdMarker>CAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****</VersionIdMarker>
            <NextVersionIdMarker>BAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****</NextVersionIdMarker>
            <MaxKeys>1000</MaxKeys>
            <IsTruncated>false</IsTruncated>
            <Delimiter>a%2Fdelimiter</Delimiter>
            <EncodingType>a</EncodingType>
            <NextKeyMarker>a%2FnextKeyMarker</NextKeyMarker>
            <Version>
            <Key>a%2Fexample-object-1.jpg</Key>
            <VersionId>versionId1</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-5T12:03:10.000Z</LastModified>
            <ETag>5B3C1A2E053D763E1B669CC607C5A0FE1****</ETag>
            <TransitionTime>2019-08-9T12:03:06.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            </Version>
            <Version>
            <Key>a%2Fexample-object-2.jpg</Key>
            <VersionId>versionId2</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-9T12:03:09.000Z</LastModified>
            <ETag>5B3C1A2E053D763E1B002CC607C5A0FE1****</ETag>
            <TransitionTime>2019-08-9T12:03:08.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            </Version>
            <Version>
            <Key>a%2Fexample-object-3.jpg</Key>
            <VersionId>versionId3</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-10T12:03:08.000Z</LastModified>
            <ETag>4B3F1A2E053D763E1B002CC607C5AGTRF****</ETag>
            <TransitionTime>2019-08-9T12:03:07.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            </Version>
            <DeleteMarker>
            <Key>a%2Fexample</Key>
            <VersionId>CAEQMxiBgICAof2D0BYiIDJhMGE3N2M1YTI1NDQzOGY5NTkyNTI3MGYyMzJm****</VersionId>
            <IsLatest>false</IsLatest>
            <LastModified>2019-04-09T07:27:28.000Z</LastModified>
            <Owner>
            <ID>1234512528586****</ID>
            <DisplayName>12345125285864390</DisplayName>
            </Owner>
            </DeleteMarker>
            <CommonPrefixes>
            <Prefix>a%2Fb%2F</Prefix>
            </CommonPrefixes>
            </ListVersionsResult>
        """.trimIndent()
        var result = fromXmlListVersionsResult(xml.toByteArray())
        assertEquals("examplebucket-1250000000", result.name)
        assertEquals(1000, result.maxKeys)
        assertEquals("BAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****", result.nextVersionIdMarker)
        assertEquals("a%2FnextKeyMarker", result.nextKeyMarker)
        assertEquals("CAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****", result.versionIdMarker)
        assertEquals("a%2Fexample", result.keyMarker)
        assertEquals(false, result.isTruncated)
        assertEquals("a", result.encodingType)
        assertEquals("a%2Fdelimiter", result.delimiter)
        assertEquals("a%2Fprefix", result.prefix)
        assertEquals(3, result.versions?.size)
        assertEquals("a%2Fexample-object-1.jpg", result.versions?.get(0)?.key)
        assertEquals("versionId1", result.versions?.get(0)?.versionId)
        assertEquals(true, result.versions?.get(0)?.isLatest)
        assertEquals("2019-08-5T12:03:10.000Z", result.versions?.get(0)?.lastModified)
        assertEquals("5B3C1A2E053D763E1B669CC607C5A0FE1****", result.versions?.get(0)?.eTag)
        assertEquals("2019-08-9T12:03:06.000Z", result.versions?.get(0)?.transitionTime)
        assertEquals(20, result.versions?.get(0)?.size)
        assertEquals("STANDARD", result.versions?.get(0)?.storageClass)
        assertEquals("1250000000", result.versions?.get(0)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(0)?.owner?.displayName)
        assertEquals("a%2Fexample-object-2.jpg", result.versions?.get(1)?.key)
        assertEquals("versionId2", result.versions?.get(1)?.versionId)
        assertEquals(true, result.versions?.get(1)?.isLatest)
        assertEquals("2019-08-9T12:03:09.000Z", result.versions?.get(1)?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.versions?.get(1)?.eTag)
        assertEquals("2019-08-9T12:03:08.000Z", result.versions?.get(1)?.transitionTime)
        assertEquals(20, result.versions?.get(1)?.size)
        assertEquals("STANDARD", result.versions?.get(1)?.storageClass)
        assertEquals("1250000000", result.versions?.get(1)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(1)?.owner?.displayName)
        assertEquals("a%2Fexample-object-3.jpg", result.versions?.get(2)?.key)
        assertEquals("versionId3", result.versions?.get(2)?.versionId)
        assertEquals(true, result.versions?.get(2)?.isLatest)
        assertEquals("2019-08-10T12:03:08.000Z", result.versions?.get(2)?.lastModified)
        assertEquals("4B3F1A2E053D763E1B002CC607C5AGTRF****", result.versions?.get(2)?.eTag)
        assertEquals("2019-08-9T12:03:07.000Z", result.versions?.get(2)?.transitionTime)
        assertEquals(20, result.versions?.get(2)?.size)
        assertEquals("STANDARD", result.versions?.get(2)?.storageClass)
        assertEquals("1250000000", result.versions?.get(2)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(2)?.owner?.displayName)
        assertEquals(1, result.deleteMarkers?.size)
        assertEquals("a%2Fexample", result.deleteMarkers?.first()?.key)
        assertEquals("2019-04-09T07:27:28.000Z", result.deleteMarkers?.first()?.lastModified)
        assertEquals(false, result.deleteMarkers?.first()?.isLatest)
        assertEquals(
            "CAEQMxiBgICAof2D0BYiIDJhMGE3N2M1YTI1NDQzOGY5NTkyNTI3MGYyMzJm****",
            result.deleteMarkers?.first()?.versionId
        )
        assertEquals("1234512528586****", result.deleteMarkers?.first()?.owner?.id)
        assertEquals("12345125285864390", result.deleteMarkers?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("a%2Fb%2F", result.commonPrefixes?.first()?.prefix)

        // url encoding
        xml = """
            <ListVersionsResult>
            <Name>examplebucket-1250000000</Name>
            <Prefix>a%2Fprefix</Prefix>
            <KeyMarker>a%2Fexample</KeyMarker>
            <VersionIdMarker>CAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****</VersionIdMarker>
            <NextVersionIdMarker>BAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****</NextVersionIdMarker>
            <MaxKeys>1000</MaxKeys>
            <IsTruncated>false</IsTruncated>
            <Delimiter>a%2Fdelimiter</Delimiter>
            <EncodingType>url</EncodingType>
            <NextKeyMarker>a%2FnextKeyMarker</NextKeyMarker>
            <Version>
            <Key>a%2Fexample-object-1.jpg</Key>
            <VersionId>versionId1</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-5T12:03:10.000Z</LastModified>
            <ETag>5B3C1A2E053D763E1B669CC607C5A0FE1****</ETag>
            <TransitionTime>2019-08-9T12:03:06.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            </Version>
            <Version>
            <Key>a%2Fexample-object-2.jpg</Key>
            <VersionId>versionId2</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-9T12:03:09.000Z</LastModified>
            <ETag>5B3C1A2E053D763E1B002CC607C5A0FE1****</ETag>
            <TransitionTime>2019-08-9T12:03:08.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            </Version>
            <Version>
            <Key>a%2Fexample-object-3.jpg</Key>
            <VersionId>versionId3</VersionId>
            <IsLatest>true</IsLatest>
            <LastModified>2019-08-10T12:03:08.000Z</LastModified>
            <ETag>4B3F1A2E053D763E1B002CC607C5AGTRF****</ETag>
            <TransitionTime>2019-08-9T12:03:07.000Z</TransitionTime>
            <Size>20</Size>
            <StorageClass>STANDARD</StorageClass>
            <Owner>
            <ID>1250000000</ID>
            <DisplayName>1250000000</DisplayName>
            </Owner>
            </Version>
            <DeleteMarker>
            <Key>a%2Fexample</Key>
            <VersionId>CAEQMxiBgICAof2D0BYiIDJhMGE3N2M1YTI1NDQzOGY5NTkyNTI3MGYyMzJm****</VersionId>
            <IsLatest>false</IsLatest>
            <LastModified>2019-04-09T07:27:28.000Z</LastModified>
            <Owner>
            <ID>1234512528586****</ID>
            <DisplayName>12345125285864390</DisplayName>
            </Owner>
            </DeleteMarker>
            <CommonPrefixes>
            <Prefix>a%2Fb%2F</Prefix>
            </CommonPrefixes>
            </ListVersionsResult>
        """.trimIndent()
        result = fromXmlListVersionsResult(xml.toByteArray())
        assertEquals("examplebucket-1250000000", result.name)
        assertEquals(1000, result.maxKeys)
        assertEquals("BAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****", result.nextVersionIdMarker)
        assertEquals("a/nextKeyMarker", result.nextKeyMarker)
        assertEquals("CAEQMxiBgICbof2D0BYiIGRhZjgwMzJiMjA3MjQ0ODE5MWYxZDYwMzJlZjU1****", result.versionIdMarker)
        assertEquals("a/example", result.keyMarker)
        assertEquals(false, result.isTruncated)
        assertEquals("url", result.encodingType)
        assertEquals("a/delimiter", result.delimiter)
        assertEquals("a/prefix", result.prefix)
        assertEquals(3, result.versions?.size)
        assertEquals("a/example-object-1.jpg", result.versions?.get(0)?.key)
        assertEquals("versionId1", result.versions?.get(0)?.versionId)
        assertEquals(true, result.versions?.get(0)?.isLatest)
        assertEquals("2019-08-5T12:03:10.000Z", result.versions?.get(0)?.lastModified)
        assertEquals("5B3C1A2E053D763E1B669CC607C5A0FE1****", result.versions?.get(0)?.eTag)
        assertEquals("2019-08-9T12:03:06.000Z", result.versions?.get(0)?.transitionTime)
        assertEquals(20, result.versions?.get(0)?.size)
        assertEquals("STANDARD", result.versions?.get(0)?.storageClass)
        assertEquals("1250000000", result.versions?.get(0)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(0)?.owner?.displayName)
        assertEquals("a/example-object-2.jpg", result.versions?.get(1)?.key)
        assertEquals("versionId2", result.versions?.get(1)?.versionId)
        assertEquals(true, result.versions?.get(1)?.isLatest)
        assertEquals("2019-08-9T12:03:09.000Z", result.versions?.get(1)?.lastModified)
        assertEquals("5B3C1A2E053D763E1B002CC607C5A0FE1****", result.versions?.get(1)?.eTag)
        assertEquals("2019-08-9T12:03:08.000Z", result.versions?.get(1)?.transitionTime)
        assertEquals(20, result.versions?.get(1)?.size)
        assertEquals("STANDARD", result.versions?.get(1)?.storageClass)
        assertEquals("1250000000", result.versions?.get(1)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(1)?.owner?.displayName)
        assertEquals("a/example-object-3.jpg", result.versions?.get(2)?.key)
        assertEquals("versionId3", result.versions?.get(2)?.versionId)
        assertEquals(true, result.versions?.get(2)?.isLatest)
        assertEquals("2019-08-10T12:03:08.000Z", result.versions?.get(2)?.lastModified)
        assertEquals("4B3F1A2E053D763E1B002CC607C5AGTRF****", result.versions?.get(2)?.eTag)
        assertEquals("2019-08-9T12:03:07.000Z", result.versions?.get(2)?.transitionTime)
        assertEquals(20, result.versions?.get(2)?.size)
        assertEquals("STANDARD", result.versions?.get(2)?.storageClass)
        assertEquals("1250000000", result.versions?.get(2)?.owner?.id)
        assertEquals("1250000000", result.versions?.get(2)?.owner?.displayName)
        assertEquals(1, result.deleteMarkers?.size)
        assertEquals("a/example", result.deleteMarkers?.first()?.key)
        assertEquals("2019-04-09T07:27:28.000Z", result.deleteMarkers?.first()?.lastModified)
        assertEquals(false, result.deleteMarkers?.first()?.isLatest)
        assertEquals(
            "CAEQMxiBgICAof2D0BYiIDJhMGE3N2M1YTI1NDQzOGY5NTkyNTI3MGYyMzJm****",
            result.deleteMarkers?.first()?.versionId
        )
        assertEquals("1234512528586****", result.deleteMarkers?.first()?.owner?.id)
        assertEquals("12345125285864390", result.deleteMarkers?.first()?.owner?.displayName)
        assertEquals(1, result.commonPrefixes?.size)
        assertEquals("a/b/", result.commonPrefixes?.first()?.prefix)
    }
}
