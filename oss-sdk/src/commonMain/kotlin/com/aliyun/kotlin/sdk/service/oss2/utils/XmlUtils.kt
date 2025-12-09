package com.aliyun.kotlin.sdk.service.oss2.utils

internal object XmlUtils {

    fun escapeText(key: String?): String {
        if (key == null) {
            return ""
        }
        val len = key.length
        val builder = StringBuilder()
        var pos: Int = 0
        while (pos < len) {
            val ch = key[pos]
            val escapedStr = when (ch) {
                '&' -> "&amp;"
                '\'' -> "&apos;"
                '"' -> "&quot;"
                '<' -> "&lt;"
                '>' -> "&gt;"
                else -> if (ch.code < 0x20) {
                    "&#x" + Integer.toHexString(ch.code) + ";"
                } else {
                    null
                }
            }

            if (escapedStr != null) {
                builder.append(escapedStr)
            } else {
                builder.append(ch)
            }
            pos++
        }

        return builder.toString()
    }
}
