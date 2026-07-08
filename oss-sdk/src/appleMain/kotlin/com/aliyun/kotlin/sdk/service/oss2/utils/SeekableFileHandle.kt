package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.posix.O_RDONLY
import platform.posix.open
import platform.posix.pread

internal actual class SeekableFileHandle actual constructor(path: String) {
    private val fd: Int = open(path, O_RDONLY)

    @OptIn(ExperimentalForeignApi::class)
    actual fun read(position: Long, destination: ByteArray, destinationOffset: Int, length: Int): Int {
        if (fd < 0) return -1
        return destination.usePinned { pinned ->
            val buf = pinned.addressOf(destinationOffset)
            val bytesRead = pread(fd, buf, length.toULong(), position)
            if (bytesRead <= 0) -1 else bytesRead.toInt()
        }
    }

    actual fun close() {
        platform.posix.close(fd)
    }
}
