package com.aliyun.kotlin.sdk.service.oss2.hash

/**
 * An interface representing a data hash.
 */
public interface HashFunction {
    /**
     * Updates the current hash with the specified byte.
     */
    public fun update(input: ByteArray, offset: Int, length: Int)

    /**
     * Finalize the hash computation and return the digest bytes.
     */
    public fun digest(): ByteArray

    /**
     * Resets the digest to its initial value.
     */
    public fun reset()
}
