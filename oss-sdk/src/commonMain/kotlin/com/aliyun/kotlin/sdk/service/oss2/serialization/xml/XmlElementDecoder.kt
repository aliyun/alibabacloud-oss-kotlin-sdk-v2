package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.modules.SerializersModule

internal abstract class XmlElementDecoder(
    override val serializersModule: SerializersModule
) : CompositeDecoder {

    override fun endStructure(descriptor: SerialDescriptor) {
        TODO("Not yet implemented")
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun decodeBooleanElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Boolean {
        return decodeStringElement(descriptor, index).toBoolean()
    }

    override fun decodeByteElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Byte {
        return decodeStringElement(descriptor, index).toByte()
    }

    override fun decodeCharElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Char {
        return decodeStringElement(descriptor, index).toCharArray()[0]
    }

    override fun decodeShortElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Short {
        return decodeStringElement(descriptor, index).toShort()
    }

    override fun decodeIntElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Int {
        return decodeStringElement(descriptor, index).toInt()
    }

    override fun decodeLongElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Long {
        return decodeStringElement(descriptor, index).toLong()
    }

    override fun decodeFloatElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Float {
        return decodeStringElement(descriptor, index).toFloat()
    }

    override fun decodeDoubleElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Double {
        return decodeStringElement(descriptor, index).toDouble()
    }

    override fun decodeInlineElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Decoder {
        TODO("Not yet implemented")
    }

    @ExperimentalSerializationApi
    override fun <T : Any> decodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T?>,
        previousValue: T?
    ): T? {
        TODO("Not yet implemented")
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return super.decodeCollectionSize(descriptor)
    }

    @ExperimentalSerializationApi
    override fun decodeSequentially(): Boolean {
        return super.decodeSequentially()
    }
}

internal open class XmlClassDecoder(
    val node: XmlNode,
    override val serializersModule: SerializersModule,
    descriptor: SerialDescriptor,
) : XmlElementDecoder(serializersModule) {

    val childrenNodes: MutableIterator<MutableMap.MutableEntry<String, MutableList<XmlNode>>> = node.children.entries.iterator()
    lateinit var childrenNode: MutableMap.MutableEntry<String, MutableList<XmlNode>>

    private val elementNames = (0 until descriptor.elementsCount).map { i ->
        descriptor.getElementAnnotations(i)
            .filterIsInstance<XmlElement>()
            .firstOrNull()
            ?.name ?: descriptor.getElementName(i)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        // no op
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (true) {
            if (!childrenNodes.hasNext()) {
                return DECODE_DONE
            }
            childrenNode = childrenNodes.next()
            val index = elementNames.indexOfFirst { it == childrenNode.key }
            return if (index == -1) {
                continue
            } else {
                index
            }
        }
    }

    override fun decodeStringElement(
        descriptor: SerialDescriptor,
        index: Int
    ): String {
        return childrenNode.value.first().text ?: throw IllegalArgumentException("Invalid string value")
    }

    override fun <T> decodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T>,
        previousValue: T?
    ): T {
        if (deserializer.descriptor.kind == StructureKind.LIST) {
            return deserializer.deserialize(StreamingXmlListDecoder(childrenNode.value, serializersModule))
        }
        return deserializer.deserialize(StreamingXmlClassDecoder(childrenNode.value.first(), serializersModule))
    }
}

internal class XmlListDecoder(
    val nodes: List<XmlNode>,
    override val serializersModule: SerializersModule,
) : XmlElementDecoder(serializersModule) {

    var index = 0

    override fun endStructure(descriptor: SerialDescriptor) {
        // no op
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (index >= nodes.size) {
            return DECODE_DONE
        }
        return index++
    }

    override fun decodeStringElement(
        descriptor: SerialDescriptor,
        index: Int
    ): String {
        TODO("Not yet implemented")
    }

    override fun <T> decodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T>,
        previousValue: T?
    ): T {
        return deserializer.deserialize(StreamingXmlClassDecoder(nodes[index], serializersModule))
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return super.decodeCollectionSize(descriptor)
    }

    @ExperimentalSerializationApi
    override fun decodeSequentially(): Boolean {
        return super.decodeSequentially()
    }
}
