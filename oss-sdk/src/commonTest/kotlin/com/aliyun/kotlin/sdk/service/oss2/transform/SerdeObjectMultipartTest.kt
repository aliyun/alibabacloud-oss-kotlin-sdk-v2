package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.models.CompleteMultipartUpload
import com.aliyun.kotlin.sdk.service.oss2.models.UploadPart
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeObjectMultipartTest {

    @Test
    fun testFromXmlInitiateMultipartUpload() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlInitiateMultipartUpload(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlInitiateMultipartUpload("<a></a>".toByteArray()) }

        // normal
        // no encoding
        var xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <InitiateMultipartUploadResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
                <Bucket>oss-example</Bucket>
                <Key>a%2fmultipart.data</Key>
                <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>
            </InitiateMultipartUploadResult>
        """.trimIndent()
        var result = fromXmlInitiateMultipartUpload(xml.toByteArray())
        assertEquals("a%2fmultipart.data", result.key)
        assertEquals("oss-example", result.bucket)
        assertEquals("0004B9894A22E5B1888A1E29F823****", result.uploadId)

        // url encoding
        xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <InitiateMultipartUploadResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
                <Bucket>oss-example</Bucket>
                <Key>a%2fmultipart.data</Key>
                <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>
                <EncodingType>url</EncodingType>
            </InitiateMultipartUploadResult>
        """.trimIndent()
        result = fromXmlInitiateMultipartUpload(xml.toByteArray())
        assertEquals("a/multipart.data", result.key)
        assertEquals("oss-example", result.bucket)
        assertEquals("0004B9894A22E5B1888A1E29F823****", result.uploadId)
    }

    @Test
    fun testToXmlCompleteMultipartUpload() {
        var completeMultipartUpload = CompleteMultipartUpload.Builder().build()
        runBlocking {
            assertEquals(
                "<CompleteMultipartUpload></CompleteMultipartUpload>",
                String(toXmlCompleteMultipartUpload(completeMultipartUpload).toByteArray())
            )
        }

        val xml = """
            <CompleteMultipartUpload>
            <Part>
            <PartNumber>1</PartNumber>
            <ETag>"3349DC700140D7F86A0784842780****"</ETag>
            </Part>
            <Part>
            <PartNumber>5</PartNumber>
            <ETag>"8EFDA8BE206636A695359836FE0A****"</ETag>
            </Part>
            <Part>
            <PartNumber>8</PartNumber>
            <ETag>"8C315065167132444177411FDA14****"</ETag>
            </Part>
            </CompleteMultipartUpload>
        """.trimIndent().replace("\n", "")
        completeMultipartUpload = CompleteMultipartUpload.Builder().apply {
            parts = listOf(
                UploadPart.Builder().apply {
                    eTag = "\"3349DC700140D7F86A0784842780****\""
                    partNumber = 1
                }.build(),
                UploadPart.Builder().apply {
                    eTag = "\"8EFDA8BE206636A695359836FE0A****\""
                    partNumber = 5
                }.build(),
                UploadPart.Builder().apply {
                    eTag = "\"8C315065167132444177411FDA14****\""
                    partNumber = 8
                }.build()
            )
        }.build()
        runBlocking {
            assertEquals(
                xml,
                String(toXmlCompleteMultipartUpload(completeMultipartUpload).toByteArray())
            )
        }
    }

    @Test
    fun testFromXmlCompleteMultipartUpload() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlCompleteMultipartUpload(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlCompleteMultipartUpload("<a></a>".toByteArray()) }

        // normal
        // no encoding
        var xml = """
            <CompleteMultipartUploadResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <EncodingType>a</EncodingType>
            <Location>http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data</Location>
            <Bucket>oss-example</Bucket>
            <Key>a%2fmultipart.data</Key>
            <ETag>"B864DB6A936D376F9F8D3ED3BBE540****"</ETag>
            </CompleteMultipartUploadResult>
        """.trimIndent()
        var result = fromXmlCompleteMultipartUpload(xml.toByteArray())
        assertEquals("a%2fmultipart.data", result.key)
        assertEquals("oss-example", result.bucket)
        assertEquals("a", result.encodingType)
        assertEquals("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data", result.location)
        assertEquals("\"B864DB6A936D376F9F8D3ED3BBE540****\"", result.eTag)

        // url encoding
        xml = """
            <CompleteMultipartUploadResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <EncodingType>url</EncodingType>
            <Location>http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data</Location>
            <Bucket>oss-example</Bucket>
            <Key>a%2fmultipart.data</Key>
            <ETag>"B864DB6A936D376F9F8D3ED3BBE540****"</ETag>
            </CompleteMultipartUploadResult>
        """.trimIndent()
        result = fromXmlCompleteMultipartUpload(xml.toByteArray())
        assertEquals("a/multipart.data", result.key)
        assertEquals("oss-example", result.bucket)
        assertEquals("url", result.encodingType)
        assertEquals("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data", result.location)
        assertEquals("\"B864DB6A936D376F9F8D3ED3BBE540****\"", result.eTag)
    }

    @Test
    fun testFromXmlCopyPartResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlCopyPartResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlCopyPartResult("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <CopyPartResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <LastModified>2014-07-17T06:27:54.000Z</LastModified>
            <ETag>"5B3C1A2E053D763E1B002CC607C5****"</ETag>
            </CopyPartResult>
        """.trimIndent()
        val result = fromXmlCopyPartResult(xml.toByteArray())
        assertEquals("2014-07-17T06:27:54.000Z", result.lastModified)
        assertEquals("\"5B3C1A2E053D763E1B002CC607C5****\"", result.eTag)
    }

    @Test
    fun testFromXmlListMultipartUploadsResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListMultipartUploadsResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListMultipartUploadsResult("<a></a>".toByteArray()) }

        // normal
        // no encoding
        var xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListMultipartUploadsResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Bucket>oss-example</Bucket>
            <KeyMarker>a%2fb</KeyMarker>
            <UploadIdMarker>uploadIdMarker</UploadIdMarker>
            <NextKeyMarker>a%2foss.avi</NextKeyMarker>
            <NextUploadIdMarker>0004B99B8E707874FC2D692FA5D77D3F</NextUploadIdMarker>
            <Delimiter>a%2fb</Delimiter>
            <Prefix>a%2fb</Prefix>
            <MaxUploads>1000</MaxUploads>
            <IsTruncated>false</IsTruncated>
            <Upload>
            <Key>a%2fmultipart.data</Key>
            <UploadId>0004B999EF518A1FE585B0C9360DC4C8</UploadId>
            <Initiated>2012-02-23T04:18:23.000Z</Initiated>
            </Upload>
            <Upload>
            <Key>a%2fmultipart2.data</Key>
            <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>
            <Initiated>2012-02-23T04:18:23.000Z</Initiated>
            </Upload>
            <Upload>
            <Key>a%2foss.avi</Key>
            <UploadId>0004B99B8E707874FC2D692FA5D7****</UploadId>
            <Initiated>2012-02-23T06:14:27.000Z</Initiated>
            </Upload>
            </ListMultipartUploadsResult>
        """.trimIndent()
        var result = fromXmlListMultipartUploadsResult(xml.toByteArray())
        assertEquals("oss-example", result.bucket)
        assertEquals("a%2fb", result.keyMarker)
        assertEquals("uploadIdMarker", result.uploadIdMarker)
        assertEquals("a%2foss.avi", result.nextKeyMarker)
        assertEquals("0004B99B8E707874FC2D692FA5D77D3F", result.nextUploadIdMarker)
        assertEquals("a%2fb", result.delimiter)
        assertEquals("a%2fb", result.prefix)
        assertEquals(1000, result.maxUploads)
        assertEquals(false, result.isTruncated)
        assertEquals(3, result.uploads?.size)
        assertEquals("a%2fmultipart.data", result.uploads?.get(0)?.key)
        assertEquals("0004B999EF518A1FE585B0C9360DC4C8", result.uploads?.get(0)?.uploadId)
        assertEquals("2012-02-23T04:18:23.000Z", result.uploads?.get(0)?.initiated)
        assertEquals("a%2fmultipart2.data", result.uploads?.get(1)?.key)
        assertEquals("0004B999EF5A239BB9138C6227D6****", result.uploads?.get(1)?.uploadId)
        assertEquals("2012-02-23T04:18:23.000Z", result.uploads?.get(1)?.initiated)
        assertEquals("a%2foss.avi", result.uploads?.get(2)?.key)
        assertEquals("0004B99B8E707874FC2D692FA5D7****", result.uploads?.get(2)?.uploadId)
        assertEquals("2012-02-23T06:14:27.000Z", result.uploads?.get(2)?.initiated)

        // url encoding
        xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListMultipartUploadsResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Bucket>oss-example</Bucket>
            <KeyMarker>a%2fb</KeyMarker>
            <UploadIdMarker>uploadIdMarker</UploadIdMarker>
            <NextKeyMarker>a%2foss.avi</NextKeyMarker>
            <NextUploadIdMarker>0004B99B8E707874FC2D692FA5D77D3F</NextUploadIdMarker>
            <Delimiter>a%2fb</Delimiter>
            <Prefix>a%2fb</Prefix>
            <MaxUploads>1000</MaxUploads>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <Upload>
            <Key>a%2fmultipart.data</Key>
            <UploadId>0004B999EF518A1FE585B0C9360DC4C8</UploadId>
            <Initiated>2012-02-23T04:18:23.000Z</Initiated>
            </Upload>
            <Upload>
            <Key>a%2fmultipart2.data</Key>
            <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>
            <Initiated>2012-02-23T04:18:23.000Z</Initiated>
            </Upload>
            <Upload>
            <Key>a%2foss.avi</Key>
            <UploadId>0004B99B8E707874FC2D692FA5D7****</UploadId>
            <Initiated>2012-02-23T06:14:27.000Z</Initiated>
            </Upload>
            </ListMultipartUploadsResult>
        """.trimIndent()
        result = fromXmlListMultipartUploadsResult(xml.toByteArray())
        assertEquals("oss-example", result.bucket)
        assertEquals("a/b", result.keyMarker)
        assertEquals("uploadIdMarker", result.uploadIdMarker)
        assertEquals("a/oss.avi", result.nextKeyMarker)
        assertEquals("0004B99B8E707874FC2D692FA5D77D3F", result.nextUploadIdMarker)
        assertEquals("a/b", result.delimiter)
        assertEquals("a/b", result.prefix)
        assertEquals(1000, result.maxUploads)
        assertEquals(false, result.isTruncated)
        assertEquals("url", result.encodingType)
        assertEquals(3, result.uploads?.size)
        assertEquals("a/multipart.data", result.uploads?.get(0)?.key)
        assertEquals("0004B999EF518A1FE585B0C9360DC4C8", result.uploads?.get(0)?.uploadId)
        assertEquals("2012-02-23T04:18:23.000Z", result.uploads?.get(0)?.initiated)
        assertEquals("a/multipart2.data", result.uploads?.get(1)?.key)
        assertEquals("0004B999EF5A239BB9138C6227D6****", result.uploads?.get(1)?.uploadId)
        assertEquals("2012-02-23T04:18:23.000Z", result.uploads?.get(1)?.initiated)
        assertEquals("a/oss.avi", result.uploads?.get(2)?.key)
        assertEquals("0004B99B8E707874FC2D692FA5D7****", result.uploads?.get(2)?.uploadId)
        assertEquals("2012-02-23T06:14:27.000Z", result.uploads?.get(2)?.initiated)
    }

    @Test
    fun testFromXmlListPartResult() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlListPartResult(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlListPartResult("<a></a>".toByteArray()) }

        // normal
        // no encoding
        var xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListPartsResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Bucket>multipart_upload</Bucket>
            <Key>a%2fmultipart.data</Key>
            <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>
            <NextPartNumberMarker>5</NextPartNumberMarker>
            <MaxParts>1000</MaxParts>
            <IsTruncated>false</IsTruncated>
            <Part>
            <PartNumber>1</PartNumber>
            <LastModified>2012-02-23T07:01:34.000Z</LastModified>
            <ETag>"3349DC700140D7F86A0784842780****"</ETag>
            <Size>6291456</Size>
            </Part>
            <Part>
            <PartNumber>2</PartNumber>
            <LastModified>2012-02-23T07:01:12.000Z</LastModified>
            <ETag>"3349DC700140D7F86A0784842780****"</ETag>
            <Size>6291456</Size>
            </Part>
            <Part>
            <PartNumber>5</PartNumber>
            <LastModified>2012-02-23T07:02:03.000Z</LastModified>
            <ETag>"7265F4D211B56873A381D321F586****"</ETag>
            <Size>1024</Size>
            </Part>
            </ListPartsResult>
        """.trimIndent()
        var result = fromXmlListPartResult(xml.toByteArray())
        assertEquals("multipart_upload", result.bucket)
        assertEquals("a%2fmultipart.data", result.key)
        assertEquals("0004B999EF5A239BB9138C6227D6****", result.uploadId)
        assertEquals(5, result.nextPartNumberMarker)
        assertEquals(1000, result.maxParts)
        assertEquals(false, result.isTruncated)
        assertEquals(3, result.parts?.size)
        assertEquals(1, result.parts?.get(0)?.partNumber)
        assertEquals("2012-02-23T07:01:34.000Z", result.parts?.get(0)?.lastModified)
        assertEquals("\"3349DC700140D7F86A0784842780****\"", result.parts?.get(0)?.eTag)
        assertEquals(6291456, result.parts?.get(0)?.size)
        assertEquals(2, result.parts?.get(1)?.partNumber)
        assertEquals("2012-02-23T07:01:12.000Z", result.parts?.get(1)?.lastModified)
        assertEquals("\"3349DC700140D7F86A0784842780****\"", result.parts?.get(1)?.eTag)
        assertEquals(6291456, result.parts?.get(1)?.size)
        assertEquals(5, result.parts?.get(2)?.partNumber)
        assertEquals("2012-02-23T07:02:03.000Z", result.parts?.get(2)?.lastModified)
        assertEquals("\"7265F4D211B56873A381D321F586****\"", result.parts?.get(2)?.eTag)
        assertEquals(1024, result.parts?.get(2)?.size)

        // url encoding
        xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListPartsResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Bucket>multipart_upload</Bucket>
            <Key>a%2fmultipart.data</Key>
            <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>
            <NextPartNumberMarker>5</NextPartNumberMarker>
            <MaxParts>1000</MaxParts>
            <IsTruncated>false</IsTruncated>
            <EncodingType>url</EncodingType>
            <Part>
            <PartNumber>1</PartNumber>
            <LastModified>2012-02-23T07:01:34.000Z</LastModified>
            <ETag>"3349DC700140D7F86A0784842780****"</ETag>
            <Size>6291456</Size>
            </Part>
            <Part>
            <PartNumber>2</PartNumber>
            <LastModified>2012-02-23T07:01:12.000Z</LastModified>
            <ETag>"3349DC700140D7F86A0784842780****"</ETag>
            <Size>6291456</Size>
            </Part>
            <Part>
            <PartNumber>5</PartNumber>
            <LastModified>2012-02-23T07:02:03.000Z</LastModified>
            <ETag>"7265F4D211B56873A381D321F586****"</ETag>
            <Size>1024</Size>
            </Part>
            </ListPartsResult>
        """.trimIndent()
        result = fromXmlListPartResult(xml.toByteArray())
        assertEquals("multipart_upload", result.bucket)
        assertEquals("a/multipart.data", result.key)
        assertEquals("0004B999EF5A239BB9138C6227D6****", result.uploadId)
        assertEquals(5, result.nextPartNumberMarker)
        assertEquals(1000, result.maxParts)
        assertEquals(false, result.isTruncated)
        assertEquals(3, result.parts?.size)
        assertEquals(1, result.parts?.get(0)?.partNumber)
        assertEquals("2012-02-23T07:01:34.000Z", result.parts?.get(0)?.lastModified)
        assertEquals("\"3349DC700140D7F86A0784842780****\"", result.parts?.get(0)?.eTag)
        assertEquals(6291456, result.parts?.get(0)?.size)
        assertEquals(2, result.parts?.get(1)?.partNumber)
        assertEquals("2012-02-23T07:01:12.000Z", result.parts?.get(1)?.lastModified)
        assertEquals("\"3349DC700140D7F86A0784842780****\"", result.parts?.get(1)?.eTag)
        assertEquals(6291456, result.parts?.get(1)?.size)
        assertEquals(5, result.parts?.get(2)?.partNumber)
        assertEquals("2012-02-23T07:02:03.000Z", result.parts?.get(2)?.lastModified)
        assertEquals("\"7265F4D211B56873A381D321F586****\"", result.parts?.get(2)?.eTag)
        assertEquals(1024, result.parts?.get(2)?.size)
    }
}
