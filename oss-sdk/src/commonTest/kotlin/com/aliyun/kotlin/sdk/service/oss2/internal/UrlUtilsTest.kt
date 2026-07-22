package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UrlUtilsTest {
    @Test
    fun testHttpsWithoutPort() {
        val result = assertNotNull(parseUrl("https://oss-cn-hangzhou.aliyuncs.com"))
        assertEquals("https", result["scheme"])
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result["host"])
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result["authority"])
    }

    @Test
    fun testHttpScheme() {
        val result = assertNotNull(parseUrl("http://oss-cn-hangzhou.aliyuncs.com"))
        assertEquals("http", result["scheme"])
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result["host"])
    }

    @Test
    fun testExplicitPortIncludedInAuthority() {
        val result = assertNotNull(parseUrl("http://example.com:8080"))
        assertEquals("http", result["scheme"])
        assertEquals("example.com", result["host"])
        assertEquals("example.com:8080", result["authority"])
    }

    @Test
    fun testPathAndQueryExcludedFromHostAndAuthority() {
        val result = assertNotNull(parseUrl("https://oss-cn-hangzhou.aliyuncs.com/bucket?list-type=2&encoding-type=url"))
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result["host"])
        assertEquals("oss-cn-hangzhou.aliyuncs.com", result["authority"])
    }

    @Test
    fun testPortWithPath() {
        val result = assertNotNull(parseUrl("https://example.com:8443/bucket/key?x=1"))
        assertEquals("example.com", result["host"])
        assertEquals("example.com:8443", result["authority"])
    }

    @Test
    fun testUserInfoExcludedFromAuthority() {
        val result = assertNotNull(parseUrl("https://user:pass@example.com/bucket"))
        assertEquals("example.com", result["host"])
        assertEquals("example.com", result["authority"])
    }

    @Test
    fun testUserInfoExcludedWithPort() {
        val result = assertNotNull(parseUrl("https://user:pass@example.com:8443/bucket"))
        assertEquals("example.com", result["host"])
        assertEquals("example.com:8443", result["authority"])
    }

    @Test
    fun testEmptyStringReturnsNull() {
        assertNull(parseUrl(""))
    }

    @Test
    fun testMissingSchemeAndHostReturnsNull() {
        assertNull(parseUrl("oss-cn-hangzhou.aliyuncs.com"))
    }

    @Test
    fun testSchemeWithEmptyAuthorityReturnsNull() {
        assertNull(parseUrl("https://#!@"))
        assertNull(parseUrl("https:///path"))
    }
}
