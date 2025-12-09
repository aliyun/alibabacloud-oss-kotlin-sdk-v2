package com.aliyun.kotlin.sdk.service.oss2

import com.aliyun.kotlin.sdk.service.oss2.types.AttributeKey
import com.aliyun.kotlin.sdk.service.oss2.types.get
import com.aliyun.kotlin.sdk.service.oss2.types.mutableAttributes
import kotlin.test.*

class OperationOutputTest {
    @Test
    fun buildOperationOutputWithEmptyValues() {
        val output = OperationOutput.build {}

        assertEquals("", output.status)
        assertEquals(0, output.statusCode)
        assertTrue(output.headers.isEmpty())
        assertNull(output.input)
        assertNull(output.opMetadata)
    }

    @Test
    fun buildOperationInputWithValues() {
        var output = OperationOutput.build {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf()
            input = OperationInput.build {}
            // TODO body
            opMetadata = mutableAttributes()
        }

        assertEquals("OK", output.status)
        assertEquals(200, output.statusCode)
        assertTrue(output.headers.isEmpty())
        assertNotNull(output.input)
        assertEquals(0, output.opMetadata!!.keys.size)

        // all have values
        val opMeta = mutableAttributes()
        opMeta.set(STRING_KEY, "hello world")

        output = OperationOutput.build {
            status = "OK"
            statusCode = 200
            headers = mutableMapOf()
            input = OperationInput.build {}
            headers = mutableMapOf("header" to "h-str-value")
            // TODO body
            opMetadata = opMeta
        }

        assertEquals("OK", output.status)
        assertEquals(200, output.statusCode)
        assertEquals(1, output.headers.size)
        assertEquals("h-str-value", output.headers["header"])
        assertEquals(1, output.opMetadata!!.keys.size)
        assertEquals("hello world", output.opMetadata[STRING_KEY])
    }

    @Test
    fun buildAndUpdateOperationInput() {
        val output = OperationOutput.build {
            opMetadata = mutableAttributes()
        }

        assertEquals("", output.status)
        assertEquals(0, output.statusCode)
        assertTrue(output.headers.isEmpty())
        assertNull(output.input)
        assertEquals(0, output.opMetadata!!.keys.size)

        // update header parameters, opMetadata
        output.headers.put("header", "h-str-value")
        output.opMetadata.set(STRING_KEY, "hello world")

        assertEquals(1, output.headers.size)
        assertEquals("h-str-value", output.headers["header"])
        assertEquals(1, output.opMetadata.keys.size)
        assertEquals("hello world", output.opMetadata[STRING_KEY])
    }

    companion object {
        private val STRING_KEY: AttributeKey<String> = AttributeKey<String>("STRING_KEY")
    }
}
