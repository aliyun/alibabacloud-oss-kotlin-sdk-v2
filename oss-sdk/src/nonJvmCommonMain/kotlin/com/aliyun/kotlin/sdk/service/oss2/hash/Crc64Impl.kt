package com.aliyun.kotlin.sdk.service.oss2.hash

import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64.Companion.GF2_DIM
import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64.Companion.POLY
import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64.Companion.gf2MatrixSquare
import com.aliyun.kotlin.sdk.service.oss2.hash.Crc64.Companion.gf2MatrixTimes

/**
 * CRC-64 implementation with ability to combine checksums calculated over
 * different blocks of data.
 * <a href="http://www.ecma-international.org/publications/standards/Ecma-182.htm">Standard ECMA-182</a>
 */
public actual class Crc64 : HashFunction {

    public actual constructor() : this(0)
    public actual constructor(value: Long) {
        initValue = value
        this.value = value
    }
    public actual constructor(input: ByteArray, length: Int) : this(0) {
        update(input, 0, length)
    }
    private val initValue: Long

    private var value: Long
    public actual val digestValue: Long
        get() = value

    public companion object {
        internal const val POLY = 0xc96c5795d7870f42uL // ECMA-182
        internal const val GF2_DIM = 64 // dimension of GF(2) vectors (length of CRC)

        /* CRC64 calculation table. */
        internal val table: Array<LongArray> = Array(8) { LongArray(256) }

        init {
            /*
             * Nested tables as described by Mark Adler:
             * http://stackoverflow.com/a/20579405/58962
             */
            for (n in 0 until 256) {
                var crc = n.toLong()
                for (k in 0 until 8) {
                    crc = if ((crc and 1) == 1L) {
                        (crc ushr 1) xor POLY.toLong()
                    } else {
                        crc ushr 1
                    }
                }
                table[0][n] = crc
            }

            /* generate nested CRC table for future slice-by-8 lookup */
            for (n in 0 until 256) {
                var crc = table[0][n]
                for (k in 1 until 8) {
                    crc = table[0][(crc and 0xff).toInt()] xor (crc ushr 8)
                    table[k][n] = crc
                }
            }
        }

        /**
         * Construct new CRC64 instance from byte array.
         *
         * @param b the buffer into which the data is read.
         * @return a [Crc64] instance.
         **/
        public fun fromBytes(b: ByteArray): Crc64 {
            var l = 0L
            for (i in 0 until 4) {
                l = l shl 8
                l = l xor (b[i].toLong() and 0xFF)
            }
            return Crc64(l)
        }

        internal fun gf2MatrixTimes(mat: LongArray, vec: Long): Long {
            var vec = vec
            var sum = 0L
            var idx = 0
            while (vec != 0L) {
                if ((vec and 1) == 1L) sum = sum xor mat[idx]
                vec = vec ushr 1
                idx++
            }
            return sum
        }

        internal fun gf2MatrixSquare(square: LongArray, mat: LongArray) {
            for (n in 0 until GF2_DIM) {
                square[n] = gf2MatrixTimes(mat, mat[n])
            }
        }
    }

    actual override fun digest(): ByteArray {
        val b = ByteArray(8)
        for (i in 0 until 8) {
            b[7 - i] = (value ushr (i * 8)).toByte()
        }
        return b
    }

    actual override fun update(input: ByteArray, offset: Int, length: Int) {
        value = value.inv()

        /* fast middle processing, 8 bytes (aligned!) per loop */
        var idx = offset
        var length = length

        while (length >= 8) {
            value = table[7][(value and 0xff xor (input[idx].toLong() and 0xff)).toInt()] xor
                table[6][((value ushr 8) and 0xff xor (input[idx + 1].toLong() and 0xff)).toInt()] xor
                table[5][((value ushr 16) and 0xff xor (input[idx + 2].toLong() and 0xff)).toInt()] xor
                table[4][((value ushr 24) and 0xff xor (input[idx + 3].toLong() and 0xff)).toInt()] xor
                table[3][((value ushr 32) and 0xff xor (input[idx + 4].toLong() and 0xff)).toInt()] xor
                table[2][((value ushr 40) and 0xff xor (input[idx + 5].toLong() and 0xff)).toInt()] xor
                table[1][((value ushr 48) and 0xff xor (input[idx + 6].toLong() and 0xff)).toInt()] xor
                table[0][((value ushr 56) xor (input[idx + 7].toLong() and 0xff)).toInt()]
            idx += 8
            length -= 8
        }

        /* process remaining bytes (can't be larger than 8) */
        while (length > 0) {
            value = table[0][((value xor input[idx].toLong()) and 0xff).toInt()] xor (value ushr 8)
            idx++
            length--
        }

        value = value.inv()
    }

    actual override fun reset() {
        value = initValue
    }
}

