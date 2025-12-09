@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

/**
 * Implementation of SHA-1 hash function.
 */
public expect class Sha1() : HashFunction {
    override fun update(input: ByteArray, offset: Int, length: Int)
    override fun digest(): ByteArray
    override fun reset()
}

/**
 * Compute the SHA-1 hash of the current [ByteArray]
 */
public expect fun ByteArray.sha1(): ByteArray

/**
 * Performs HMAC-SHA1 calculation of the current [ByteArray]
 * @param key  HMAC key bytes
 */
public expect fun ByteArray.hmacSha1(key: ByteArray): ByteArray
