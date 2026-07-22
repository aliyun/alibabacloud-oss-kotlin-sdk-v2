@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

import org.kotlincrypto.hash.sha1.SHA1
import org.kotlincrypto.macs.hmac.sha1.HmacSHA1

public actual class Sha1 : HashFunction {
    private val digest = SHA1()
    actual override fun update(input: ByteArray, offset: Int, length: Int): Unit = digest.update(input, offset, length)
    actual override fun digest(): ByteArray = digest.digest()
    actual override fun reset(): Unit = digest.reset()
}

public actual fun ByteArray.sha1(): ByteArray = SHA1().digest(this)

public actual fun ByteArray.hmacSha1(key: ByteArray): ByteArray = HmacSHA1(key).doFinal(this)
