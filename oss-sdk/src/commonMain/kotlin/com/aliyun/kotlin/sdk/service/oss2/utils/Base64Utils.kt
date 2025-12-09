package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.io.encoding.Base64

public object Base64Utils {

    public fun encode(src: ByteArray): ByteArray {
        return Base64.encodeToByteArray(src)
    }

    public fun encodeToString(src: ByteArray): String {
        return Base64.encode(src)
    }

    public fun decode(encoded: ByteArray): ByteArray {
        return Base64.decode(encoded)
    }

    /**
     * Decodes a base64 encoded string.
     */
    public fun decodeString(encoded: String): ByteArray {
        return Base64.decode(encoded)
    }
}
