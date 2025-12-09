package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.models.Tag
import com.aliyun.kotlin.sdk.service.oss2.models.TagSet
import com.aliyun.kotlin.sdk.service.oss2.models.Tagging
import com.aliyun.kotlin.sdk.service.oss2.types.toByteArray
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SerdeObjectTaggingTest {

    @Test
    fun testToXmlTagging() {
        var tagging = Tagging.Builder().build()
        runBlocking {
            assertEquals(
                String(toXmlTagging(tagging).toByteArray()),
                "<Tagging></Tagging>"
            )
        }

        val xml = "<Tagging><TagSet><Tag><Key>a</Key><Value>1</Value></Tag><Tag><Key>b</Key><Value>2</Value></Tag></TagSet></Tagging>"
        tagging = Tagging.Builder().apply {
            tagSet = TagSet.Builder().apply {
                tags = listOf(
                    Tag.Builder().apply {
                        key = "a"
                        value = "1"
                    }.build(),
                    Tag.Builder().apply {
                        key = "b"
                        value = "2"
                    }.build()
                )
            }.build()
        }.build()
        runBlocking {
            assertEquals(
                String(toXmlTagging(tagging).toByteArray()),
                xml
            )
        }
    }

    @Test
    fun testFromXmlTagging() {
        // body is null
        assertFailsWith<DeserializationException> { fromXmlTagging(null) }

        // body is unexpected
        assertFailsWith<DeserializationException> { fromXmlTagging("<a></a>".toByteArray()) }

        // normal
        val xml = """
            <Tagging>
            <TagSet>
            <Tag>
            <Key>a</Key>
            <Value>1</Value>
            </Tag>
            <Tag>
            <Key>b</Key>
            <Value>2</Value>
            </Tag>
            </TagSet>
            </Tagging>
        """.trimIndent()
        val result = fromXmlTagging(xml.toByteArray())
        assertEquals(2, result.tagSet?.tags?.size)
        assertEquals("a", result.tagSet?.tags?.first()?.key)
        assertEquals("1", result.tagSet?.tags?.first()?.value)
        assertEquals("b", result.tagSet?.tags?.last()?.key)
        assertEquals("2", result.tagSet?.tags?.last()?.value)
    }
}
