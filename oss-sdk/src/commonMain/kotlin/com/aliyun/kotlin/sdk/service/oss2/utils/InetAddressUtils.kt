package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.text.Regex

internal object InetAddressUtils {
    private const val MAX_BYTE = 128

    private const val IPV4_MAX_OCTET_VALUE = 255

    private const val MAX_UNSIGNED_SHORT = 0xffff

    private const val BASE_16 = 16

    private const val IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$"

    // Max number of hex groups (separated by :) in an IPV6 address
    private const val IPV6_MAX_HEX_GROUPS = 8

    // Max hex digits in each IPv6 group
    private const val IPV6_MAX_HEX_DIGITS_PER_GROUP = 4

    private val DIGITS_PATTERN = Regex("\\d{1,3}").toPattern()

    private val ID_CHECK_PATTERN = Regex("[^\\s/%]+").toPattern()

    /**
     * Checks if the specified string is a valid IPv4 or IPv6 address.
     *
     * @param inetAddress the string to validate
     * @return true if the string validates as an IP address
     */
    fun isValid(inetAddress: String): Boolean {
        return isValidInet4Address(inetAddress) || isValidInet6Address(inetAddress)
    }

    /**
     * Validates an IPv4 address. Returns true if valid.
     *
     * @param inet4Address the IPv4 address to validate
     * @return true if the argument contains a valid IPv4 address
     */
    fun isValidInet4Address(inet4Address: String?): Boolean {
        // verify that address conforms to generic IPv4 format
        val groups = ipv4Match(inet4Address)
        if (groups == null) {
            return false
        }
        // verify that address subgroups are legal
        for (ipSegment in groups) {
            if (isBlankOrNull(ipSegment)) {
                return false
            }
            var iIpSegment: Int
            try {
                iIpSegment = ipSegment!!.toInt()
            } catch (_: NumberFormatException) {
                return false
            }
            if (iIpSegment > IPV4_MAX_OCTET_VALUE || ipSegment.length > 1 && ipSegment.startsWith("0")) {
                return false
            }
        }
        return true
    }

    /**
     * Validates an IPv6 address. Returns true if valid.
     *
     * @param inet6Address the IPv6 address to validate
     * @return true if the argument contains a valid IPv6 address
     */
    fun isValidInet6Address(inet6Address: String): Boolean {
        var inet6Address = inet6Address
        // remove prefix size. This will appear after the zone id (if any)
        var parts = inet6Address.split("/".toRegex()).toTypedArray()
        if (parts.size > 2) {
            return false // can only have one prefix specifier
        }
        if (parts.size == 2) {
            if (!DIGITS_PATTERN.matcher(parts[1]).matches()) {
                return false // not a valid number
            }
            val bits = parts[1].toInt() // cannot fail because of RE check
            if (bits < 0 || bits > MAX_BYTE) {
                return false // out of range
            }
        }
        // remove zone-id
        parts = parts[0].split("%".toRegex()).toTypedArray()
        // The id syntax is implementation independent, but it presumably cannot allow:
        // whitespace, '/' or '%'
        if ((parts.size > 2) || (parts.size == 2 && !ID_CHECK_PATTERN.matcher(parts[1]).matches())) {
            return false // invalid id
        }
        inet6Address = parts[0]
        val containsCompressedZeroes = inet6Address.contains("::")
        if (containsCompressedZeroes && inet6Address.indexOf("::") != inet6Address.lastIndexOf("::")) {
            return false
        }
        val startsWithCompressed = inet6Address.startsWith("::")
        val endsWithCompressed = inet6Address.endsWith("::")
        val endsWithSep = inet6Address.endsWith(":")
        if (inet6Address.startsWith(":") && !startsWithCompressed || endsWithSep && !endsWithCompressed) {
            return false
        }
        var octets: Array<String> = inet6Address.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (containsCompressedZeroes) {
            val octetList: MutableList<String> = mutableListOf(*octets)
            if (endsWithCompressed) {
                // String.split() drops ending empty segments
                octetList.add("")
            } else if (startsWithCompressed && !octetList.isEmpty()) {
                octetList.removeAt(0)
            }
            octets = octetList.toTypedArray<String>()
        }
        if (octets.size > IPV6_MAX_HEX_GROUPS) {
            return false
        }
        var validOctets = 0
        var emptyOctets = 0 // consecutive empty chunks
        for (index in octets.indices) {
            val octet = octets[index]
            if (isBlankOrNull(octet)) {
                emptyOctets++
                if (emptyOctets > 1) {
                    return false
                }
            } else {
                emptyOctets = 0
                // Is last chunk an IPv4 address?
                if (index == octets.size - 1 && octet.contains(".")) {
                    if (!isValidInet4Address(octet)) {
                        return false
                    }
                    validOctets += 2
                    continue
                }
                if (octet.length > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
                    return false
                }
                var octetInt: Int
                try {
                    octetInt = octet.toInt(BASE_16)
                } catch (_: NumberFormatException) {
                    return false
                }
                if (octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT) {
                    return false
                }
            }
            validOctets++
        }
        return validOctets <= IPV6_MAX_HEX_GROUPS && (validOctets >= IPV6_MAX_HEX_GROUPS || containsCompressedZeroes)
    }

    fun isBlankOrNull(value: String?): Boolean {
        // Don't trim is already empty.
        return value == null || value.isEmpty() || value.trim { it <= ' ' }.isEmpty()
    }

    fun ipv4Match(value: String?): Array<String?>? {
        if (value == null) {
            return null
        }
        val pattern = Regex(IPV4_REGEX).toPattern()
        val matcher = pattern.matcher(value)
        if (matcher.matches()) {
            val count = matcher.groupCount()
            val groups = arrayOfNulls<String>(count)
            for (j in 0..<count) {
                groups[j] = matcher.group(j + 1)
            }
            return groups
        }
        return null
    }
}