public actual fun Crc64.combine(crc: Crc64, length: Long): Crc64 {
    // degenerate case.
    if (length == 0L) return Crc64(digestValue)

    val even = LongArray(GF2_DIM) // even-power-of-two zeros operator
    val odd = LongArray(GF2_DIM) // odd-power-of-two zeros operator

    // put operator for one zero bit in odd
    odd[0] = POLY.toLong() // CRC-64 polynomial

    var row = 1L
    for (n in 1 until GF2_DIM) {
        odd[n] = row
        row = row shl 1
    }

    // put operator for two zero bits in even
    gf2MatrixSquare(even, odd)

    // put operator for four zero bits in odd
    gf2MatrixSquare(odd, even)

    // apply len2 zeros to crc1 (first square will put the operator for one
    // zero byte, eight zero bits, in even)
    var crc1 = digestValue
    val crc2 = crc.digestValue
    var len = length
    do {
        // apply zeros operator for this bit of len2
        gf2MatrixSquare(even, odd)
        if ((len and 1) == 1L) crc1 = gf2MatrixTimes(even, crc1)
        len = len ushr 1

        // if no more bits set, then done
        if (len == 0L) break

        // another iteration of the loop with odd and even swapped
        gf2MatrixSquare(odd, even)
        if ((len and 1) == 1L) crc1 = gf2MatrixTimes(odd, crc1)
        len = len ushr 1

        // if no more bits set, then done
    } while (len != 0L)

    // return combined crc.
    crc1 = crc1 xor crc2
    return Crc64(crc1)
}

public actual fun Long.combine(crc: Long, length: Long): Long {
    // degenerate case.
    if (length == 0L) return this

    val even = LongArray(GF2_DIM) // even-power-of-two zeros operator
    val odd = LongArray(GF2_DIM) // odd-power-of-two zeros operator

    // put operator for one zero bit in odd
    odd[0] = POLY.toLong() // CRC-64 polynomial

    var row = 1L
    for (n in 1 until GF2_DIM) {
        odd[n] = row
        row = row shl 1
    }

    // put operator for two zero bits in even
    gf2MatrixSquare(even, odd)

    // put operator for four zero bits in odd
    gf2MatrixSquare(odd, even)

    // apply len2 zeros to crc1 (first square will put the operator for one
    // zero byte, eight zero bits, in even)
    var crc1Var = this
    var len = length
    do {
        // apply zeros operator for this bit of len2
        gf2MatrixSquare(even, odd)
        if ((len and 1) == 1L) crc1Var = gf2MatrixTimes(even, crc1Var)
        len = len ushr 1

        // if no more bits set, then done
        if (len == 0L) break

        // another iteration of the loop with odd and even swapped
        gf2MatrixSquare(odd, even)
        if ((len and 1) == 1L) crc1Var = gf2MatrixTimes(odd, crc1Var)
        len = len ushr 1

        // if no more bits set, then done
    } while (len != 0L)

    // return combined crc.
    crc1Var = crc1Var xor crc
    return crc1Var
}
