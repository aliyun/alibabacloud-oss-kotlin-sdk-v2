package com.aliyun.kotlin.sdk.service.oss2.progress

import kotlin.test.Test

class ProgressTest {

    @Test
    fun testProgressWithRetry() {
        /*
        var lTotalBytesTransferred: Long = 0
        val progress =
            ProgressClosure { bytesIncrement, totalBytesTransferred, totalBytesExpected ->
                assertTrue(lTotalBytesTransferred < totalBytesTransferred)
                lTotalBytesTransferred = totalBytesTransferred
            }

        val prog = RetryProgressListener(progress)

        for (bytesCount in 0..50 step 5) {
            prog.onProgress(5, bytesCount.toLong(), 100)
        }

        for (bytesCount in 0..100 step 5) {
            prog.onProgress(5, bytesCount.toLong(), 100)
        }

         */
    }
}
