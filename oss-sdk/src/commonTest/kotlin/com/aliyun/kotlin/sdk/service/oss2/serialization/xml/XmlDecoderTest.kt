package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class XmlDecoderTest {

    @Serializable
    @SerialName("Greeting")
    @XmlRoot
    data class Greeting(
        @XmlElement("Message") val message: List<String>,
        @XmlElement("SubGreeting") val subGreeting: SubGreeting
    )

    @Serializable
    @SerialName("SubGreeting")
    data class SubGreeting(
        @XmlElement("Message") val message: String
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

    @Serializable
    @SerialName("Contents")
    data class Contents(
        @XmlElement("Key") val key: String,
        @XmlElement("Size") val size: Int,
        @XmlElement("LastModified") val lastModified: String,
        @XmlElement("ETag") val eTag: String,
        @XmlElement("StorageClass") val storageClass: String,
        var temp: String? = null
    )

    @Serializable
    @SerialName("OptionalFields")
    data class OptionalFields(
        @XmlElement("Field") val field: List<String>,
        var temp: List<String>? = null
    )

    @Test
    fun basicXml() {
        val xml = """
            <Greeting>
            <SubGreeting>
            <Message>hi</Message>
            </SubGreeting>
            <Message>Hi1</Message>
            <Message>Hi2</Message>
            <Param>param</Param>
            </Greeting>
        """.trimIndent()

        val actual = XmlSerializer.Default.decodeFromString<Greeting>(xml)
        assertEquals(
            Greeting(
                message = listOf("Hi1", "Hi2"),
                subGreeting = SubGreeting("hi")
            ),
            actual
        )
    }

    @Test
    fun skipsComments() {
        val xml = """
            <!-- This is some fiiine XML! -->
            <Greeting>
            <!-- In here's not so bad! -->
            <SubGreeting>
            <Message>hi</Message>
            </SubGreeting>
            <Message>Hi1</Message>
            </Greeting>
        """.trimIndent()
        val actual = XmlSerializer.Default.decodeFromString<Greeting>(xml)
        assertEquals(
            Greeting(
                message = listOf("Hi1"),
                subGreeting = SubGreeting("hi")
            ),
            actual
        )
    }

    @Test
    fun skipsXmlDecl() {
        val xml = """
            <?xml version="1.1"?>
            <Greeting>
            <SubGreeting>
            <Message>hi</Message>
            </SubGreeting>
            <Message>Hi1</Message>
            </Greeting>
        """.trimIndent()
        val actual = XmlSerializer.Default.decodeFromString<Greeting>(xml)
        assertEquals(
            Greeting(
                message = listOf("Hi1"),
                subGreeting = SubGreeting("hi")
            ),
            actual
        )
    }

    @Test
    fun skipsWhitespaceBeforeXml() {
        val xml = """
                <?xml version="1.1"?>
            <Greeting>
            <SubGreeting>
            <Message>hi</Message>
            </SubGreeting>
            <Message>Hi1</Message>
            </Greeting>
        """.trimIndent()
        val actual = XmlSerializer.Default.decodeFromString<Greeting>(xml)
        assertEquals(
            Greeting(
                message = listOf("Hi1"),
                subGreeting = SubGreeting("hi")
            ),
            actual
        )
    }

    @Test
    fun complexXml() {
        val xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <ListBucketResult xmlns="http://doc.oss-cn-hangzhou.aliyuncs.com">
            <Name>examplebucket</Name>
            <Prefix>1</Prefix>
            <MaxKeys>100</MaxKeys>
            <EncodingType>url</EncodingType>
            <IsTruncated>false</IsTruncated>
            <Contents>
            <Key>a</Key>
            <LastModified>2020-05-18T05:45:43.000Z</LastModified>
            <ETag>"35A27C2B9EAEEB6F48FD7FB5861D****"</ETag>
            <Size>25</Size>
            <StorageClass>STANDARD</StorageClass>
            </Contents>
            <Contents>
            <Key>a/b</Key>
            <LastModified>2020-05-18T05:45:47.000Z</LastModified>
            <ETag>"35A27C2B9EAEEB6F48FD7FB5861D****"</ETag>
            <Size>25</Size>
            <StorageClass>STANDARD</StorageClass>
            </Contents>
            <KeyCount>2</KeyCount>
            <OptionalFields>
            <Field>Size</Field>
            <Field>LastModifiedDate</Field>
            <Field>ETag</Field>
            <Field>StorageClass</Field>
            <Field>IsMultipartUploaded</Field>
            <Field>EncryptionStatus</Field>
            </OptionalFields>
            </ListBucketResult>
        """.trimIndent()
        val actual = XmlSerializer.Default.decodeFromString<ListBucketResult>(xml)
        assertEquals(
            ListBucketResult(
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
                        temp = null
                    ),
                    Contents(
                        key = "a/b",
                        size = 25,
                        lastModified = "2020-05-18T05:45:47.000Z",
                        eTag = "\"35A27C2B9EAEEB6F48FD7FB5861D****\"",
                        storageClass = "STANDARD",
                        temp = null
                    )
                ),
                optionalFields = OptionalFields(
                    listOf(
                        "Size",
                        "LastModifiedDate",
                        "ETag",
                        "StorageClass",
                        "IsMultipartUploaded",
                        "EncryptionStatus"
                    ),
                    null
                )
            ),
            actual
        )
    }

    @Test
    fun basicXmlWithNullableElement() {
        @Serializable
        @SerialName("SubBox")
        data class SubBox(
            @XmlElement("Message") val message: String? = null
        )

        @Serializable
        @SerialName("Box")
        @XmlRoot
        data class Box(
            @XmlElement("SubBox") val subBox: SubBox? = null,
            @XmlElement("Message") val message: List<String>? = null
        )

        var xml = """
            <Box>
            <SubBox>
            <Message>hi</Message>
            </SubBox>
            <Message>hi1</Message>
            <Message>hi2</Message>
            </Box>
        """.trimIndent().replace("\n", "")
        var actual = XmlSerializer.Default.decodeFromString<Box>(xml)
        assertEquals("hi", actual.subBox?.message)
        assertEquals(2, actual.message?.size)
        assertEquals("hi1", actual.message?.get(0))
        assertEquals("hi2", actual.message?.get(1))

        xml = """
            <Box>
            <SubBox>
            </SubBox>
            <Message>hi1</Message>
            <Message>hi2</Message>
            </Box>
        """.trimIndent().replace("\n", "")
        actual = XmlSerializer.Default.decodeFromString<Box>(xml)
        assertNotNull(actual.subBox)
        assertNull(actual.subBox.message)
        assertEquals(2, actual.message?.size)
        assertEquals("hi1", actual.message?.get(0))
        assertEquals("hi2", actual.message?.get(1))


        xml = """
            <Box>
            <Message>hi1</Message>
            <Message>hi2</Message>
            </Box>
        """.trimIndent().replace("\n", "")
        actual = XmlSerializer.Default.decodeFromString<Box>(xml)
        assertNull(actual.subBox)
        assertEquals(2, actual.message?.size)
        assertEquals("hi1", actual.message?.get(0))
        assertEquals("hi2", actual.message?.get(1))

        xml = """
            <Box>
            <SubBox>
            <Message>hi</Message>
            </SubBox>
            </Box>
        """.trimIndent().replace("\n", "")
        actual = XmlSerializer.Default.decodeFromString<Box>(xml)
        assertEquals("hi", actual.subBox?.message)
    }
}
