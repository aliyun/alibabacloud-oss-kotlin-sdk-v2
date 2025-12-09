package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.models.CreateBucketConfiguration
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeBucketBasicTest {

    @Test
    fun testFromXmlBucketStat() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlBucketStat(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlBucketStat("<a></a>".toByteArray()) }

        // normal
        val storage: Long = 1600
        val objectCount: Long = 230
        val multipartUploadCount: Long = 40
        val liveChannelCount: Long = 4
        val lastModifiedTime: Long = 1_643_341_269
        val standardStorage: Long = 430
        val standardObjectCount: Long = 66
        val infrequentAccessStorage: Long = 2_359_296
        val infrequentAccessRealStorage: Long = 360
        val infrequentAccessObjectCount: Long = 54
        val archiveStorage: Long = 2_949_120
        val archiveRealStorage: Long = 450
        val archiveObjectCount: Long = 74
        val coldArchiveStorage: Long = 2_359_296
        val coldArchiveRealStorage: Long = 360
        val coldArchiveObjectCount: Long = 36
        val deepColdArchiveStorage: Long = 2_359_296
        val deepColdArchiveRealStorage: Long = 360
        val deepColdArchiveObjectCount: Long = 36
        val deleteMarkerCount: Long = 12
        val xml = """
             <?xml version="1.0" encoding="UTF-8"?>
             <BucketStat>
             <Storage>$storage</Storage>
             <ObjectCount>$objectCount</ObjectCount>
             <MultipartUploadCount>$multipartUploadCount</MultipartUploadCount>
             <LiveChannelCount>$liveChannelCount</LiveChannelCount>
             <LastModifiedTime>$lastModifiedTime</LastModifiedTime>
             <StandardStorage>$standardStorage</StandardStorage>
             <StandardObjectCount>$standardObjectCount</StandardObjectCount>
             <InfrequentAccessStorage>$infrequentAccessStorage</InfrequentAccessStorage>
             <InfrequentAccessRealStorage>$infrequentAccessRealStorage</InfrequentAccessRealStorage>
             <InfrequentAccessObjectCount>$infrequentAccessObjectCount</InfrequentAccessObjectCount>
             <ArchiveStorage>$archiveStorage</ArchiveStorage>
             <ArchiveRealStorage>$archiveRealStorage</ArchiveRealStorage>
             <ArchiveObjectCount>$archiveObjectCount</ArchiveObjectCount>
             <ColdArchiveStorage>$coldArchiveStorage</ColdArchiveStorage>
             <ColdArchiveRealStorage>$coldArchiveRealStorage</ColdArchiveRealStorage>
             <ColdArchiveObjectCount>$coldArchiveObjectCount</ColdArchiveObjectCount>
             <DeleteMarkerCount>$deleteMarkerCount</DeleteMarkerCount>
             <DeepColdArchiveStorage>$deepColdArchiveStorage</DeepColdArchiveStorage>
             <DeepColdArchiveRealStorage>$deepColdArchiveRealStorage</DeepColdArchiveRealStorage>
             <DeepColdArchiveObjectCount>$deepColdArchiveObjectCount</DeepColdArchiveObjectCount>
             </BucketStat>
        """.trimIndent()
        val result = fromXmlBucketStat(xml.toByteArray())
        assertEquals(result.storage, storage)
        assertEquals(result.objectCount, objectCount)
        assertEquals(result.multipartUploadCount, multipartUploadCount)
        assertEquals(result.liveChannelCount, liveChannelCount)
        assertEquals(result.lastModifiedTime, lastModifiedTime)
        assertEquals(result.standardStorage, standardStorage)
        assertEquals(result.standardObjectCount, standardObjectCount)
        assertEquals(result.infrequentAccessStorage, infrequentAccessStorage)
        assertEquals(
            result.infrequentAccessRealStorage,
            infrequentAccessRealStorage
        )
        assertEquals(
            result.infrequentAccessObjectCount,
            infrequentAccessObjectCount
        )
        assertEquals(result.archiveStorage, archiveStorage)
        assertEquals(result.archiveRealStorage, archiveRealStorage)
        assertEquals(result.archiveObjectCount, archiveObjectCount)
        assertEquals(result.coldArchiveStorage, coldArchiveStorage)
        assertEquals(result.coldArchiveRealStorage, coldArchiveRealStorage)
        assertEquals(result.coldArchiveObjectCount, coldArchiveObjectCount)
        assertEquals(result.deleteMarkerCount, deleteMarkerCount)
        assertEquals(result.deepColdArchiveStorage, deepColdArchiveStorage)
        assertEquals(
            result.deepColdArchiveRealStorage,
            deepColdArchiveRealStorage
        )
        assertEquals(
            result.deepColdArchiveObjectCount,
            deepColdArchiveObjectCount
        )
    }

    @Test
    fun testToXmlCreateBucketConfiguration() {
        val storageClass = "Archive"
        val dataRedundancyType = "ZRS"

        var xml = toXmlCreateBucketConfiguration(CreateBucketConfiguration.Builder().build())
        runBlocking {
            assertEquals(
                String(xml.toByteArray()),
                "<CreateBucketConfiguration></CreateBucketConfiguration>"
            )
        }

        val createBucketConfiguration = CreateBucketConfiguration.Builder().apply {
            this.storageClass = storageClass
            this.dataRedundancyType = dataRedundancyType
        }.build()
        xml = toXmlCreateBucketConfiguration(createBucketConfiguration)
        runBlocking {
            assertEquals(
                String(xml.toByteArray()),
                "<CreateBucketConfiguration><StorageClass>Archive</StorageClass><DataRedundancyType>ZRS</DataRedundancyType></CreateBucketConfiguration>"
            )
        }
    }

    @Test
    fun testFromXmlListBucketResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListBucketResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListBucketResult("<a></a>".toByteArray()) }

        // one result
        var xml = """
            <ListBucketResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Name>examplebucket</Name>
            <Prefix>Prefix/</Prefix>
            <Marker>marker</Marker>
            <NextMarker>nextMarker</NextMarker>
            <MaxKeys>100</MaxKeys>
            <Delimiter>delimiter</Delimiter>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <Contents>
            <Key>fun%2Fmovie%2F001.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>344606</Size>
            <StorageClass>Standard</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            <CommonPrefixes>
            <Prefix>a%2Fb%2F</Prefix>
            </CommonPrefixes>
            </ListBucketResult>
        """.trimIndent()

        var result = fromXmlListBucketResult(xml.toByteArray())
        assertEquals(result.delimiter, "delimiter")
        assertEquals(result.isTruncated, false)
        assertEquals(result.marker, "marker")
        assertEquals(result.nextMarker, "nextMarker")
        assertEquals(result.maxKeys, 100)
        assertEquals(result.name, "examplebucket")
        assertEquals(result.prefix, "Prefix/")
        assertEquals(result.encodingType, "url")
        assertEquals(result.contents?.size, 1)
        assertEquals(result.contents?.first()?.key, "fun/movie/001.avi")
        assertEquals(result.contents?.first()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.first()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.first()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.first()?.type, "Normal")
        assertEquals(result.contents?.first()?.size, 344606)
        assertEquals(result.contents?.first()?.storageClass, "Standard")
        assertEquals(result.contents?.first()?.owner?.id, "0022012****")
        assertEquals(result.contents?.first()?.owner?.displayName, "user-example")
        assertEquals(result.commonPrefixes?.size, 1)
        assertEquals(result.commonPrefixes?.first()?.prefix, "a/b/")

        // multiple results
        xml = """
            <ListBucketResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Name>examplebucket</Name>
            <Prefix>Prefix/</Prefix>
            <Marker>marker</Marker>
            <NextMarker>nextMarker</NextMarker>
            <MaxKeys>100</MaxKeys>
            <Delimiter>delimiter</Delimiter>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <Contents>
            <Key>fun/movie/001.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>344606</Size>
            <StorageClass>Standard</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            <Contents>
            <Key>fun/movie/002.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>444606</Size>
            <StorageClass>Archive</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            </ListBucketResult>
        """.trimIndent()

        result = fromXmlListBucketResult(xml.toByteArray())
        assertEquals(result.delimiter, "delimiter")
        assertEquals(result.isTruncated, false)
        assertEquals(result.marker, "marker")
        assertEquals(result.nextMarker, "nextMarker")
        assertEquals(result.maxKeys, 100)
        assertEquals(result.name, "examplebucket")
        assertEquals(result.prefix, "Prefix/")
        assertEquals(result.encodingType, "url")
        assertEquals(result.contents?.size, 2)
        assertEquals(result.contents?.first()?.key, "fun/movie/001.avi")
        assertEquals(result.contents?.first()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.first()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.first()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.first()?.type, "Normal")
        assertEquals(result.contents?.first()?.size, 344606)
        assertEquals(result.contents?.first()?.storageClass, "Standard")
        assertEquals(result.contents?.first()?.owner?.id, "0022012****")
        assertEquals(result.contents?.first()?.owner?.displayName, "user-example")
        assertEquals(result.contents?.last()?.key, "fun/movie/002.avi")
        assertEquals(result.contents?.last()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.last()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.last()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.last()?.type, "Normal")
        assertEquals(result.contents?.last()?.size, 444606)
        assertEquals(result.contents?.last()?.storageClass, "Archive")
        assertEquals(result.contents?.last()?.owner?.id, "0022012****")
        assertEquals(result.contents?.last()?.owner?.displayName, "user-example")
    }

    @Test
    fun testFromXmlListBucketV2Result() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListBucketV2Result(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListBucketV2Result("<a></a>".toByteArray()) }

        // normal
        // one result
        var xml = """
            <ListBucketResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Name>examplebucket</Name>
            <Prefix>prefix%2F</Prefix>
            <ContinuationToken>continuationToken</ContinuationToken>
            <NextContinuationToken>nextContinuationToken</NextContinuationToken>
            <MaxKeys>100</MaxKeys>
            <KeyCount>6</KeyCount>
            <Delimiter>delimiter</Delimiter>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <StartAfter>startAfter</StartAfter>
            <Contents>
            <Key>fun%2Fmovie%2F001.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>344606</Size>
            <StorageClass>Standard</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            <CommonPrefixes>
            <Prefix>a%2Fb%2F</Prefix>
            </CommonPrefixes>
            </ListBucketResult>
        """.trimIndent()

        var result = fromXmlListBucketV2Result(xml.toByteArray())
        assertEquals(result.delimiter, "delimiter")
        assertEquals(result.isTruncated, false)
        assertEquals(result.continuationToken, "continuationToken")
        assertEquals(result.nextContinuationToken, "nextContinuationToken")
        assertEquals(result.maxKeys, 100)
        assertEquals(result.keyCount, 6)
        assertEquals(result.name, "examplebucket")
        assertEquals(result.startAfter, "startAfter")
        assertEquals(result.prefix, "prefix/")
        assertEquals(result.encodingType, "url")
        assertEquals(result.contents?.size, 1)
        assertEquals(result.contents?.first()?.key, "fun/movie/001.avi")
        assertEquals(result.contents?.first()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.first()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.first()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.first()?.type, "Normal")
        assertEquals(result.contents?.first()?.size, 344606)
        assertEquals(result.contents?.first()?.storageClass, "Standard")
        assertEquals(result.contents?.first()?.owner?.id, "0022012****")
        assertEquals(result.contents?.first()?.owner?.displayName, "user-example")
        assertEquals(result.commonPrefixes?.size, 1)
        assertEquals(result.commonPrefixes?.first()?.prefix, "a/b/")

        // multiple results
        xml = """
            <ListBucketResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Name>examplebucket</Name>
            <Prefix>Prefix/</Prefix>
            <ContinuationToken>continuationToken</ContinuationToken>
            <NextContinuationToken>nextContinuationToken</NextContinuationToken>
            <MaxKeys>100</MaxKeys>
            <KeyCount>6</KeyCount>
            <Delimiter>delimiter</Delimiter>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <StartAfter>startAfter</StartAfter>
            <Contents>
            <Key>fun/movie/001.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>344606</Size>
            <StorageClass>Standard</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            <Contents>
            <Key>fun/movie/002.avi</Key>
            <ETag>"5B3C1A2E053D763E1B002CC607C5A0FE1****"</ETag>
            <TransitionTime>2024-04-23T07:21:42.000Z</TransitionTime>
            <LastModified>2012-02-24T08:43:07.000Z</LastModified>
            <Type>Normal</Type>
            <Size>444606</Size>
            <StorageClass>Archive</StorageClass>
            <Owner>
            <ID>0022012****</ID>
            <DisplayName>user-example</DisplayName>
            </Owner>
            </Contents>
            </ListBucketResult>
        """.trimIndent()

        result = fromXmlListBucketV2Result(xml.toByteArray())
        assertEquals(result.delimiter, "delimiter")
        assertEquals(result.continuationToken, "continuationToken")
        assertEquals(result.nextContinuationToken, "nextContinuationToken")
        assertEquals(result.maxKeys, 100)
        assertEquals(result.keyCount, 6)
        assertEquals(result.name, "examplebucket")
        assertEquals(result.startAfter, "startAfter")
        assertEquals(result.prefix, "Prefix/")
        assertEquals(result.encodingType, "url")
        assertEquals(result.contents?.size, 2)
        assertEquals(result.contents?.first()?.key, "fun/movie/001.avi")
        assertEquals(result.contents?.first()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.first()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.first()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.first()?.type, "Normal")
        assertEquals(result.contents?.first()?.size, 344606)
        assertEquals(result.contents?.first()?.storageClass, "Standard")
        assertEquals(result.contents?.first()?.owner?.id, "0022012****")
        assertEquals(result.contents?.first()?.owner?.displayName, "user-example")
        assertEquals(result.contents?.last()?.key, "fun/movie/002.avi")
        assertEquals(result.contents?.last()?.eTag, "\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
        assertEquals(result.contents?.last()?.transitionTime, "2024-04-23T07:21:42.000Z")
        assertEquals(result.contents?.last()?.lastModified, "2012-02-24T08:43:07.000Z")
        assertEquals(result.contents?.last()?.type, "Normal")
        assertEquals(result.contents?.last()?.size, 444606)
        assertEquals(result.contents?.last()?.storageClass, "Archive")
        assertEquals(result.contents?.last()?.owner?.id, "0022012****")
        assertEquals(result.contents?.last()?.owner?.displayName, "user-example")
    }

    @Test
    fun testFromXmlBucketInfo() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlBucketInfo(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlBucketInfo("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <BucketInfo>
            <Bucket>
            <AccessMonitor>Enabled</AccessMonitor>
            <CreationDate>2013-07-31T10:56:21.000Z</CreationDate>
            <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>
            <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>
            <Location>oss-cn-hangzhou</Location>
            <StorageClass>Standard</StorageClass>
            <TransferAcceleration>Disabled</TransferAcceleration>
            <CrossRegionReplication>Disabled</CrossRegionReplication>
            <Name>oss-example</Name>
            <ResourceGroupId>rg-aek27tc********</ResourceGroupId>
            <Owner>
            <DisplayName>username</DisplayName>
            <ID>27183473914****</ID>
            </Owner>
            <AccessControlList>
            <Grant>private</Grant>
            </AccessControlList>  
            <Comment>test</Comment>
            <BucketPolicy>
            <LogBucket>examplebucket</LogBucket>
            <LogPrefix>log/</LogPrefix>
            </BucketPolicy>
            <BlockPublicAccess>true</BlockPublicAccess>
            <DataRedundancyType>LRS</DataRedundancyType>
            <Versioning>Enabled</Versioning>
            <ServerSideEncryptionRule>
            <SSEAlgorithm>KMS</SSEAlgorithm>
            <KMSMasterKeyID>kMSMasterKeyID</KMSMasterKeyID>
            <KMSDataEncryption>SM4</KMSDataEncryption>
            </ServerSideEncryptionRule>
            </Bucket>
            </BucketInfo>
        """.trimIndent()
        val result = fromXmlBucketInfo(xml.toByteArray())
        assertEquals("Enabled", result.bucket?.accessMonitor)
        assertEquals("2013-07-31T10:56:21.000Z", result.bucket?.creationDate)
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result.bucket?.extranetEndpoint)
        assertEquals("oss-cn-hangzhou-internal.aliyuncs.com", result.bucket?.intranetEndpoint)
        assertEquals("oss-cn-hangzhou", result.bucket?.location)
        assertEquals("Standard", result.bucket?.storageClass)
        assertEquals("Disabled", result.bucket?.transferAcceleration)
        assertEquals("Disabled", result.bucket?.crossRegionReplication)
        assertEquals("oss-example", result.bucket?.name)
        assertEquals("rg-aek27tc********", result.bucket?.resourceGroupId)
        assertEquals("27183473914****", result.bucket?.owner?.id)
        assertEquals("username", result.bucket?.owner?.displayName)
        assertEquals("private", result.bucket?.accessControlList?.grant)
        assertEquals("test", result.bucket?.comment)
        assertEquals("examplebucket", result.bucket?.bucketPolicy?.logBucket)
        assertEquals("log/", result.bucket?.bucketPolicy?.logPrefix)
        assertEquals(true, result.bucket?.blockPublicAccess)
        assertEquals("LRS", result.bucket?.dataRedundancyType)
        assertEquals("Enabled", result.bucket?.versioning)
        assertEquals("KMS", result.bucket?.serverSideEncryptionRule?.sSEAlgorithm)
        assertEquals("SM4", result.bucket?.serverSideEncryptionRule?.kMSDataEncryption)
        assertEquals("kMSMasterKeyID", result.bucket?.serverSideEncryptionRule?.kMSMasterKeyID)
    }

    @Test
    fun testFromXmlString() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlBucketLocation(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlBucketLocation("<a></a>".toByteArray()) }

        // normal
        val xml = "<LocationConstraint>oss-cn-hangzhou</LocationConstraint>"
        val result = fromXmlBucketLocation(xml.toByteArray())
        assertEquals("oss-cn-hangzhou", result)
    }
}
