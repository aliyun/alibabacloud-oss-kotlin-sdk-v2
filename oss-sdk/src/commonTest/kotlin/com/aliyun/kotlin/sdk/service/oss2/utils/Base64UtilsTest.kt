package com.aliyun.kotlin.sdk.service.oss2.utils

import com.aliyun.kotlin.sdk.service.oss2.utils.Base64Utils.decode
import kotlin.test.*

class Base64UtilsTest {
    @Test
    fun testVectorsPerRfc4648() {
        val testVectors = arrayOf("", "f", "fo", "foo", "foob", "fooba", "foobar")
        val expected = arrayOf("", "Zg==", "Zm8=", "Zm9v", "Zm9vYg==", "Zm9vYmE=", "Zm9vYmFy")

        for (i in testVectors.indices) {
            val data = testVectors[i]
            val source = data.toByteArray()
            val b64encoded = Base64Utils.encodeToString(data.toByteArray())
            assertEquals(expected[i], b64encoded)
            val b64 = b64encoded.toByteArray()

            val decoded = decode(b64)
            assertContentEquals(source, decoded)
        }
    }
}
