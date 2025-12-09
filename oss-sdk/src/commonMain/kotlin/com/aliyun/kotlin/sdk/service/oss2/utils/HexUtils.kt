package com.aliyun.kotlin.sdk.service.oss2.utils

internal object HexUtils {
    private val hexChars = "0123456789abcdef".toCharArray()

    fun encodeHex(bytes: ByteArray): String = buildString(bytes.size * 2) {
        for (i in bytes.indices) {
            val byte = bytes[i].toInt() and 0xff
            append(hexChars[byte shr 4])
            append(hexChars[byte and 0x0f])
        }
    }
}
