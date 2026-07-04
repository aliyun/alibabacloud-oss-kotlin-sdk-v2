package com.aliyun.kotlin.sdk.service.oss2.hash

import com.aliyun.kotlin.sdk.service.oss2.utils.HexUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha1StressTest {
    @Test
    fun testVector6() {
        val input = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno"
        val expected = "7789f0c9 ef7bfc40 d9331114 3dfbe69e 2017f592".replace(" ", "")
        val hash = Sha1()
        val chunk = input.encodeToByteArray()
        for (i in 0 until 16_777_216) {
            hash.update(chunk, 0, chunk.size)
        }
        assertEquals(expected, HexUtils.encodeHex(hash.digest()))
    }
}
