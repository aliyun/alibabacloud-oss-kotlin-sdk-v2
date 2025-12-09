package com.aliyun.kotlin.sdk.service.oss2.types

/**
 * Container for wrapping a ByteArray as a [ByteStream]
 */
internal class ByteArrayContent(private val bytes: ByteArray) : ByteStream.Buffer() {
    override val contentLength: Long = bytes.size.toLong()
    override fun bytes(): ByteArray = bytes
}
