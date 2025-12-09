package com.aliyun.kotlin.sdk.service.oss2.exceptions

/**
 * Inconsistent Error public class that indicates a mismatch between client and server CRC checksum values
 */
public class InconsistentException(
    public val clientCrc: String,
    public val serverCrc: String,
    public val headers: Map<String, String>?
) : RuntimeException(
    "crc is inconsistent, client crc: $clientCrc, server crc:$serverCrc, requestId: ${toRequestId(headers)}."
) {

    public val requestId: String
        get() = toRequestId(this.headers)

    private companion object {
        fun toRequestId(headers: Map<String, String>?): String {
            if (headers == null) {
                return ""
            }
            return headers["x-oss-request-id"] ?: ""
        }
    }
}
