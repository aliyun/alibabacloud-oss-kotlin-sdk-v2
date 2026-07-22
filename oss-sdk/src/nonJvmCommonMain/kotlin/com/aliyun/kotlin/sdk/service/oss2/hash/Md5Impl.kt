package com.aliyun.kotlin.sdk.service.oss2.hash

import org.kotlincrypto.hash.md.MD5

public actual class Md5 : HashFunction {
    private val digest = MD5()
    actual override fun update(input: ByteArray, offset: Int, length: Int): Unit = digest.update(input, offset, length)
    actual override fun digest(): ByteArray = digest.digest()
    actual override fun reset(): Unit = digest.reset()
}

public actual fun ByteArray.md5(): ByteArray = MD5().digest(this)
