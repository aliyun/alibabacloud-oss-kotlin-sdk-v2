package com.aliyun.kotlin.sdk.service.oss2.hash

import com.aliyun.kotlin.sdk.service.oss2.utils.HexUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha256Test {
    private fun assertSha256HexEqual(input: String, sha: String, repeat: Int = 1) {
        val expected = sha.replace(" ", "")
        val hash = Sha256()
        val chunk = input.encodeToByteArray()
        for (i in 0 until repeat) {
            hash.update(chunk, 0, chunk.size)
        }
        val actual = HexUtils.encodeHex(hash.digest())
        assertEquals(expected, actual)
    }

    @Test
    fun testVector1() {
        val input = "abc"
        val expected = "ba7816bf 8f01cfea 414140de 5dae2223 b00361a3 96177a9c b410ff61 f20015ad"
        assertSha256HexEqual(input, expected)
    }

    @Test
    fun testVector2() {
        val input = ""
        val expected = "e3b0c442 98fc1c14 9afbf4c8 996fb924 27ae41e4 649b934c a495991b 7852b855"
        assertSha256HexEqual(input, expected)
    }

    @Test
    fun testVector3() {
        val input = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"
        val expected = "248d6a61 d20638b8 e5c02693 0c3e6039 a33ce459 64ff2167 f6ecedd4 19db06c1"
        assertSha256HexEqual(input, expected)
    }

    @Test
    fun testVector4() {
        val input = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu"
        val expected = "cf5b16a7 78af8380 036ce59e 7b049237 0b249b11 e8f07a51 afac4503 7afee9d1"
        assertSha256HexEqual(input, expected)
    }

    @Test
    fun testVector5() {
        val input = "a"
        val expected = "cdc76e5c 9914fb92 81a1c7e2 84d73e67 f1809a48 a497200e 046d39cc c7112cd0"
        assertSha256HexEqual(input, expected, 1_000_000)
    }

    @Test
    fun testVector6() {
        val input = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno"
        val expected = "50e72a0e 26442fe2 552dc393 8ac58658 228c0cbf b1d2ca87 2ae43526 6fcd055e"
        assertSha256HexEqual(input, expected, 16_777_216)
    }

    @Test
    fun testPangramSha256() {
        val PANGRAM = "The quick brown fox jumps over the lazy dog"
        assertEquals(
            "f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8",
            toHMacHex("key", PANGRAM)
        )
        assertEquals(
            "5597b93a2843078cbb0c920ae41dfe20f1685e10c67e423c11ab91adfc319d12",
            toHMacHex(PANGRAM.repeat(2), "message")
        )
    }

    @Test
    fun testRfc4231Case1() {
        assertEquals(
            "b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7",
            toHMacHex(bytes(20) { 0x0b }, "Hi There"),
        )
    }

    @Test
    fun testRfc4231Case2() {
        assertEquals(
            "5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843",
            toHMacHex("Jefe", "what do ya want for nothing?"),
        )
    }

    @Test
    fun testRfc4231Case3() {
        assertEquals(
            "773ea91e36800e46854db8ebd09181a72959098b3ef8c122d9635514ced565fe",
            toHMacHex(bytes(20) { 0xaa }, bytes(50) { 0xdd }),
        )
    }

    @Test
    fun testRfc4231Case4() {
        assertEquals(
            "82558a389a443c0ea4cc819899f2083a85f0faa3e578f8077a2e3ff46729665b",
            toHMacHex(bytes(25) { it + 1 }, bytes(50) { 0xcd }),
        )
    }

    @Test
    fun testRfc4231Case6() {
        assertEquals(
            "60e431591ee0b67f0d8a26aacbf5b77f8e0bc6213728c5140546040f0ee37f54",
            toHMacHex(bytes(131) { 0xaa }, "Test Using Larger Than Block-Size Key - Hash Key First"),
        )
    }

    @Test
    fun testRfc4231Case7() {
        val payload = "This is a test using a larger than block-size key and a larger than block-size data. The key " +
            "needs to be hashed before being used by the HMAC algorithm."
        assertEquals(
            "9b09ffa71b942fcb27635fbcd5b0e944bfdc63644f0713938a7f51535c3a35e2",
            toHMacHex(bytes(131) { 0xaa }, payload),
        )
    }

    private fun toHMacHex(key: String, message: String): String {
        return message.encodeToByteArray().hmacSha256(key.encodeToByteArray()).toHexString()
    }

    private fun toHMacHex(key: ByteArray, message: String): String {
        return message.encodeToByteArray().hmacSha256(key).toHexString()
    }

    private fun toHMacHex(key: ByteArray, message: ByteArray): String {
        return message.hmacSha256(key).toHexString()
    }

    private fun bytes(length: Int, values: (Int) -> Int): ByteArray =
        ByteArray(length) { values(it).toByte() }
}
