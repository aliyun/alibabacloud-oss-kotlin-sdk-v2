package com.aliyun.kotlin.sdk.service.oss2.hash

/**
 * Implementation of MD5 hash function.
 */
public expect class Md5 : HashFunction {
    override fun update(input: ByteArray, offset: Int, length: Int)
    override fun digest(): ByteArray
    override fun reset()
}

/**
 * Compute the MD5 hash of the current [ByteArray]
 */
public expect fun ByteArray.md5(): ByteArray
