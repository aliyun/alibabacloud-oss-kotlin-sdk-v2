@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

import java.security.MessageDigest

public actual class Md5 : HashFunction {
    private val md = MessageDigest.getInstance("MD5")
    actual override fun update(input: ByteArray, offset: Int, length: Int): Unit = md.update(input, offset, length)
    actual override fun digest(): ByteArray = md.digest()
    actual override fun reset(): Unit = md.reset()
}

public actual fun ByteArray.md5(): ByteArray {
    val hash = Md5()
    hash.update(this, 0, this.size)
    return hash.digest()
}
