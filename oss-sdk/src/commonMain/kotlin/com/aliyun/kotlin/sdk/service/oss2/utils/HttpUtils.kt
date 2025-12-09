package com.aliyun.kotlin.sdk.service.oss2.utils

/**
 * An algorithm that percent-encodes string data for use in URLs
 * @param name The name of this encoding
 * @param validChars The set of characters which are valid _unencoded_ (i.e., in plain text). All other characters will
 * be percent-encoded unless they appear in [specialMapping].
 * @param specialMapping A mapping of characters to their special (i.e., non-percent-encoded) form. The characters which
 * are keys in this map will not be percent-encoded.
 */
internal class PercentEncoding(
    val name: String,
    val validChars: Set<Char>,
    val specialMapping: Map<Char, Char> = mapOf(),
) {
    companion object {
        // These definitions are from RFC 3986 Appendix A, see https://datatracker.ietf.org/doc/html/rfc3986#appendix-A
        private val ALPHA = (('A'..'Z') + ('a'..'z')).toSet()
        private val DIGIT = ('0'..'9').toSet()
        private val UNRESERVED = ALPHA + DIGIT + setOf('-', '.', '_', '~')
        private val VALID_PCHAR = UNRESERVED + setOf('/')

        private const val UPPER_HEX = "0123456789ABCDEF"

        /**
         * A [PercentEncoding] instance suitable for encoding URL paths
         */
        val Path = PercentEncoding("path", VALID_PCHAR)

        /**
         * A [PercentEncoding] instance suitable for encoding strings
         */
        val Default = PercentEncoding("default", UNRESERVED)

        private fun percentAsciiEncode(char: Char) = buildString {
            val value = char.code and 0xff
            append('%')
            append(UPPER_HEX[value shr 4])
            append(UPPER_HEX[value and 0x0f])
        }

        private fun StringBuilder.percentEncode(byte: Byte) {
            val value = byte.toInt() and 0xff
            append('%')
            append(UPPER_HEX[value shr 4])
            append(UPPER_HEX[value and 0x0f])
        }
    }

    private val asciiMapping = (0..<128)
        .map(Int::toChar)
        .filterNot(validChars::contains)
        .associateWith(Companion::percentAsciiEncode)

    private val encodeMap = asciiMapping + specialMapping.mapValues { (_, char) -> char.toString() }

    private val decodeMap = (validChars.associateWith { it } + specialMapping)
        .entries
        .associate { (decoded, encoded) -> encoded to decoded }

    fun decode(encoded: String): String = buildString(encoded.length) {
        var byteBuffer: ByteArray? = null // Do not initialize unless needed

        var i = 0
        var c: Char
        while (i < encoded.length) {
            c = encoded[i]
            if (c == '%') {
                if (byteBuffer == null) {
                    byteBuffer = ByteArray((encoded.length - i) / 3) // Max remaining percent-encoded bytes
                }

                var byteCount = 0
                while ((i + 2) < encoded.length && c == '%') {
                    val byte = encoded.substring(i + 1, i + 3).toIntOrNull(radix = 16)?.toByte() ?: break
                    byteBuffer[byteCount++] = byte

                    i += 3
                    if (i < encoded.length) c = encoded[i]
                }

                append(byteBuffer.decodeToString(endIndex = byteCount))

                if (i != encoded.length && c == '%') {
                    append(c)
                    i++
                }
            } else {
                append(decodeMap[c] ?: c)
                i++
            }
        }
    }

    fun encode(decoded: String): String = buildString(decoded.length) {
        val bytes = decoded.encodeToByteArray()
        for (byte in bytes) {
            val char = byte.toInt().toChar()
            if (char in validChars) {
                append(char)
            } else {
                encodeMap[char]?.let(::append) ?: percentEncode(byte)
            }
        }
    }
}

internal object HttpUtils {
    /**
     * Encode a string according to RFC 3986: encoding for URI paths, query strings, etc.
     */
    fun urlEncode(value: String): String {
        return PercentEncoding.Default.encode(value)
    }

    /**
     * Encode a string according to RFC 3986, but ignore "/" characters.
     * This is useful for encoding the components of a path without encoding the path separators.
     */
    fun urlEncodePath(value: String): String {
        return PercentEncoding.Path.encode(value)
    }

    /**
     * Decode the string according to RFC 3986: encoding for URI paths, query strings, etc.
     *
     * @param value The string to decode.
     * @return The decoded string.
     */
    fun urlDecode(value: String): String {
        return PercentEncoding.Default.decode(value)
    }

    /**
     * Encode request parameters to URL segment.
     */
    fun encodeQueryParameters(parameters: Map<String, String?>): String {
        if (parameters.isEmpty()) {
            return ""
        }

        var first = true
        val queryString = StringBuilder()
        for (p in parameters.entries) {
            val key = p.key
            val value = p.value

            if (!first) {
                queryString.append("&")
            }
            queryString.append(urlEncode(key))
            if (value != null) {
                queryString.append("=").append(urlEncode(value))
            }
            first = false
        }

        return queryString.toString()
    }

    /**
     * Extracts query parameters from the given URI, do not do url decode
     */
    fun extractParamsWithoutDecode(uri: String): MutableMap<String, String> {
        val start = uri.indexOf("?")

        // no query string
        if (start < 0) {
            return mutableMapOf()
        }

        val rawQuery = uri.substring(start + 1)

        if (rawQuery.isNotEmpty()) {
            return rawQuery
                .split("&").associate { segment ->
                    val parts = segment.split("=")
                    val key = parts[0]
                    val value = when (parts.size) {
                        1 -> ""
                        2 -> parts[1]
                        else -> throw IllegalArgumentException("invalid query string segment $segment")
                    }
                    key to value
                }
                .toMutableMap()
        }

        return mutableMapOf()
    }

    fun extractParams(uri: String): MutableMap<String, String> {
        val start = uri.indexOf("?")

        // no query string
        if (start < 0) {
            return mutableMapOf()
        }

        val rawQuery = uri.substring(start + 1)

        if (rawQuery.isNotEmpty()) {
            return rawQuery
                .split("&").associate { segment ->
                    val parts = segment.split("=")
                    val key = urlDecode(parts[0])
                    val value = when (parts.size) {
                        1 -> ""
                        2 -> urlDecode(parts[1])
                        else -> throw IllegalArgumentException("invalid query string segment $segment")
                    }
                    key to value
                }
                .toMutableMap()
        }

        return mutableMapOf()
    }
}

internal fun String.urlEncode() = HttpUtils.urlEncode(this)
internal fun String.urlEncodePath() = HttpUtils.urlEncodePath(this)
internal fun String.urlDecode() = HttpUtils.urlDecode(this)
