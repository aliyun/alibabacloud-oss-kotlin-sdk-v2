package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.modules.SerializersModule
import java.security.InvalidParameterException

internal abstract class StreamingXmlDecoder(
    override val serializersModule: SerializersModule
) : Decoder {
    @ExperimentalSerializationApi
    override fun decodeNotNullMark(): Boolean {
        TODO("Not yet implemented")
    }

    @ExperimentalSerializationApi
    override fun decodeNull(): Nothing? {
        TODO("Not yet implemented")
    }

    override fun decodeBoolean(): Boolean {
        return decodeString().toBoolean()
    }

    override fun decodeByte(): Byte {
        return decodeString().toByte()
    }

    override fun decodeShort(): Short {
        return decodeString().toShort()
    }

    override fun decodeChar(): Char {
        return decodeString().toCharArray()[0]
    }

    override fun decodeInt(): Int {
        return decodeString().toInt()
    }

    override fun decodeLong(): Long {
        return decodeString().toLong()
    }

    override fun decodeFloat(): Float {
        return decodeString().toFloat()
    }

    override fun decodeDouble(): Double {
        return decodeString().toDouble()
    }

    override fun decodeString(): String {
        TODO("Not yet implemented")
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun decodeInline(descriptor: SerialDescriptor): Decoder {
        TODO("Not yet implemented")
    }
}

internal class StreamingXmlClassDecoder(
    private val node: XmlNode,
    override val serializersModule: SerializersModule,
) : StreamingXmlDecoder(serializersModule) {

    override fun decodeString(): String {
        return node.text ?: throw InvalidParameterException("")
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        return XmlClassDecoder(node, serializersModule, descriptor)
    }
}

internal class StreamingXmlListDecoder(
    private val nodes: List<XmlNode>,
    override val serializersModule: SerializersModule,
) : StreamingXmlDecoder(serializersModule) {

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        return XmlListDecoder(nodes, serializersModule)
    }
}
