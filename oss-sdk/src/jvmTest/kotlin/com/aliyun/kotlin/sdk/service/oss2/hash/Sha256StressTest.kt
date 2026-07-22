package com.aliyun.kotlin.sdk.service.oss2.hash

import com.aliyun.kotlin.sdk.service.oss2.utils.HexUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha256StressTest {
    @Test
    fun testVector6() {
        val input = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno"
        val expected = "50e72a0e 26442fe2 552dc393 8ac58658 228c0cbf b1d2ca87 2ae43526 6fcd055e".replace(" ", "")
        val hash = Sha256()
        val chunk = input.encodeToByteArray()
        for (i in 0 until 16_777_216) {
            hash.update(chunk, 0, chunk.size)
        }
        assertEquals(expected, HexUtils.encodeHex(hash.digest()))
    }
}
