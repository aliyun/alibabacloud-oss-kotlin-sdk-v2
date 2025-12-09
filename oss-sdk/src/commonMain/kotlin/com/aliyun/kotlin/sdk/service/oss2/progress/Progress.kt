package com.aliyun.kotlin.sdk.service.oss2.progress

public fun interface ProgressListener {
    public fun onProgress(bytesIncrement: Long, totalBytesTransferred: Long, totalBytesExpected: Long)
    public fun onFinish() {
        // noop
    }
}
