@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.aliyun.kotlin.sdk.service.oss2.hash

/**
 * Implementation of SHA-256 hash function.
 */
public expect class Sha256() : HashFunction {
    override fun update(input: ByteArray, offset: Int, length: Int)
    override fun digest(): ByteArray
    override fun reset()
}

/**
 * Compute the SHA-256 hash of the current [ByteArray]
 */
public expect fun ByteArray.sha256(): ByteArray

/**
 * Performs HMAC-SHA256 calculation of the current [ByteArray]
 * @param key  HMAC key bytes
 */
public expect fun ByteArray.hmacSha256(key: ByteArray): ByteArray
