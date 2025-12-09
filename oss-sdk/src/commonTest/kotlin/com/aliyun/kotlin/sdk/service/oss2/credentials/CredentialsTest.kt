package com.aliyun.kotlin.sdk.service.oss2.credentials

import kotlin.test.*

class CredentialsTest {
    @Test
    fun testConstructor() {
        var cred = Credentials("ak", "sk")
        assertNotNull(cred)

        assertEquals("ak", cred.accessKeyId)
        assertEquals("sk", cred.accessKeySecret)
        assertNull(cred.securityToken)
        assertTrue(cred.hasKeys())

        cred = Credentials("ak", "sk", "token")
        assertNotNull(cred)

        assertEquals("ak", cred.accessKeyId)
        assertEquals("sk", cred.accessKeySecret)
        assertEquals("token", cred.securityToken)
        assertTrue(cred.hasKeys())
    }

    @Test
    fun testConstructor_empty() {
        var cred = Credentials("", "")
        assertNotNull(cred)

        assertEquals("", cred.accessKeyId)
        assertEquals("", cred.accessKeySecret)
        assertNull(cred.securityToken)
        assertFalse(cred.hasKeys())

        cred = Credentials("", "sk")
        assertNotNull(cred)

        assertEquals("", cred.accessKeyId)
        assertEquals("sk", cred.accessKeySecret)
        assertNull(cred.securityToken)
        assertFalse(cred.hasKeys())

        cred = Credentials("ak", "")
        assertNotNull(cred)

        assertEquals("ak", cred.accessKeyId)
        assertEquals("", cred.accessKeySecret)
        assertNull(cred.securityToken)
        assertFalse(cred.hasKeys())
    }
}
