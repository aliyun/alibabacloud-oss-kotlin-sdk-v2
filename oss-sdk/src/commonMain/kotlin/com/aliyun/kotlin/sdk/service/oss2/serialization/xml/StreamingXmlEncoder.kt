package com.aliyun.kotlin.sdk.service.oss2.serialization.xml

import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.ListStack
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.XmlNode
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom.push
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule

internal abstract class StreamingXmlEncoder(
    override val serializersModule: SerializersModule
) : CompositeEncoder, kotlinx.serialization.encoding.Encoder {

    override fun endStructure(descriptor: SerialDescriptor) {
    }

    override fun encodeBooleanElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Boolean
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeByteElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Byte
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeShortElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Short
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeCharElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Char
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeIntElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Int
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeLongElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Long
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeFloatElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Float
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeDoubleElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: Double
    ) {
        encodeStringElement(descriptor, index, value.toString())
    }

    override fun encodeInlineElement(
        descriptor: SerialDescriptor,
        index: Int
    ): kotlinx.serialization.encoding.Encoder {
        TODO("Not yet implemented")
    }

    @ExperimentalSerializationApi
    override fun <T : Any> encodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T?
    ) {
        value?.let {
            when (it) {
                is Boolean -> encodeBooleanElement(descriptor, index, it)
                is Byte -> encodeByteElement(descriptor, index, it)
                is Short -> encodeShortElement(descriptor, index, it)
                is Char -> encodeCharElement(descriptor, index, it)
                is Int -> encodeIntElement(descriptor, index, it)
                is Long -> encodeLongElement(descriptor, index, it)
                is Float -> encodeFloatElement(descriptor, index, it)
                is Double -> encodeDoubleElement(descriptor, index, it)
                is String -> encodeStringElement(descriptor, index, it)
                else -> encodeSerializableElement(descriptor, index, serializer, it)
            }
        }
    }

    @ExperimentalSerializationApi
    override fun encodeNull() {
    }

    override fun encodeBoolean(value: Boolean) {
        encodeString(value.toString())
    }

    override fun encodeByte(value: Byte) {
        encodeString(value.toString())
    }

    override fun encodeShort(value: Short) {
        encodeString(value.toString())
    }

    override fun encodeChar(value: Char) {
        encodeString(value.toString())
    }

    override fun encodeInt(value: Int) {
        encodeString(value.toString())
    }

    override fun encodeLong(value: Long) {
        encodeString(value.toString())
    }

    override fun encodeFloat(value: Float) {
        encodeString(value.toString())
    }

    override fun encodeDouble(value: Double) {
        encodeString(value.toString())
    }

    override fun encodeEnum(
        enumDescriptor: SerialDescriptor,
        index: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun encodeInline(descriptor: SerialDescriptor): kotlinx.serialization.encoding.Encoder {
        TODO("Not yet implemented")
    }
}

internal class ClassStreamingXmlEncoder(
    var rootNode: XmlNode? = null,
    serializersModule: SerializersModule
) : StreamingXmlEncoder(serializersModule) {

    var node: XmlNode? = rootNode
    var stack: ListStack<XmlNode> = mutableListOf<XmlNode>()

    override fun <T> encodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T
    ) {
        stack = mutableListOf<XmlNode>()
        val annotations = descriptor.getElementAnnotations(index).filterIsInstance<XmlElement>()
        annotations.firstOrNull()?.name?.let {
            when (value) {
                is List<*> -> {
                    value.forEach { _ ->
                        val node = XmlNode(it)
                        rootNode?.addChild(node)
                        stack.push(node)
                    }
                }

                else -> {
                    val node = XmlNode(it)
                    rootNode?.addChild(node)
                    this.node = node
                }
            }
            encodeSerializableValue(serializer, value)
            this.node = rootNode
        }
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        if (descriptor.kind != StructureKind.LIST) {
            if (descriptor.annotations.any { it is XmlRoot }) {
                rootNode = XmlNode(descriptor.serialName)
                this.node = rootNode
            }
            return ClassStreamingXmlEncoder(node, serializersModule)
        } else {
            return ListStreamingXmlEncoder(stack, serializersModule)
        }
    }

    override fun encodeString(value: String) {
        node?.text = value
    }

    override fun encodeStringElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: String
    ) {
        val annotations = descriptor.getElementAnnotations(index).filterIsInstance<XmlElement>()
        annotations.firstOrNull()?.name?.let {
            val node = XmlNode(it)
            node.text = value
            this.node?.addChild(node)
        }
    }
}

internal class ListStreamingXmlEncoder(
    var nodes: List<XmlNode>,
    serializersModule: SerializersModule
) : StreamingXmlEncoder(serializersModule) {

    var node: XmlNode? = null

    override fun <T> encodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T
    ) {
        node = nodes[index]
        encodeSerializableValue(serializer, value)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return ClassStreamingXmlEncoder(node, serializersModule)
    }

    override fun encodeString(value: String) {
        node?.text = value
    }

    override fun encodeStringElement(
        descriptor: SerialDescriptor,
        index: Int,
        value: String
    ) {
        nodes[index].text = value
    }
}
