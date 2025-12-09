package com.aliyun.kotlin.sdk.service.oss2.types

/**
 * Container for wrapping a String as a [ByteStream]
 */
internal class StringContent(str: String) : ByteStream.Buffer() {
    private val asBytes: ByteArray = str.encodeToByteArray()

    override val contentLength: Long = asBytes.size.toLong()

    override fun bytes(): ByteArray = asBytes
}
