package com.aliyun.kotlin.sdk.service.oss2.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteArrayContentTest {
    @Test
    fun itCanBeConsumedAsByteStream() {
        val actual = byteArrayOf(0x01, 0x02, 0x03)
        val content = ByteArrayContent(actual)
        assertEquals(3, content.contentLength)
        assertEquals(0x02, content.bytes()[1])

        // test companion object
        val stream = ByteStream.fromBytes(actual)
        assertTrue(stream is ByteStream.Buffer)
    }
}
