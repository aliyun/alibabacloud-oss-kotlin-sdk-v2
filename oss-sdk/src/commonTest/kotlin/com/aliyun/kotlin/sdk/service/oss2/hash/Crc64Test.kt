package com.aliyun.kotlin.sdk.service.oss2.hash

import java.lang.String
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long
import kotlin.RuntimeException
import kotlin.compareTo
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.text.format

class Crc64Test {

    @Test
    fun testCrc64() {
        val data1 = "123456789"
        val data2 = "This is a test of the emergency broadcast system."

        var crc64 = Crc64(0)
        crc64.update(data1.toByteArray(), 0, data1.length)
        var pat = "-7395533204333446662".toLong()
        assertEquals(pat, crc64.digestValue)

        crc64 = Crc64(data2.toByteArray(), data2.length)
        pat = "2871916124362751090".toLong()
        assertEquals(pat, crc64.digestValue)

        crc64.reset()
        crc64.update(data1.toByteArray(), 0, data1.length)
        pat = "-7395533204333446662".toLong()
        assertEquals(pat, crc64.digestValue)

        val init = byteArrayOf(0, 0, 0, 0)
        crc64 = Crc64.fromBytes(init)
        assertEquals(0, crc64.digestValue)

        val total = data1 + data2
        val crc1: Crc64 = Crc64()
        crc1.update(data1.toByteArray(), 0, data1.length)

        val crc2: Crc64 = Crc64()
        crc2.update(data2.toByteArray(), 0, data2.length)

        val crc3: Crc64 = Crc64()
        crc3.update(total.toByteArray(), 0, total.length)

        val crc4: Crc64 = crc1.combine(crc2, data2.length.toLong())
        assertEquals(crc3.digestValue, crc4.digestValue)

        val crc5: Crc64 = crc1.combine(crc2, 0)
        assertEquals(crc1.digestValue, crc5.digestValue)

        assertEquals(2, 2.toLong().combine(3, 0))

        assertTrue(crc4.digest().isNotEmpty())
    }

    @Test
    fun testCrc64AndReset() {
        val data1 = "123456789"
        val data2 = "This is a test of the emergency broadcast system."
        val data = data1 + data2

        // total crc
        val crc: Crc64 = Crc64()
        crc.update(data.toByteArray(), 0, data.length)
        val pat = "7672622622872733320".toLong()
        val crcValue: Long = crc.digestValue
        assertEquals(pat, crcValue)

        // crc 1
        val crc1: Crc64 = Crc64()
        crc1.update(data1.toByteArray(), 0, data1.length)
        val crcValue1: Long = crc1.digestValue
        val pat1 = "-7395533204333446662".toLong()
        assertEquals(pat1, crcValue1)

        // crc 2
        val crc2: Crc64 = Crc64(crcValue1)
        crc2.update(data2.toByteArray(), 0, data2.length)
        val crcValue2: Long = crc2.digestValue
        assertEquals(crcValue, crcValue2)

        crc2.reset()
        crc2.update(data2.toByteArray(), 0, data2.length)
        val crcValue3: Long = crc2.digestValue
        assertEquals(crcValue, crcValue3)
    }

    @Test
    fun testBytes() {
        val test1 = "123456789".toByteArray()
        val tesLen1 = 9
        val testCrc1 = -0x66a2364420e6c606L // ECMA.
        calcAndCheck(test1, tesLen1, testCrc1)

        val test2 = "This is a test of the emergency broadcast system.".toByteArray()
        val tesLen2 = 49
        val testCrc2 = 0x27db187fc15bbc72L // ECMA.
        calcAndCheck(test2, tesLen2, testCrc2)

        val test3 = "IHATEMATH".toByteArray()
        val tesLen3 = 9
        val testCrc3 = 0x3920e0f66b6ee0c8L // ECMA.
        calcAndCheck(test3, tesLen3, testCrc3)
    }

    @Test
    fun testPerformance() {
        val b = Random.nextBytes(65536)

        // warmup
        var crc: Crc64 = Crc64()
        crc.update(b, 0, b.size)

        // start bench
        var bytes: Long = 0
        val start = System.currentTimeMillis()
        crc = Crc64()
        for (i in 0..99999) {
            crc.update(b, 0, b.size)
            bytes += b.size.toLong()
        }

        var duration = System.currentTimeMillis() - start
        duration = if (duration == 0L) 1 else duration // div0
        val bytesPerSec = (bytes / duration) * 1000

        println(
            (bytes / 1024 / 1024).toString() + " MB processed in " + duration + " ms @ " + bytesPerSec / 1024 / 1024 +
                " MB/s"
        )
    }

    @Test
    fun testUpdateAndReset() {
        val crc: Crc64 = Crc64()

        val test1 = "123456789".toByteArray()
        val testLen1 = 9
        val testCrc1 = -0x66a2364420e6c606L // ECMA.

        crc.update(test1, 0, testLen1)

        assertEquals(testCrc1, crc.digestValue)

        crc.reset()

        assertEquals(0, crc.digestValue)

        val test2 = "This is a test of the emergency broadcast system.".toByteArray()
        val testLen2 = 49
        val testCrc2 = 0x27db187fc15bbc72L // ECMA.

        crc.update(test2, 0, testLen2)

        assertEquals(testCrc2, crc.digestValue)
    }

    private fun calcAndCheck(b: ByteArray, len: Int, crcValue: Long) {
        /* Test Crc64 default calculation. */

        var crc: Crc64 = Crc64(b, len)
        if (crc.digestValue != crcValue) {
            throw RuntimeException(
                (
                    "mismatch: " + String.format("%016x", crc.digestValue) + " should be " +
                        kotlin.String.format("%016x", crcValue)
                    )
            )
        }

        /* test combine() */
        val crc1: Crc64 = Crc64(b, (len + 1) ushr 1)
        val crc2: Crc64 = Crc64(b.copyOfRange(((len + 1) ushr 1), b.size), len ushr 1)
        crc = crc1.combine(crc2, (len ushr 1).toLong())

        if (crc.digestValue != crcValue) {
            throw RuntimeException(
                (
                    "mismatch: " + String.format("%016x", crc.digestValue) + " should be " +
                        kotlin.String.format("%016x", crcValue)
                    )
            )
        }
    }
}
