package com.aliyun.kotlin.sdk.service.oss2.internal

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EnsureTest {

    @Test
    fun isValidAccountIdPureDigits() {
        assertTrue(Ensure.isValidAccountId("1234567890123456"))
        assertTrue(Ensure.isValidAccountId("0"))
        assertTrue(Ensure.isValidAccountId("9999"))
    }

    @Test
    fun isValidAccountIdNonDigitsOrEmpty() {
        assertFalse(Ensure.isValidAccountId(null))
        assertFalse(Ensure.isValidAccountId(""))
        assertFalse(Ensure.isValidAccountId("abc"))
        assertFalse(Ensure.isValidAccountId("1234abc"))
        assertFalse(Ensure.isValidAccountId("123-456"))
        assertFalse(Ensure.isValidAccountId(" 123"))
    }
}
