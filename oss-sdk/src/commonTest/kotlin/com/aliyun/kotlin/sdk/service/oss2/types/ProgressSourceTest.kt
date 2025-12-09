package com.aliyun.kotlin.sdk.service.oss2.types

import kotlin.test.Test

class ProgressSourceTest {

    @Test
    fun testProgressSource() {
        /*
        var totalBytesTransferred: Long = 0
        val source = ProgressSource(Buffer().also { it0 ->
            repeat(1024) {
                it0.write("Hello oss.".toByteArray())
            }
        }, 10240, ProgressClosure { bytesSent, totalBytesSent, totalBytesExpected ->
            totalBytesTransferred += bytesSent
            assertEquals(10240, totalBytesExpected)
        }).buffered()

        while (!source.exhausted()) {
            val bytes = source.readByteArray(10)
            assertEquals("Hello oss.", bytes.decodeToString())
        }
        assertEquals(10240, totalBytesTransferred)
         */
    }
}
