package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.junit.Test
import kotlin.test.assertEquals

class XmlEncoderTest {

    @Test
    fun basicXml() {
        @Serializable
        @SerialName("SubBox")
        data class SubBox(
            @XmlElement("Message") val message: String
        )

        @Serializable
        @SerialName("Box")
        @XmlRoot
        data class Box(
            @XmlElement("SubBox") val subBox: SubBox,
            @XmlElement("Message") val message: List<String>
        )

        val xml = """
            <Box>
            <SubBox>
            <Message>hi</Message>
            </SubBox>
            <Message>hi1</Message>
            <Message>hi2</Message>
            </Box>
        """.trimIndent().replace("\n", "")
        val box = Box(
            SubBox("hi"),
            listOf("hi1", "hi2")
        )

        val actual = Xml.Default.encodeToString(box)
        assertEquals(xml, actual)
    }

    @Test
    fun complexXml() {
        @Serializable
        @SerialName("Contents")
        data class Contents(
            @XmlElement("Key") val key: String,
            @XmlElement("Size") val size: Int,
            @XmlElement("LastModified") val lastModified: String,
            @XmlElement("ETag") val eTag: String,
            @XmlElement("StorageClass") val storageClass: String,
            val temp: String
        )

        @Serializable
        @SerialName("OptionalFields")
        data class OptionalFields(
            @XmlElement("Field") val field: List<String>,
            val temp: List<String>
        )

        @Serializable
        @SerialName("ListBucketResult")
        @XmlRoot
        data class ListBucketResult(
            @XmlElement("Name") val name: String,
            @XmlElement("Prefix") val prefix: String,
            @XmlElement("MaxKeys") val maxKeys: Int,
            @XmlElement("EncodingType") val encodingType: String,
            @XmlElement("IsTruncated") val isTruncated: Boolean,
            @XmlElement("KeyCount") val keyCount: Int,
            @XmlElement("Contents") val contents: List<Contents>,
            @XmlElement("OptionalFields") val optionalFields: OptionalFields
        )

        val xml = """
            <ListBucketResult>
            <Name>examplebucket</Name>
            <Prefix>1</Prefix>
            <MaxKeys>100</MaxKeys>
            <EncodingType>url</EncodingType>
            <IsTruncated>false</IsTruncated>
            <KeyCount>2</KeyCount>
            <Contents>
            <Key>a</Key>
            <Size>25</Size>
            <LastModified>2020-05-18T05:45:43.000Z</LastModified>
            <ETag>"35A27C2B9EAEEB6F48FD7FB5861D****"</ETag>
            <StorageClass>STANDARD</StorageClass>
            </Contents>
            <Contents>
            <Key>a/b</Key>
            <Size>25</Size>
            <LastModified>2020-05-18T05:45:47.000Z</LastModified>
            <ETag>"35A27C2B9EAEEB6F48FD7FB5861D****"</ETag>
            <StorageClass>STANDARD</StorageClass>
            </Contents>
            <OptionalFields>
            <Field>Size</Field>
            <Field>LastModifiedDate</Field>
            <Field>ETag</Field>
            <Field>StorageClass</Field>
            <Field>IsMultipartUploaded</Field>
            <Field>EncryptionStatus</Field>
            </OptionalFields>
            </ListBucketResult>
        """.trimIndent().replace("\n", "")
        val result = ListBucketResult(
            name = "examplebucket",
            prefix = "1",
            maxKeys = 100,
            encodingType = "url",
            isTruncated = false,
            keyCount = 2,
            contents = listOf(
                Contents(
                    key = "a",
                    size = 25,
                    lastModified = "2020-05-18T05:45:43.000Z",
                    eTag = "\"35A27C2B9EAEEB6F48FD7FB5861D****\"",
                    storageClass = "STANDARD",
                    temp = "a"
                ),
                Contents(
                    key = "a/b",
                    size = 25,
                    lastModified = "2020-05-18T05:45:47.000Z",
                    eTag = "\"35A27C2B9EAEEB6F48FD7FB5861D****\"",
                    storageClass = "STANDARD",
                    temp = "b"
                )
            ),
            optionalFields = OptionalFields(
                listOf("Size", "LastModifiedDate", "ETag", "StorageClass", "IsMultipartUploaded", "EncryptionStatus"),
                listOf("1", "2")
            )
        )
        val actual = Xml.Default.encodeToString(result)
        assertEquals(xml, actual)
    }
}
