package com.aliyun.kotlin.sdk.service.oss2.utils

import com.aliyun.kotlin.sdk.service.oss2.utils.InetAddressUtils.isValid
import com.aliyun.kotlin.sdk.service.oss2.utils.InetAddressUtils.isValidInet6Address
import kotlin.test.*

class InetAddressUtilsTest {
    /**
     * Test obviously broken IPs.
     */
    @Test
    fun testBrokenInetAddresses() {
        assertFalse(isValid("124.14.32.abc"))
        // TODO: there is some debate as to whether leading zeros should be allowed
        // They are ambiguous: does the leading 0 mean octal?
        assertFalse(isValid("124.14.32.01"))
        assertFalse(isValid("23.64.12"))
        assertFalse(isValid("26.34.23.77.234"))
        assertFalse(isValidInet6Address("")) // empty string
    }

    /**
     * Test valid and invalid IPs from each address class.
     */
    @Test
    fun testInetAddressesByClass() {
        assertTrue(isValid("24.25.231.12"))
        assertFalse(isValid("2.41.32.324"))

        assertTrue(isValid("135.14.44.12"))
        assertFalse(isValid("154.123.441.123"))

        assertTrue(isValid("213.25.224.32"))
        assertFalse(isValid("201.543.23.11"))

        assertTrue(isValid("229.35.159.6"))
        assertFalse(isValid("231.54.11.987"))

        assertTrue(isValid("248.85.24.92"))
        assertFalse(isValid("250.21.323.48"))
    }

    /**
     * Test IPs that point to real, well-known hosts (without actually looking them up).
     */
    @Test
    fun testInetAddressesFromTheWild() {
        assertTrue(isValid("140.211.11.130"))
        assertTrue(isValid("72.14.253.103"))
        assertTrue(isValid("199.232.41.5"))
        assertTrue(isValid("216.35.123.87"))
    }

    /**
     * Test IPv6 addresses.
     *
     *
     * These tests were ported from a [Perl script](https://download.dartware.com/thirdparty/test-ipv6-regex.pl).
     *
     */
    @Test
    fun testIPv6() {
        // The original Perl script contained a lot of duplicate tests.
        // I removed the duplicates I noticed, but there may be more.
        assertFalse(isValidInet6Address("")) // empty string
        assertTrue(isValidInet6Address("::1")) // loopback, compressed, non-routable
        assertTrue(isValidInet6Address("::")) // unspecified, compressed, non-routable
        assertTrue(isValidInet6Address("0:0:0:0:0:0:0:1")) // loopback, full
        assertTrue(isValidInet6Address("0:0:0:0:0:0:0:0")) // unspecified, full
        assertTrue(isValidInet6Address("2001:DB8:0:0:8:800:200C:417A")) // unicast, full
        assertTrue(isValidInet6Address("FF01:0:0:0:0:0:0:101")) // multicast, full
        assertTrue(isValidInet6Address("2001:DB8::8:800:200C:417A")) // unicast, compressed
        assertTrue(isValidInet6Address("FF01::101")) // multicast, compressed
        assertFalse(isValidInet6Address("2001:DB8:0:0:8:800:200C:417A:221")) // unicast,
        // full
        assertFalse(isValidInet6Address("FF01::101::2")) // multicast, compressed
        assertTrue(isValidInet6Address("fe80::217:f2ff:fe07:ed62"))
        assertTrue(isValidInet6Address("2001:0000:1234:0000:0000:C1C0:ABCD:0876"))
        assertTrue(isValidInet6Address("3ffe:0b00:0000:0000:0001:0000:0000:000a"))
        assertTrue(isValidInet6Address("FF02:0000:0000:0000:0000:0000:0000:0001"))
        assertTrue(isValidInet6Address("0000:0000:0000:0000:0000:0000:0000:0001"))
        assertTrue(isValidInet6Address("0:a:b:c:d:e:f::"))
        assertTrue(isValidInet6Address("::0:a:b:c:d:e:f")) // syntactically correct, but bad form (::0:...
        // could be combined)
        assertTrue(isValidInet6Address("a:b:c:d:e:f:0::"))
        assertFalse(isValidInet6Address("':10.0.0.1"))
    }

    /**
     * Test reserved IPs.
     */
    @Test
    fun testReservedInetAddresses() {
        assertTrue(isValid("127.0.0.1"))
        assertTrue(isValid("255.255.255.255"))
    }

    @Test
    fun testValidator335() {
        assertTrue(isValid("2001:0438:FFFE:0000:0000:0000:0000:0A35"))
    }

    @Test
    fun testValidator419() {
        var addr: String?
        addr = "0:0:0:0:0:0:13.1.68.3"
        assertTrue(isValid(addr))
        addr = "0:0:0:0:0:FFFF:129.144.52.38"
        assertTrue(isValid(addr))
        addr = "::13.1.68.3"
        assertTrue(isValid(addr))
        addr = "::FFFF:129.144.52.38"
        assertTrue(isValid(addr))

        addr = "::ffff:192.168.1.1:192.168.1.1"
        assertFalse(isValid(addr))
        addr = "::192.168.1.1:192.168.1.1"
        assertFalse(isValid(addr))
    }

    /**
     * Inet6Address may also contain a scope id.
     */
    @Test
    fun testValidator445() {
        val valid = arrayOf<String?>(
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876",
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/123",
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/0",
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876%0",
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abcdefgh",
        )
        val invalid = arrayOf<String?>(
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/129", // too big
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/-0", // sign not allowed
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/+0", // sign not allowed
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/10O", // non-digit
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876/0%0", // /bits before %node-id
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abc defgh", // space in node id
            "2001:0000:1234:0000:0000:C1C0:ABCD:0876%abc%defgh", // '%' in node id
        )
        for (item in valid) {
            assertTrue(InetAddressUtils.isValid(item!!), item + " should be valid")
        }
        for (item in invalid) {
            assertFalse(InetAddressUtils.isValid(item!!), item + " should be invalid")
        }
    }
}
