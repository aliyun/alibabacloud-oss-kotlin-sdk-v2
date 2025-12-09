@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

public actual class Sha1 : HashFunction {
    private val md = MessageDigest.getInstance("SHA-1")
    actual override fun update(input: ByteArray, offset: Int, length: Int): Unit = md.update(input, offset, length)
    actual override fun digest(): ByteArray = md.digest()
    actual override fun reset(): Unit = md.reset()
}

public actual fun ByteArray.sha1(): ByteArray {
    val hash = Sha1()
    hash.update(this, 0, this.size)
    return hash.digest()
}

public actual fun ByteArray.hmacSha1(key: ByteArray): ByteArray {
    val mac = Mac.getInstance("HmacSHA1")
    mac.init(SecretKeySpec(key, "HmacSHA1"))
    return mac.doFinal(this)
}
