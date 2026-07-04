package com.aliyun.kotlin.sdk.service.oss2.hash

import kotlin.random.Random
import kotlin.test.Test

class Crc64PerfTest {
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
}
