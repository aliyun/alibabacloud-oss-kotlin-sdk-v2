@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

import org.kotlincrypto.hash.sha2.SHA256
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256

public actual class Sha256 : HashFunction {
    private val digest = SHA256()
    actual override fun update(input: ByteArray, offset: Int, length: Int): Unit = digest.update(input, offset, length)
    actual override fun digest(): ByteArray = digest.digest()
    actual override fun reset(): Unit = digest.reset()
}

public actual fun ByteArray.sha256(): ByteArray = SHA256().digest(this)

public actual fun ByteArray.hmacSha256(key: ByteArray): ByteArray = HmacSHA256(key).doFinal(this)
