package com.aliyun.kotlin.sdk.service.oss2.hash

public expect class Crc64 : HashFunction {
    public constructor()
    public constructor(value: Long)
    public constructor(input: ByteArray, length: Int)
    public val digestValue: Long

    override fun update(input: ByteArray, offset: Int, length: Int)
    override fun digest(): ByteArray
    override fun reset()
}

public expect fun Crc64.combine(crc: Crc64, length: Long): Crc64
public expect fun Long.combine(crc: Long, length: Long): Long
