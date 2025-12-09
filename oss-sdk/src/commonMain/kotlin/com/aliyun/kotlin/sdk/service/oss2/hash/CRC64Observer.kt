package com.aliyun.kotlin.sdk.service.oss2.hash

import com.aliyun.kotlin.sdk.service.oss2.types.StreamObserver

internal class CRC64Observer(
    value: Long = 0
) : StreamObserver() {
    val checksum: Crc64 = Crc64(value)

    override fun data(buffer: ByteArray, offset: Int, byteCount: Int) {
        checksum.update(buffer, offset, byteCount)
    }

    override fun reset() {
        checksum.reset()
    }
}
