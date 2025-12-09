package com.aliyun.kotlin.sdk.service.oss2.credentials

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AnonymousCredentialsProviderTest {

    @Test
    fun testConstructor() {
        runBlocking {
            val provider = AnonymousCredentialsProvider()
            assertNotNull(provider)

            val cred = provider.getCredentials()
            assertEquals("", cred.accessKeyId)
            assertEquals("", cred.accessKeySecret)
            assertNull(cred.securityToken)
            assertFalse(cred.hasKeys())
        }
    }
}
