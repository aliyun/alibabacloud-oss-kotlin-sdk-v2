package com.aliyun.kotlin.sdk.service.oss2.serialization.xml.dom

import com.aliyun.kotlin.sdk.service.oss2.exceptions.DeserializationException
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlStreamReader
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.XmlToken
import com.aliyun.kotlin.sdk.service.oss2.serialization.xml.xmlStreamReader

/**
 * Convenience type for treating a list like stack
 */
internal typealias ListStack<T> = MutableList<T>

/**
 * Push an item to top of stack
 */
internal fun <T> ListStack<T>.push(item: T): Boolean = add(item)

/**
 * Pop the top of the stack or throw [NoSuchElementException]
 */
internal fun <T> ListStack<T>.pop(): T = removeAt(lastIndex)

/**
 * Pop the top of the stack or return null if stack is empty
 */
internal fun <T> ListStack<T>.popOrNull(): T? = removeLastOrNull()

/**
 * Return top of stack or throws exception if stack is empty
 */
internal fun <T> ListStack<T>.top(): T = this[count() - 1]

/**
 * DOM representation of an XML document
 */
public class XmlNode {
    public val name: XmlToken.QualifiedName

    // child element name (local) -> children
    public val children: MutableMap<String, MutableList<XmlNode>> = linkedMapOf()
    public var text: String? = null
    public val attributes: MutableMap<XmlToken.QualifiedName, String> = linkedMapOf()

    // namespaces declared by this node
    public val namespaces: MutableList<XmlToken.Namespace> = mutableListOf()
    public var parent: XmlNode? = null

    public constructor(name: String) : this(XmlToken.QualifiedName(name))
    public constructor(name: XmlToken.QualifiedName) {
        this.name = name
    }

    override fun toString(): String = "XmlNode($name)"

    public companion object {

        public fun parse(xmlpayload: ByteArray): XmlNode {
            val reader = xmlStreamReader(xmlpayload)
            return parseDom(reader)
        }

        internal fun fromToken(token: XmlToken.BeginElement): XmlNode = XmlNode(token.name).apply {
            attributes.putAll(token.attributes)
            namespaces.addAll(token.nsDeclarations)
        }
    }

    public fun addChild(child: XmlNode) {
        val name = requireNotNull(child.name) { "child must have a name" }
        val childNodes = children.getOrPut(name.local) {
            mutableListOf()
        }
        childNodes.add(child)
    }

    internal operator fun XmlNode.unaryPlus() = addChild(this)
}

// parse a string into a dom representation
public fun parseDom(reader: XmlStreamReader): XmlNode {
    val nodeStack: ListStack<XmlNode> = mutableListOf()

    loop@while (true) {
        when (val token = reader.nextToken()) {
            is XmlToken.BeginElement -> {
                val newNode = XmlNode.fromToken(token)
                if (nodeStack.isNotEmpty()) {
                    val curr = nodeStack.top()
                    curr.addChild(newNode)
                    newNode.parent = curr
                }

                nodeStack.push(newNode)
            }
            is XmlToken.EndElement -> {
                val curr = nodeStack.top()

                if (curr.name != token.name) {
                    throw DeserializationException("expected end of element: `${curr.name}`, found: `${token.name}`")
                }

                if (nodeStack.count() > 1) {
                    // finished with this child node
                    nodeStack.pop()
                }
            }
            is XmlToken.Text -> {
                val curr = nodeStack.top()
                curr.text = token.value
            }
            null,
            is XmlToken.EndDocument,
            -> break@loop
            else -> continue // ignore unknown token types
        }
    }

    // root node should be all that is left
    check(nodeStack.count() == 1) { "invalid XML document, node stack size > 1" }
    return nodeStack.pop()
}

public fun XmlNode.toXmlString(pretty: Boolean = false): String {
    val sb = StringBuilder()
    formatXmlNode(this, 0, sb, pretty)
    return sb.toString()
}

internal fun formatXmlNode(curr: XmlNode, depth: Int, sb: StringBuilder, pretty: Boolean) {
    sb.apply {
        val indent = if (pretty) " ".repeat(depth * 4) else ""

        // open tag
        append("$indent<")
        append(curr.name.toString())
        curr.namespaces.forEach {
            // namespaces declared by this node
            append(" xmlns")
            if (it.prefix != null) {
                append(":${it.prefix}")
            }
            append("=\"${it.uri}\"")
        }

        // attributes
        if (curr.attributes.isNotEmpty()) append(" ")
        curr.attributes.forEach {
            append("${it.key}=\"${it.value}\"")
        }
        append(">")

        // text
        if (curr.text != null) append(curr.text)

        // children
        if (pretty && curr.children.isNotEmpty()) appendLine()
        curr.children.forEach {
            it.value.forEach { child ->
                formatXmlNode(child, depth + 1, sb, pretty)
            }
        }

        // end tag
        if (curr.children.isNotEmpty()) {
            append(indent)
        }

        append("</")
        append(curr.name.toString())
        append(">")

        if (pretty && depth > 0) appendLine()
    }
}
