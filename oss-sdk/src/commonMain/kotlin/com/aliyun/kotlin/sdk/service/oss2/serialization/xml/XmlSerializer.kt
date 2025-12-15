package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.toXmlString
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

public sealed class XmlSerializer(override val serializersModule: SerializersModule) : StringFormat {

    public companion object Default : XmlSerializer(EmptySerializersModule())

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
        val encoder = ClassStreamingXmlEncoder(null, serializersModule)
        serializer.serialize(encoder, value)
        val node = encoder.rootNode!!
        return node.toXmlString()
    }

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        val node = XmlNode.parse(string.toByteArray())
        val input = StreamingXmlClassDecoder(node, serializersModule)
        return input.decodeSerializableValue(deserializer)
    }
}
