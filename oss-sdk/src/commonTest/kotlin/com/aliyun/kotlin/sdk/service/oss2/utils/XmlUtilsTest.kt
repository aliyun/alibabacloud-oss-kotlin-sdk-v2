package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class XmlUtilsTest {

    @Test
    fun testEscapeText() {
        val string1 = "abcdefghijklmnopqrstuvwxyzZBCDEFGHIJKLMNOPQRSTUVWXYZ1234567890一\\[],./-_=+"
        val string2 = "abcdefghijklmnopqrstuvwxyzZBCDEFGHIJKLMNOPQRSTUVWXYZ1234567890一\\[],\'.\"/<->_&=+"
        val escapeString2 = "abcdefghijklmnopqrstuvwxyzZBCDEFGHIJKLMNOPQRSTUVWXYZ1234567890一\\[],&apos;.&quot;/&lt;-&gt;_&amp;=+"

        assertEquals(string1, XmlUtils.escapeText(string1))
        assertEquals(escapeString2, XmlUtils.escapeText(string2))
    }
}
