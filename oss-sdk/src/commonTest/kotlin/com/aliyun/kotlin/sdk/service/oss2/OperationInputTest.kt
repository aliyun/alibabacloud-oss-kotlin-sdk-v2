package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.AttributeKey
import com.aliyun.kotlin.sdk.service.oss2.types.get
import com.aliyun.kotlin.sdk.service.oss2.types.mutableAttributes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OperationInputTest {
    @Test
    fun buildOperationInputWithEmptyValues() {
        val input = OperationInput.build {}

        assertEquals("", input.opName)
        assertEquals("", input.method)
        assertTrue(input.headers.isEmpty())
        assertTrue(input.parameters.isEmpty())
        assertNull(input.bucket)
        assertNull(input.key)
        assertNotNull(input.opMetadata)
        assertEquals(0, input.opMetadata.keys.size)
    }

    @Test
    fun buildOperationInputWithValues() {
        var input = OperationInput.build {
            opName = "Operation"
            method = "PUT"
            bucket = "bucket"
            key = "key"
            headers = mutableMapOf()
            parameters = mutableMapOf()
            // TODO body
            opMetadata = mutableAttributes()
        }

        assertEquals("Operation", input.opName)
        assertEquals("PUT", input.method)
        assertEquals("bucket", input.bucket)
        assertEquals("key", input.key)
        assertTrue(input.headers.isEmpty())
        assertTrue(input.parameters.isEmpty())
        assertNotNull(input.opMetadata)
        assertEquals(0, input.opMetadata.keys.size)

        // all have values
        val opMeta = mutableAttributes()
        opMeta[STRING_KEY] = "hello world"

        input = OperationInput.build {
            opName = "Operation"
            method = "GET"
            bucket = "bucket"
            key = "key"
            headers = mutableMapOf("header" to "h-str-value")
            parameters = mutableMapOf("param" to "p-str-value")
            // TODO body
            opMetadata = opMeta
        }

        assertEquals("Operation", input.opName)
        assertEquals("GET", input.method)
        assertEquals("bucket", input.bucket)
        assertEquals("key", input.key)
        assertEquals(1, input.headers.size)
        assertEquals("h-str-value", input.headers["header"])
        assertEquals(1, input.parameters.size)
        assertEquals("p-str-value", input.parameters["param"])
        assertEquals(1, input.opMetadata.keys.size)
        assertEquals("hello world", input.opMetadata[STRING_KEY])
    }

    @Test
    fun buildAndUpdateOperationInput() {
        val input = OperationInput.build {}

        assertEquals("", input.opName)
        assertEquals("", input.method)
        assertTrue(input.headers.isEmpty())
        assertTrue(input.parameters.isEmpty())
        assertNull(input.bucket)
        assertNull(input.key)
        assertEquals(0, input.opMetadata.keys.size)

        // update header parameters, opMetadata
        input.headers.put("header", "h-str-value")
        input.parameters.put("param", "p-str-value")
        input.opMetadata[STRING_KEY] = "hello world"

        assertEquals(1, input.headers.size)
        assertEquals("h-str-value", input.headers["header"])
        assertEquals(1, input.parameters.size)
        assertEquals("p-str-value", input.parameters["param"])
        assertEquals(1, input.opMetadata.keys.size)
        assertEquals("hello world", input.opMetadata[STRING_KEY])
    }

    companion object {
        private val STRING_KEY: AttributeKey<String> = AttributeKey<String>("STRING_KEY")
    }
}
