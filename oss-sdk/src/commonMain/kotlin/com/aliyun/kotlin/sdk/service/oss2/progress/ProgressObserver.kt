package com.aliyun.kotlin.sdk.service.oss2.progress

import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver

public class ProgressObserver(
    private val listener: ProgressListener,
    total: Long? = -1,
) : StreamObserver() {

    private val total: Long = total ?: -1
    private var written: Long = 0
    private var lastWritten: Long = 0 // last written

    private fun notify(inc: Long) {
        if (this.written > this.lastWritten) {
            this.listener.onProgress(inc, this.written, this.total)
        }
    }

    public override fun data(buffer: ByteArray, offset: Int, byteCount: Int) {
        this.written += byteCount.toLong()
        notify(byteCount.toLong())
    }

    public override fun finished() {
        this.listener.onFinish()
    }

    public override fun reset() {
        this.lastWritten = this.written
        this.written = 0
    }
}
