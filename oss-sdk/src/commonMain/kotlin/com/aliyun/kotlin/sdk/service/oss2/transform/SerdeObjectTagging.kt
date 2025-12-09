package com.aliyun.kotlin.sdk.service.oss2.transform

import com.aliyun.kotlin.sdk.service.oss2.models.*
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import com.aliyun.kotlin.sdk.service.oss2.transform.SerdeUtils.deserializeXmlBody
import com.aliyun.kotlin.sdk.service.oss2.types.ByteStream

internal fun toXmlTagging(value: Tagging): ByteStream {
    val root = XmlNode("Tagging")
    value.tagSet?.let { it0 ->
        val tagSet = XmlNode("TagSet")
        it0.tags?.forEach { it1 ->
            val tag = XmlNode("Tag")
            it1.key?.let { tag.addChild(XmlNode("Key").apply { text = it }) }
            it1.value?.let { tag.addChild(XmlNode("Value").apply { text = it }) }
            tagSet.addChild(tag)
        }
        root.addChild(tagSet)
    }

    return ByteStream.fromString(root.toXmlString())
}

internal fun fromXmlTagging(data: ByteArray?): Tagging {
    val root = deserializeXmlBody(data, "Tagging")
    val result = Tagging.Builder()
    root.children.forEach { it0 ->
        when (it0.key) {
            "TagSet" -> {
                val tagSet = TagSet.Builder()
                it0.value.first().children.forEach { it1 ->
                    when (it1.key) {
                        "Tag" -> {
                            val tags = mutableListOf<Tag>()
                            it1.value.forEach { it2 ->
                                val tag = Tag.Builder()
                                it2.children.forEach { it3 ->
                                    when (it3.key) {
                                        "Key" -> {
                                            tag.key = it3.value.first().text
                                        }
                                        "Value" -> {
                                            tag.value = it3.value.first().text
                                        }
                                    }
                                }
                                tags.add(tag.build())
                            }
                            tagSet.tags = tags
                        }
                    }
                }
                result.tagSet = tagSet.build()
            }
        }
    }

    return result.build()
}
